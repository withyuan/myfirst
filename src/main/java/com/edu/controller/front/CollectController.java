package com.edu.controller.front;

import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.ICollectService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 收藏品接口
 */
@RestController
@RequestMapping("/collect/")
@CrossOrigin
public class CollectController {
    @Autowired
    ICollectService collectService;

    /**
     * 添加商品到收藏
     * /collect/list.do
     */
    @RequestMapping("add/{productId}")
    public ServerResponse addCart(@PathVariable("productId") Integer productId
            , HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return collectService.addProductToCollect(user.getId(), productId );
    }

    /**
     * 购物车List列表
     * /collect/list.do
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId = user.getId();
        return collectService.list(userId);

    }


    /**
     * .移除收藏某个产品
     * /collect/delete_product.do
     */
    @RequestMapping("delete_product.do")
    public ServerResponse deleteProduct(String productIds, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId = user.getId();
        return collectService.deleteProduct(productIds, userId);
    }





}
