package com.edu.controller.front;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.IOrderService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    IOrderService orderService;
    /**
     * 创建订单/order/create.do
     */
    @RequestMapping("create/{shippingId}")
    public ServerResponse create(@PathVariable("shippingId") Integer shippingId,
                                 HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "未登录");
        }
        Integer userId = user.getId();
        return orderService.create(userId, shippingId);


    }
}
