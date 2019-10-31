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

}
