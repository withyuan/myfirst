package com.edu.controller.backend;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.Product;
import com.edu.pojo.User;
import com.edu.service.IProductService;
import com.edu.untils.Const;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 商品接口
 */
@RestController
@RequestMapping("/manage/product")
public class ProductController {
    @Autowired
    IProductService productService;

    /**
     * 商品添加/product/save.do
     */
    @RequestMapping("save.do")
    public ServerResponse addOrUpdate(Product product, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }
        int role= user.getRole();
        if(role== RoleEnum.ROLE_User.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "权限不足");
        }

        return productService.addOrUpdate(product);


    }
    /**
     * 商品上下架
     * /manage/product/set_sale_status.do
     */
    @RequestMapping("set_sale_status.do")
    public ServerResponse setSaleStatus(Integer productId,Integer status, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }
        int role= user.getRole();
        if(role== RoleEnum.ROLE_User.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "权限不足");
        }

        return productService.setSaleStatus(productId, status);


    }

    /**
     * 搜索接口/manage/product/search.do
     * @param productName 商品名字
     * @param productId 商品ID
     * @param pageNum   默认1 第几页
     * @param pageSize 默认10 一页几个
     * @return
     */
    @RequestMapping(value = "search.do")
    public  ServerResponse  search(@RequestParam(name = "productName",required = false)String productName,
                                   @RequestParam(name="productId",required =false )Integer productId,
                                   @RequestParam(name="pageNum",required = false ,defaultValue = "1")Integer pageNum,
                                   @RequestParam(name="pageSize",required = false ,defaultValue = "10")Integer pageSize,
                                   HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }
        int role= user.getRole();
        if(role== RoleEnum.ROLE_User.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "权限不足");
        }

        return   productService.search(productName, productId, pageNum, pageSize);

    }

    /**
     * 查看商品详情
     *  /manage/product/detail.do
     */
    @RequestMapping(value = "/{productId}")
    public ServerResponse detail(@PathVariable("productId") Integer productId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }
        int role= user.getRole();
        if(role== RoleEnum.ROLE_User.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "权限不足");
        }
        return  productService.detail(productId);



    }







}


