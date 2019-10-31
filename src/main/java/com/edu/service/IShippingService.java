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
    ServerResponse<Shipping> select(Integer shippingId);

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
    /**
     * 根据用户名号和地址号 查询地址是否存在
     */
    ServerResponse  isNotExistByUserIdAndShippingId(Integer userId,Integer shippingId);

}
