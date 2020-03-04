package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.Seller;
import com.edu.pojo.User;

/**
 * 商家服务接口
 */

public interface ISellerService {
        //申请入驻
    ServerResponse add(Seller seller);
    //申请入驻有账号
    ServerResponse loginAdd(Seller seller, User user);

    //查看所有商家
    ServerResponse showAllSellers(Integer pageNum,Integer pageSize);
    //根据名字查询商家
   ServerResponse searchByName(String name,String nickName,Integer pageNum,Integer pageSize);
 ServerResponse updateStatus(String sellerId,String status);
}
