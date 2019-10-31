package com.edu.service.impl;


import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.ShippingMapper;
import com.edu.pojo.Shipping;
import com.edu.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping) {
        //参数非空判断
        if (shipping == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
            //添加
            int result = shippingMapper.insert(shipping);
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加地址失败");
            } else {
                Map<String,Integer> map= Maps.newHashMap();
                map.put("shippingId",shipping.getId());
                return ServerResponse.createServerResponseBySuccess(map, "添加成功");
            }


    }

    @Override
    public ServerResponse<Shipping> select(Integer shippingId) {
        if(shippingId==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if(shipping==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "收货地址不存在");
        }
        return ServerResponse.createServerResponseBySuccess(shipping);
    }

    @Override
    public ServerResponse delete(Integer shippingId,Integer userId) {
        if(shippingId==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }
        int result = shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
            if(result<=0){
                return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"删除失败");
            }
        else {
            return ServerResponse.createServerResponseBySuccess("删除成功");
            }

    }

    @Override
    public ServerResponse update(Shipping shipping) {
        if (shipping == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }

        int result= shippingMapper.updateBySelectiveKey(shipping);
       if (result<=0){
           return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新失败");
       }

        return ServerResponse.createServerResponseBySuccess("更新地址成功");
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize,Integer userId) {
       //查询之前写
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectAllByUserId(userId);
        PageInfo pageInfo=new PageInfo(shippingList);

        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse isNotExistByUserIdAndShippingId(Integer userId, Integer shippingId) {

        if(shippingId==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }if(userId==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }
        int result = shippingMapper.isNotExistByUserIdAndShippingId(userId, shippingId);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "不存在该地址");
        }
        return ServerResponse.createServerResponseBySuccess();
    }
}
