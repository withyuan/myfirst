package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.Product;

/**
 * 商品服务接口
 */
public interface IProductService {
    ServerResponse addOrUpdate(Product product);

    /**
     * 修改状态码
     */
    ServerResponse setSaleStatus(Integer Id, Integer status);

    /**
     * 后台商品搜索
     */
    ServerResponse search(String productName,
                          Integer productId,
                          Integer pageNum,
                          Integer pageSize);

    /**
     * 查看详情产品
     */
    ServerResponse detail(Integer productId);

    /**
     * 前台搜索
     */
    ServerResponse front_list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);

    /**
     * 前台查看商品详情
     */
    ServerResponse front_detail(Integer productId);
    /**
     * 根据商品ID查询商品信息(库存)
     */
     ServerResponse<Product> findProductById(Integer productId);
}
