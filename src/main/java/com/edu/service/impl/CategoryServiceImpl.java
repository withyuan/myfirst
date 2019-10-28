package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.CategoryMapper;
import com.edu.pojo.Category;
import com.edu.service.ICategoryService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ServerResponse addCategory(Category category) {
        //参数判断
        if(category==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //调用DAO层
        int result = categoryMapper.insert(category);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加品类失败");
        }
        return ServerResponse.createServerResponseBySuccess("品类添加成功");
    }

    @Override
    public ServerResponse updateCategory(Category category) {
        //参数判断
        if(category==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if(category.getId()==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "类别Id必须传");
        }
        //调用dao
        int result = categoryMapper.updateByPrimaryKey(category);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新品类失败");
        }

        return ServerResponse.createServerResponseBySuccess("更新品类成功");
    }

    @Override
    public ServerResponse getCategoryById(Integer categoryId) {
        if(categoryId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        List<Category> categoryList = categoryMapper.selectCategoryById(categoryId);
        return ServerResponse.createServerResponseBySuccess(categoryList,"成功");
    }

    @Override
    public ServerResponse deepCategory(Integer categoryId) {
        if(categoryId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Set<Category> categories= Sets.newHashSet();
        Set<Category> categorySet = findAllChildCategory(categories, categoryId);
        Set<Integer> categoryIds=Sets.newConcurrentHashSet();
        Iterator<Category> categoryIterator = categorySet.iterator();
        while(categoryIterator.hasNext()){
            Category category = categoryIterator.next();
            categoryIds.add(category.getId());
        }
        return ServerResponse.createServerResponseBySuccess(categoryIds);
    }
    public Set<Category>  findAllChildCategory(Set<Category> categorySet,Integer categoryId){
        //查看categoryId类别信息
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }
        //查看categoryId的平级子类
        List<Category> categoryList = categoryMapper.selectCategoryById(categoryId);
        if(categoryList!=null&categoryList.size()>0){
            for (Category category1:categoryList//遍历categoryList
                 ) {
                    findAllChildCategory(categorySet, category1.getId());
            }
        }

        return categorySet;
    }
}
