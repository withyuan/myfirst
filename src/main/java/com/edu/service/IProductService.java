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
     ServerResponse setSaleStatus(Integer Id, Integer status);

     /**
      * 后台商品搜索
      */
     public ServerResponse search(String productName,
                                  Integer productId,
                                  Integer pageNum,
                                  Integer pageSize);

     /**
      * 查看详情产品
      */
     public ServerResponse detail(Integer productId);
}
