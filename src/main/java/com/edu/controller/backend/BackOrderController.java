package com.edu.controller.backend;

import com.edu.common.ServerResponse;
import com.edu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 后台订单模块
 */
@RestController
@RequestMapping("/manage/order/")
public class BackOrderController {
    @Autowired
    IOrderService orderService;

    /**
     * 订单List
     * /manage/order/list.do
     */
    @RequestMapping("list.do")
    public ServerResponse list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {


        return orderService.list(null, pageNum, pageSize);


    }

    /**
     * .订单发货
     * /manage/order/send_goods.do
     */
    @RequestMapping("send_goods/{orderNo}")
    public ServerResponse send_goods(@PathVariable("orderNo") Long orderNo) {

        return orderService.send_goods(orderNo);


    }


}
