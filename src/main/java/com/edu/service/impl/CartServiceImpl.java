package com.edu.service.impl;

import com.edu.common.CheckEnum;
import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.CartMapper;
import com.edu.pojo.Cart;
import com.edu.pojo.Product;
import com.edu.service.ICartService;
import com.edu.service.IProductService;
import com.edu.untils.BigDecimalUtils;
import com.edu.vo.CartProductVO;
import com.edu.vo.CartVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车实现类
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    IProductService productService;
    @Autowired
    CartMapper cartMapper;

    @Override
    public ServerResponse addProductToCart(Integer userId, Integer productId, Integer count) {
        //1.参数的非空判断
        if (productId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品ID必须传");
        }
        if (count == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品的数量不能为空");
        }
        //2.判断商品是否存在
        ServerResponse<Product> serverResponse = productService.findProductById(productId);
        if (!serverResponse.isSuccess()) {
            return ServerResponse.createServerResponseByError(serverResponse.getStatus(), serverResponse.getMsg());
        } else {
            Product product = serverResponse.getData();
            if (product.getStock() <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品售空");
            }
        }
        //3.判断商品是否在购物车中，如果在 增加数量，如果不在添加到购物车
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //用户没有添加购物车
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            //默认未选中
            newCart.setChecked(CheckEnum.CART_PRODUCT_UNCHECK.getCheck());
            newCart.setQuantity(count);
            int result = cartMapper.insert(newCart);
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加失败");
            }
        } else {
            //更新商品的数量
            cart.setQuantity(cart.getQuantity() + count);
            int result = cartMapper.updateByPrimaryKey(cart);
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新失败");
            }
        }
        //4.封装购物车对象CartVO
        CartVO cartVO = getCartVO(userId);
        //5.返回VO
        return ServerResponse.createServerResponseBySuccess(cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
        //查看该用户购物车根据用户号
        CartVO cartVO = getCartVO(userId);

        return ServerResponse.createServerResponseBySuccess(cartVO);
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //step1:参数判定
        if (productId == null || count == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //查询购物车中的商品
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId, productId);
        if (cart != null) {
            //更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }

        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }

    @Override
    public ServerResponse deleteProduct(String productIds, Integer userId) {
        //step1:参数非空校验
        if (productIds == null || productIds.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //productIds-->List<Integer>
        List<Integer> productList = Lists.newArrayList();
        String[] productIdArr = productIds.split(",");
        if (productIdArr != null && productIdArr.length > 0) {
            for (String p : productIdArr
            ) {
                //将字符串转成INT
                Integer productId = Integer.parseInt(p);
                productList.add(productId);
            }
        }
        //调用DAO
        cartMapper.deleteByUserIdAndProductIds(userId, productList);

        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }

    @Override
    public ServerResponse getCartProductCount(Integer userId) {

        Integer result = cartMapper.getCartProductCount(userId);

        return ServerResponse.createServerResponseBySuccess(result);
    }

    @Override
    public ServerResponse select(Integer userId, Integer productId, Integer check) {
        cartMapper.selectOrUnselectProduct(userId, productId, check);
        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }

    @Override
    public ServerResponse<List<Cart>> findCartsByUserIdAndChecked(Integer userId) {
        List<Cart> cartList = cartMapper.findCartsByUserIdAndChecked(userId);

        return ServerResponse.createServerResponseBySuccess(cartList);
    }

    @Override
    public ServerResponse deleteBatch(List<Cart> cartList) {
        if(cartList==null||cartList.size()==0){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "要删出的购物车不能为空");

        }
     int result=  cartMapper.deleteBatch(cartList);
        if(result!=cartList.size()){

            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "购物车清空失败");
        }
        return ServerResponse.createServerResponseBySuccess();




    }

    private CartVO getCartVO(Integer userId) {
        CartVO cartVO = new CartVO();
        //根据userID查询购物信息-->List<Cart>
        List<Cart> cartList = cartMapper.findCartByUserId(userId);
        if (cartList == null || cartList.size() == 0) {
            return cartVO;
        }
        //定义购物车商品总价格
        BigDecimal cartTotalPrice = new BigDecimal(0);
        //List<Cart>---List<CartProductVO>
        List<CartProductVO> cartProductVOS = Lists.newArrayList();
        int limit_quantity = 0;//加入购物车的数量
        //库存描述信息
        String limitQuantity = null;
        for (Cart cart : cartList
        ) {
            //cart--?cartProductVo
            CartProductVO cartProductVO = new CartProductVO();
            cartProductVO.setId(cart.getId());
            cartProductVO.setUserId(userId);
            cartProductVO.setProductId(cart.getProductId());
            cartProductVO.setQuantity(cart.getQuantity());
            ServerResponse<Product> serverResponse = productService.findProductById(cart.getProductId());
            if (serverResponse.isSuccess()) {
                Product product = serverResponse.getData();
                if (product.getStock() >= cart.getQuantity()) {
                    limitQuantity = "LIMIT_NUM_SUCCESS";
                    limit_quantity = cart.getQuantity();
                }
                if (product.getStock() < cart.getQuantity()) {
                    limit_quantity = product.getStock();
                    limitQuantity = "LIMIT_NUM_FAIL";

                }
                cartProductVO.setQuantity(limit_quantity);
                cartProductVO.setProductName(product.getName());
                cartProductVO.setLimitQuantity(limitQuantity);
                cartProductVO.setProductStatus(product.getStatus());
                cartProductVO.setProductSubtitle(product.getSubtitle());
                cartProductVO.setProductMainImage(product.getMainImage());
                cartProductVO.setProductPrice(product.getPrice());
                cartProductVO.setProductTotalPrice(
                        BigDecimalUtils.mul(product.getPrice().doubleValue(),
                                cart.getQuantity() * 1.0));
                cartProductVO.setProductStock(product.getStock());
                cartProductVO.setProductChecked(cart.getChecked());
                if (cart.getChecked() == CheckEnum.CART_PRODUCT_CHECK.getCheck()) {
                    //商品被选中 总价格+上商品*商品数量
                    cartTotalPrice = BigDecimalUtils.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }


                cartProductVOS.add(cartProductVO);
            }
        }
        cartVO.setCartProductVOList(cartProductVOS);
        //计算购物车总价格
        cartVO.setCarttotalprice(cartTotalPrice);

        //判断是否全选
        Integer isAllCheck = cartMapper.isAllCheck(userId);
        if (isAllCheck == 0) {
            //没查到说明全选中了
            cartVO.setIsallchecked(true);
        } else {
            cartVO.setIsallchecked(false);
        }
        return cartVO;

    }


}
