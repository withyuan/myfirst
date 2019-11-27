package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.CategoryMapper;
import com.edu.pojo.Category;
import com.edu.service.ICategoryService;
import com.edu.untils.DateUtils;
import com.edu.vo.CategoryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
        if (category == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //调用DAO层
        int result = categoryMapper.insert(category);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加品类失败");
        }
        return ServerResponse.createServerResponseBySuccess("品类添加成功");
    }

    @Override
    public ServerResponse updateCategory(Category category) {
        //参数判断
        if (category == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if (category.getId() == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "类别Id必须传");
        }
        //调用dao
        int result = categoryMapper.updateByPrimaryKey(category);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新品类失败");
        }

        return ServerResponse.createServerResponseBySuccess("更新品类成功");
    }

    @Override
    public ServerResponse getCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        List<Category> categoryList = categoryMapper.selectCategoryById(categoryId);
        return ServerResponse.createServerResponseBySuccess(categoryList, "成功");
    }

    @Override
    public ServerResponse deepCategory(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Set<Category> categories = Sets.newHashSet();
        Set<Category> categorySet = findAllChildCategory(categories, categoryId);
        Set<Integer> categoryIds = Sets.newConcurrentHashSet();
        Iterator<Category> categoryIterator = categorySet.iterator();
        while (categoryIterator.hasNext()) {
            Category category = categoryIterator.next();
            categoryIds.add(category.getId());
        }
        return ServerResponse.createServerResponseBySuccess(categoryIds);
    }

    @Override
    public ServerResponse<Category> selectCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数必须传");
        }
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        return ServerResponse.createServerResponseBySuccess(category);
    }

    @Override
    public ServerResponse showCategory(Integer pageNum, Integer pageSize) {
        //先按分页查询
        PageHelper.startPage(pageNum, pageSize);
        //去找Mapper展示所有
        List<Category> categoryList = categoryMapper.selectAll();
        //List<Category>转换成List<CategoryVO>
        List<CategoryVO> categoryVOS=Lists.newArrayList();
        for(Category c:categoryList){
                    //一个个的C转换成VO
                    CategoryVO categoryVO=new CategoryVO();
                    categoryVO.setId(c.getId());
                    categoryVO.setName(c.getName());
                    categoryVO.setMainImage(c.getMainImage());
                    categoryVO.setParentId(c.getParentId());
                    categoryVO.setSortOrder(c.getSortOrder());
                    categoryVO.setCreateTime(DateUtils.dateToStr(c.getCreateTime()));
                    categoryVO.setUpdateTime(DateUtils.dateToStr(c.getUpdateTime()));
                    categoryVO.setStatus(c.getStatus());
                    //一个个放进去
            categoryVOS.add(categoryVO);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(categoryVOS);

        //查询有多少类别
        Integer count = categoryMapper.selectCount();

        return ServerResponse.createServerResponseBySuccess(pageInfo,count);
    }

    /**
     * 递归查看
     *
     * @param categorySet 递归出来的放入到SET容器中
     * @param categoryId  根据parentID
     * @return
     */

    public Set<Category> findAllChildCategory(Set<Category> categorySet, Integer categoryId) {
        //查看categoryId类别信息
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查看categoryId的平级子类
        List<Category> categoryList = categoryMapper.selectCategoryById(categoryId);
        if (categoryList != null & categoryList.size() > 0) {
            for (Category category1 : categoryList//遍历categoryList
            ) {
                findAllChildCategory(categorySet, category1.getId());
            }
        }

        return categorySet;
    }
}
