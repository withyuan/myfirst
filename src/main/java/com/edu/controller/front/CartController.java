package com.edu.controller.front;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.ICartService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 购物车接口
 */
@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;

    /**
     * 添加商品到购物车
     * /cart/list.do
     */
    @RequestMapping("add/{productId}/{count}")
    public ServerResponse addCart(@PathVariable("productId")Integer productId
                                  , @PathVariable("count")Integer count,
                                  HttpSession session
    ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }

        return cartService.addProductToCart(user.getId(), productId, count);
    }















}
