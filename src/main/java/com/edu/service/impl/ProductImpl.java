package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.ProductMapper;
import com.edu.pojo.Product;
import com.edu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImpl implements IProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public ServerResponse addOrUpdate(Product product) {
        if(product==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //sub_images图片地址图片地址，json格式main_image产品主图，url相对地址
        //设置商品主图  用sub_images 1.png,2.png 第一个作为主图
        String subImages=product.getSubImages();
        if(subImages!=null&&!subImages.equals("")){
            String[] subImageArr = subImages.split(",");//根据逗号分隔
            if (subImageArr.length>0){
                    //设置商品主图
                    product.setMainImage(subImageArr[0]);
            }

        }




        Integer id = product.getId();
        System.out.println(product.getCategoryId());
        if (id==null){
            int result = productMapper.insert(product);
            if (result<=0){
                return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加失败");
            }else {
                return ServerResponse.createServerResponseBySuccess("添加成功");
            }
            //添加
        }else {
            int result = productMapper.updateByPrimaryKey(product);
            //更新
            if (result<=0){
                return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新失败");
            }else {
                return ServerResponse.createServerResponseBySuccess("更新成功");
            }
        }

    }

    @Override
    public ServerResponse setSaleStatus(Integer Id, Integer status) {
        //参数不能为空
        if(Id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if(status==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product p = productMapper.selectByPrimaryKey(Id);
        if(p==null){
            return  ServerResponse.createServerResponseByError(ResponseCode.UNEXIST_P, "该商品不存在");
        }
      int result=  productMapper.updateStatusById(Id,status);
     if(result<=0){
        return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "修改失败");
    }

    return ServerResponse.createServerResponseBySuccess("修改产品状态成功");




    }
}
