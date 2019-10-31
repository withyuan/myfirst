package com.edu.service;

import com.edu.common.ServerResponse;

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



}
