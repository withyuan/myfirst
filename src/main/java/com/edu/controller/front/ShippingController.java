package com.edu.controller.front;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.pojo.Shipping;
import com.edu.pojo.User;
import com.edu.service.IShippingService;
import com.edu.untils.Const;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    /**
     * 添加地址
     * /shipping/add.do
     */
    @RequestMapping("add.do")
    public ServerResponse add(Shipping shipping, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        shipping.setUserId(userId);
        return shippingService.add(shipping);
    }
    /**
     * 更新地址
     * /shipping/update.do
     */
    @RequestMapping("update.do")
    public ServerResponse update(Shipping shipping, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        shipping.setUserId(userId);
        return shippingService.update(shipping);

    }
    /**
     * 选中查看具体的地址
     * /shipping/select.do
     */
    @RequestMapping("select.do")
    public  ServerResponse select(Integer shippingID ){

        return shippingService.select(shippingID);
    }
    /**
     * 删除地址
     * /shipping/del.do
     */
    @RequestMapping("del.do")
    public ServerResponse delete(Integer shippingId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
    return shippingService.delete(shippingId, userId);

    }
    /**
     * 地址列表
     * /shipping/list.do
     */
    @RequestMapping("list.do")
    public ServerResponse list( @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                HttpSession session){

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        return shippingService.list(pageNum,pageSize,userId);



    }


}
