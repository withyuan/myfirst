package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.Shipping;

public interface IShippingService {

    /**
     * 添加
     */
    ServerResponse add(Shipping shipping);
    /**
     * 查看选中的地址
     */
    ServerResponse select(Integer shippingId);

/**
 * 删除地址
 */
    ServerResponse delete(Integer shippingId,Integer userId);
    /**
     * 修改地址信息
     */
    ServerResponse update(Shipping shipping);
    /**
     * 地址列表
     * /shipping/list.do
     */
    ServerResponse list(Integer pageNum,Integer pageSize,Integer userId);

}
