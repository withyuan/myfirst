package com.edu.controller.backend;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.Product;
import com.edu.pojo.User;
import com.edu.service.IProductService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
     */
    @RequestMapping(value = "search.do")
    public  ServerResponse  search(){
        return null;
    }







}


