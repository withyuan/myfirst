package com.edu.controller.seller;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.pojo.Seller;
import com.edu.pojo.User;
import com.edu.service.ISellerService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 商家控制类
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/seller/")
public class SellerController {
    @Autowired
    ISellerService sellerService;
    /**
     * 入驻接口  添加
     * 无账号
     *
     */
    @RequestMapping(value= "add")
    public ServerResponse add(Seller seller){
        return  sellerService.add(seller);
    }
    /**
     * 入驻接口 登录状态下
     */
    @RequestMapping(value= "loginadd")
    public ServerResponse loginAdd(Seller seller ,HttpSession session){
        //判断是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "用户未登录，无法获取当前用户信息,status=10强制退出");
        }

        return  sellerService.loginAdd(seller,user);
    }
    /**
     * 查看商家个人信息。
     */
    @RequestMapping(value = "show")
    public ServerResponse show(String sellerId){

        return sellerService.show(sellerId);

    }
    //登录状态下查看状态
    @RequestMapping(value = "show_login")
    public  ServerResponse showLogin(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "用户未登录，无法获取当前用户信息,status=10强制退出");
        }
        String username=user.getUsername();
        return  sellerService.showLogin(username);

    }

}
