package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.Category;

public interface ICategoryService {
    /**
     * 添加类别manage/category/add_category.do
     */
    public ServerResponse addCategory(Category category);
    /**
     * 修改类别manage/category/set_category_name.do
     * categoryId
     * categoryName
     * categoryUrl
     */
    public ServerResponse updateCategory(Category category );
    /**
     * 查看平级类别/manage/category/get_category.do
     * categoryId
     */
    public ServerResponse getCategoryById( Integer categoryId);
    /**
     * 查看平级类别递归子节点manage/category/get_deep_category.do
     * categoryId
     */
    public ServerResponse deepCategory(Integer categoryId);

    /**
     * 根据ID查询类别
     */
    ServerResponse<Category> selectCategoryById(Integer categoryId);

}
