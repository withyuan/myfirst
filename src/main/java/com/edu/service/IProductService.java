package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.Product;

/**
 * 商品服务接口
 */
public interface IProductService {
     ServerResponse addOrUpdate(Product product);

     /**
      * 修改状态码
      */
     ServerResponse setSaleStatus(Integer Id,Integer status);
}
