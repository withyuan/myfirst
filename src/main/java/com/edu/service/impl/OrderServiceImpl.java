package com.edu.service.impl;

import com.edu.common.*;
import com.edu.dao.OrderItemMapper;
import com.edu.dao.OrderMapper;
import com.edu.pojo.*;
import com.edu.service.ICartService;
import com.edu.service.IOrderService;
import com.edu.service.IProductService;
import com.edu.service.IShippingService;
import com.edu.untils.BigDecimalUtils;
import com.edu.untils.DateUtils;
import com.edu.vo.OrderItemVO;
import com.edu.vo.OrderVO;
import com.edu.vo.ShippingVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * 订单实现类
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    IShippingService shippingService;
    @Value("${business.imageHost}")
    private String imageHost;
    @Override
    public ServerResponse create(Integer userId, Integer shippingId) {
      /*  //1. 参数非空
        //2.查看购买商品 根据用户ID查看用户购物车中已经选的商品List<Cart>
        //3.List<Cart>-->List<OrderItemVO>
        //4.创建一个订单类Order类并保存到DB
        //5.保存List<OrderItemVO>-->order_item
        //6.扣库存
        //7.清空购物车中下单的商品
        //8.返回OrderVO*/
        //1. 参数非空
        if (shippingId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "收货地址必须传");
        }
        //判断shipping是否存在
        ServerResponse s = shippingService.isNotExistByUserIdAndShippingId(userId, shippingId);
        if(!s.isSuccess()){
            return s;
        }
        //2.查看购买商品 根据用户ID查看用户购物车中已经选的商品List<Cart>
        ServerResponse<List<Cart>> serverResponse = cartService.findCartsByUserIdAndChecked(userId);
        List<Cart> cartList = serverResponse.getData();
        if (cartList == null || cartList.size() == 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "购物车为空或者为选中购物车商品");
        }
        //3.List<Cart>-->List<OrderItem>
        ServerResponse orderItems_serverResponse = getCartOrderItem(userId, cartList);
        if (!orderItems_serverResponse.isSuccess()) {
            return orderItems_serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) orderItems_serverResponse.getData();

        //4.创建一个订单类Order类并保存到DB
        ServerResponse<Order> order_serverResponse = createOrder(userId, shippingId, orderItemList);
        if (!order_serverResponse.isSuccess()){
            return order_serverResponse;
        }

        Order order = order_serverResponse.getData();
        //5.保存List<OrderItemVO>-->order_item保存订单明细
        ServerResponse saveServerResponse1 = saveOrderItems(orderItemList, order);
        if(!saveServerResponse1.isSuccess()){
            return saveServerResponse1;
        }
        //6.扣库存
        reduceProStock(orderItemList);
        //7.清空购物车中下单的商品(批量删除)
        ServerResponse serverResponse1= cartService.deleteBatch(cartList);
        if (!serverResponse1.isSuccess()){
            return saveServerResponse1;
        }
        //8.返回OrderVO
        return  assembleOrderVO(order, orderItemList, shippingId);


    }

    //得到OrderVO
    private ServerResponse assembleOrderVO(Order order, List<OrderItem> orderItemList, Integer shippingId){
        OrderVO orderVO=new OrderVO();

        List<OrderItemVO> orderItemVOList=Lists.newArrayList();
        for(OrderItem orderItem:orderItemList){
            OrderItemVO orderItemVO= assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }
        orderVO.setOrderItemVoList(orderItemVOList);
        orderVO.setImageHost(imageHost);//图片地址
        ServerResponse<Shipping> serverResponse = shippingService.select(shippingId);
       if(!serverResponse.isSuccess()){
           return serverResponse;
       }
        Shipping shipping = serverResponse.getData();
        if(shipping!=null){
            orderVO.setShippingId(shippingId);
            ShippingVO shippingVO= assmbleShippingVO(shipping);
            orderVO.setShippingVo(shippingVO);
            orderVO.setReceiverName(shipping.getReceiverName());
        }

        orderVO.setStatus(order.getStatus());
        //遍历枚举找到状态等于getStatus
        OrderStatusEnum orderStatusEnum=OrderStatusEnum.codeOf(order.getStatus());
        if(orderStatusEnum!=null){
            orderVO.setStatusDesc(orderStatusEnum.getDesc());
        }

        orderVO.setPostage(0);
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        PayTypeEnum paymentEnum=PayTypeEnum.codeOf(order.getPaymentType());
        if(paymentEnum!=null){
            orderVO.setPaymentTypeDesc(paymentEnum.getDesc());
        }
        orderVO.setOrderNo(order.getOrderNo());

        return ServerResponse.createServerResponseBySuccess(orderVO);
    }
    private ShippingVO assmbleShippingVO(Shipping shipping){
        ShippingVO shippingVO=new ShippingVO();

        if(shipping!=null){
            shippingVO.setReceiverAddress(shipping.getReceiverAddress());
            shippingVO.setReceiverCity(shipping.getReceiverCity());
            shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
            shippingVO.setReceiverMobile(shipping.getReceiverMobile());
            shippingVO.setReceiverName(shipping.getReceiverName());
            shippingVO.setReceiverPhone(shipping.getReceiverPhone());
            shippingVO.setReceiverProvince(shipping.getReceiverProvince());
            shippingVO.setReceiverZip(shipping.getReceiverZip());
        }
        return shippingVO;
    }

        //扣库存
    private  ServerResponse reduceProStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem:orderItemList
             ) {
            Integer productId = orderItem.getProductId();
            ServerResponse<Product> serverResponse = productService.selectProduct(productId);
            Product product= serverResponse.getData();
                //商品库存重新定义
             int stock=  product.getStock()-orderItem.getQuantity();
            ServerResponse serverResponse1 = productService.reduceProductStock(productId, stock);
            if(!serverResponse1.isSuccess()){
                return serverResponse1;
            }
        }
        return ServerResponse.createServerResponseBySuccess();
    }
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {

        List<OrderItem> orderItemList = Lists.newArrayList();

        for (Cart cart : cartList) {

            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(userId);
            ServerResponse<Product> serverResponse = productService.selectProduct(cart.getProductId());
            if (!serverResponse.isSuccess()) {
                return serverResponse;
            }
            Product product = serverResponse.getData();
            serverResponse.getData();
            if (product == null) {
                return ServerResponse.createServerResponseByError("id为" + cart.getProductId() + "的商品不存在");
            }
            if (product.getStatus() != ProductStatusEnum.PRODUCT_ONLINE.getCode()) {//商品下架
                return ServerResponse.createServerResponseByError("id为" + product.getId() + "的商品已经下架");
            }
            if (product.getStock() < cart.getQuantity()) {//库存不足
                return ServerResponse.createServerResponseByError("id为" + product.getId() + "的商品库存不足");
            }
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(), cart.getQuantity().doubleValue()));

            orderItemList.add(orderItem);
        }

        return ServerResponse.createServerResponseBySuccess(orderItemList);
    }
    //5.保存List<OrderItemVO>-->order_item
    private ServerResponse saveOrderItems(List<OrderItem> orderItemList,Order order){
        for (OrderItem orderItem:
                orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //批量插入insert into name values() ,(),()..
        int result = orderItemMapper.insertBath(orderItemList);
        if(result!=orderItemList.size()){
            //有些订单明细没有保存到DB
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ERROR ,"订单明细保存失败");
        }

    return ServerResponse.createServerResponseBySuccess();

    }

    //4.创建一个订单类Order类并保存到DB
    private ServerResponse<Order> createOrder(Integer userId, Integer shippingId, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setOrderNo(generatorOrderNo());
        order.setPayment(getOrderTotalPrice(orderItems));
        order.setPaymentType(PayTypeEnum.ONLINE_PAYMENT.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.ORDER_NO_PAY.getStatus());
        int result = orderMapper.insert(order);
        if (result <= 0) {
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ERROR, "订单保存失败");

        }
        return ServerResponse.createServerResponseBySuccess(order);
    }

    private OrderItemVO assembleOrderItemVO(OrderItem orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        if (orderItem != null) {
            orderItemVO.setQuantity(orderItem.getQuantity());
            orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));
            orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
            orderItemVO.setOrderNo(orderItem.getOrderNo());
            orderItemVO.setProductId(orderItem.getProductId());
            orderItemVO.setProductImage(orderItem.getProductImage());
            orderItemVO.setProductName(orderItem.getProductName());
            orderItemVO.setTotalPrice(orderItem.getTotalPrice());

        }

        return orderItemVO;
    }

    /**
     * 生产订单号
     */
    private Long generatorOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(100);
    }

    /**
     * 计算订单的总价格
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItems) {
        BigDecimal orderTotalPrice = new BigDecimal("0");
        for (OrderItem orderItem : orderItems
        ) {

            orderTotalPrice = BigDecimalUtils.add(orderItem.getTotalPrice().doubleValue(), orderTotalPrice.doubleValue());
        }
        return orderTotalPrice;
    }
}

