package com.edu.controller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.IOrderService;
import com.edu.untils.Const;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

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
                                 HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        return orderService.create(userId, shippingId);


    }

    /**
     * 支付接口  /order/pay.do
     */
    @RequestMapping("pay/{orderNo}")
    public ServerResponse pay(@PathVariable("orderNo") Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);


        return orderService.pay(user.getId(), orderNo);

    }

    /**
     * 查询订单支付状态
     * /order/query_order_pay_status.do
     */
    @RequestMapping("query_order_pay_status/{orderNo}")
    public ServerResponse orderPay_status(@PathVariable("orderNo") Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return orderService.orderPay_status(orderNo);
    }

    /**
     * 获取订单的商品信息
     * /order/get_order_cart_product.do
     */
    @RequestMapping("get_order_cart_product")
    public ServerResponse get_order_cart_product(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        return orderService.get_order_cart_product(userId);
    }

    /**
     * 订单List
     * /order/list.do
     */
    @RequestMapping("list.do")
    public ServerResponse list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                               HttpSession session) {


        User user = (User) session.getAttribute(Const.CURRENT_USER);


        return orderService.list(user.getId(), pageNum, pageSize);


    }

    /**
     * 订单详情detail
     * /order/detail.do
     */
    @RequestMapping("detail/{orderNo}")
    public ServerResponse detail(@PathVariable("orderNo") Long orderNo, HttpSession session) {



        return orderService.detail(orderNo);

    }


    /**
     * 取消订单
     * /order/cancel.do
     */
    @RequestMapping("cancel/{orderNo}")
    public ServerResponse cancel(@PathVariable("orderNo") Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);


        Integer userId = user.getId();
        return orderService.concel(userId, orderNo);
    }


    /**
     * 支付宝服务回调商家服务器接口
     */
    @RequestMapping("callback.do")
    public String alipay_callback(HttpServletRequest request) {
        Map<String, String[]> callbackParams = request.getParameterMap();
        Map<String, String> sigParams = Maps.newHashMap();

        Iterator<String> iterator = callbackParams.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] values = callbackParams.get(key);
            StringBuffer stringBuffer = new StringBuffer();
            if (values != null || values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    stringBuffer.append(values[i]);
                    //最后一个不加，
                    if (i != values.length - 1) {
                        stringBuffer.append(",");


                    }
                }
            }
            sigParams.put(key, stringBuffer.toString());

        }

        //验证签名保证接口是由支付宝服务器调用的
        System.out.println(sigParams);
        try {
            sigParams.remove("sign_type");
            boolean result = AlipaySignature.rsaCheckV2(sigParams, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (result) {
                orderService.callback(sigParams);
            } else {
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "success";

    }


}
