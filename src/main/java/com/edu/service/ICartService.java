package com.edu.service;

import com.edu.common.ServerResponse;

/**
 * 购物车
 */
public interface ICartService {
    /**
     * 添加商品到购物车
     */
    public ServerResponse addProductToCart(Integer userId,Integer productId,Integer count);
}
