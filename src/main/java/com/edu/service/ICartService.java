package com.edu.service;

import com.edu.common.ServerResponse;

/**
 * 购物车
 */
public interface ICartService {
    /**
     * 添加商品到购物车
     */
    public ServerResponse addProductToCart(Integer userId, Integer productId, Integer count);

    /**
     * 购物车列表
     */
    ServerResponse list(Integer userId);

    /**
     * 更新购物车某个产品数量
     */
    ServerResponse update(Integer userId, Integer productId, Integer count);

    /**
     * .移除购物车某个产品
     * /cart/delete_product.do
     */
    ServerResponse deleteProduct(String productIds, Integer userId);

    /**
     * 得到购物车里产品的数量
     */
    ServerResponse getCartProductCount(Integer userId);

    /**
     * 选中购物车商品
     *
     * @param userId    用户ID
     * @param productId 产品ID
     * @param check     选中
     * @return
     */
    ServerResponse select(Integer userId, Integer productId, Integer check);

}
