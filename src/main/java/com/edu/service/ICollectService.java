package com.edu.service;

import com.edu.common.ServerResponse;

/**
 * 收藏品
 */
public interface ICollectService {
    /**
     * 添加商品到收藏
     */
    public ServerResponse addProductToCollect(Integer userId, Integer productId);

    /**
     * 收藏品列表
     */
    ServerResponse list(Integer userId);


    /**
     * .移除某个产品从收藏里
     */
    ServerResponse deleteProduct(String productIds, Integer userId);


}
