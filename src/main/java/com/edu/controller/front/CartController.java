package com.edu.controller.front;

import com.edu.common.CheckEnum;
import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.ICartService;
import com.edu.untils.Const;
import org.apache.ibatis.annotations.Param;
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
    public ServerResponse addCart(@PathVariable("productId") Integer productId
            , @PathVariable("count") Integer count,
                                  HttpSession session
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return cartService.addProductToCart(user.getId(), productId, count);
    }

    /**
     * 购物车List列表
     * /cart/list.do
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId = user.getId();
        return cartService.list(userId);

    }

    /**
     * .更新购物车某个产品数量
     * productId,
     * count
     * /cart/update.do
     */
    @RequestMapping("update/{productId}/{count}")
    public ServerResponse update(@PathVariable("productId") Integer productId, @PathVariable("count") Integer count, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId = user.getId();
        return cartService.update(userId, productId, count);

    }

    /**
     * .移除购物车某个产品
     * /cart/delete_product.do
     */
    @RequestMapping("delete_product.do")
    public ServerResponse deleteProduct(String productIds, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId = user.getId();
        return cartService.deleteProduct(productIds, userId);
    }

    /**
     * 购物车选中某个商品
     */
    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        return cartService.select(user.getId(), productId, CheckEnum.CART_PRODUCT_CHECK.getCheck());
    }

    /**
     * 购物车取消选中某个商品
     */
    @RequestMapping(value = "/un_select.do")
    public ServerResponse un_select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return cartService.select(user.getId(), productId, CheckEnum.CART_PRODUCT_UNCHECK.getCheck());
    }

    /**
     * 全选
     */
    @RequestMapping(value = "/select_all.do")
    public ServerResponse select_all(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return cartService.select(user.getId(), null, CheckEnum.CART_PRODUCT_CHECK.getCheck());
    }

    /**
     * 取消全选
     */
    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        return cartService.select(user.getId(), null, CheckEnum.CART_PRODUCT_UNCHECK.getCheck());
    }

    /**
     * 查询在购物车里的产品数量
     * /cart/get_cart_product_count.do
     */
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();

        return cartService.getCartProductCount(userId);
    }


}
