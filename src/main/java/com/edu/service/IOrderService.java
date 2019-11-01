package com.edu.service;

import com.edu.common.ServerResponse;

import java.util.Map;

/**
 * 订单
 */
public interface IOrderService {
    /**
     * 创建订单
     * @param userId 用户ID
     * @param shippingId  收货地址ID
     * @return
     */
    ServerResponse create(Integer userId,Integer shippingId);

    /**
     * 支付
     */
    ServerResponse pay(Integer userId,Long orderNo);
    /**
     * 支付回调接口
     */
    String callback(Map<String ,String> requestParams);
    /**
     * 查询订单支付状态
     */
    ServerResponse orderPay_status(Long orderNo);
    /**
     * 获取订单的商品信息
     */
    ServerResponse get_order_cart_product(Integer userId);

    /**
     * 订单List
     */
    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);
    /**
     * 订单详情
     */
   ServerResponse  detail(Long orderNo);
    /**
     * 删除订单
     */
    ServerResponse concel(Integer userId,Long orderNo);
    /**
     * 发货
     */
    ServerResponse send_goods(Long orderNo);

}
