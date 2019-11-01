package com.edu.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.edu.alipay.Main;
import com.edu.common.*;
import com.edu.controller.front.OrderController;
import com.edu.dao.OrderItemMapper;
import com.edu.dao.OrderMapper;
import com.edu.dao.PayInfoMapper;
import com.edu.pojo.*;
import com.edu.service.ICartService;
import com.edu.service.IOrderService;
import com.edu.service.IProductService;
import com.edu.service.IShippingService;
import com.edu.untils.BigDecimalUtils;
import com.edu.untils.DateUtils;
import com.edu.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static javafx.scene.input.KeyCode.R;
import static javafx.scene.input.KeyCode.S;

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
    @Autowired
    PayInfoMapper payInfoMapper;
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

    @Override
    public ServerResponse pay(Integer userId, Long orderNo) {
        //参数校验
        if (orderNo == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单号必须有");
        }
        //订单号 查询信息
        Order order = orderMapper.findOrderByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单不存在");
        }
        //如果订单号不是未支付状态
            if(order.getStatus()!=OrderStatusEnum.ORDER_NO_PAY.getStatus()){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "此订单状态不是未支付");
            }

        return prepay(order);
    }

    @Override

    public String callback(Map<String, String> requestParams) {
        //1.获取参数信息
        //订单号
        String orderNo = requestParams.get("out_trade_no");
        //流水号
        String trade_no=requestParams.get("trade_no");
        //支付状态
        String trade_status=requestParams.get("trade_status");
        //付款的时间
        String payment_time=requestParams.get("gmt_payment");
        //2.根据订单号 查询订单
        Order order = orderMapper.findOrderByOrderNo(Long.parseLong(orderNo));
        if(order==null){
            return  "fail";
        }
        if(trade_status.equals("TRADE_SUCCESS")){
            //支付成功的     修改订单状态
            order.setStatus(OrderStatusEnum.ORDER_PAYED.getStatus());
            order.setPaymentTime(DateUtils.strToDate(payment_time));
            int result= orderMapper.updateByOrderNo(order);
            if(result<=0){
                return "fail";
            }

        }
        //添加支付记录
        PayInfo payInfo=new PayInfo();
        payInfo.setOrderNo(Long.valueOf(orderNo));
        payInfo.setUserId(order.getUserId());
        payInfo.setPayPlatform(PayTypeEnum.ONLINE_PAYMENT.getCode());
        payInfo.setPlatformNumber(trade_no);
        payInfo.setPlatformStatus(trade_status);
        int pay_result = payInfoMapper.insert(payInfo);
        if (pay_result<=0){
            return "fail";
        }
        return  "success";

    }

    @Override
    public ServerResponse orderPay_status(Long orderNo) {
        if(orderNo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单号毕传");
        }
        Order order= orderMapper.findOrderByOrderNo(orderNo);
        if(order==null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单不存在");
        }
        if(order.getStatus()==OrderStatusEnum.ORDER_PAYED.getStatus()){
            return ServerResponse.createServerResponseBySuccess(true);
        }
        return ServerResponse.createServerResponseBySuccess(false);
    }

    @Override
    public ServerResponse get_order_cart_product(Integer userId) {
        //查询购物车
        ServerResponse<List<Cart>> serverResponse = cartService.findCartsByUserIdAndChecked(userId);
                //step2:List<Cart>-->List<OrderItem>
        List<Cart> cartList = serverResponse.getData();
        //3.List<Cart>-->List<OrderItem>
        ServerResponse orderItems_serverResponse = getCartOrderItem(userId, cartList);
        if (!orderItems_serverResponse.isSuccess()) {
            return orderItems_serverResponse;
        }
        CartOrderItemVO cartOrderItemVO=new CartOrderItemVO();
        cartOrderItemVO.setImageHost(imageHost);
        List<OrderItem> orderItemList = (List<OrderItem>) orderItems_serverResponse.getData();
        List<OrderItemVO> orderItemVOList=Lists.newArrayList();
        for(OrderItem orderItem:orderItemList){
            orderItemVOList.add(assembleOrderItemVO(orderItem));
        }
        cartOrderItemVO.setOrderItemVoList(orderItemVOList);
        cartOrderItemVO.setProductTotalPrice(getOrderTotalPrice(orderItemList));
        return ServerResponse.createServerResponseBySuccess(cartOrderItemVO);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList= Lists.newArrayList();
        if(userId==null){
            //查询所有
            orderList = orderMapper.selectAll();

        }else {
            //查询当前用户
           orderList= orderMapper.findOrderByUserId(userId);
        }
        if(orderList==null||orderList.size()==0){
            return  ServerResponse.createServerResponseByError("未查询到订单信息");
        }
        List<OrderVO> orderVOList=Lists.newArrayList();
        for(Order order:orderList){
            List<OrderItem> orderItemList=   orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
           ServerResponse serverResponse=assembleOrderVO(order,orderItemList,order.getShippingId());
            if(!serverResponse.isSuccess()){
                return serverResponse;
            }
            OrderVO orderVO = (OrderVO)serverResponse.getData();
           orderVOList.add(orderVO);

        }
        PageInfo pageInfo=new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse detail(Long orderNo) {
        //非空校验
        if(orderNo==null){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");

        }
        Order order= orderMapper.findOrderByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "该订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(orderNo);
        ServerResponse serverResponse = assembleOrderVO(order, orderItemList, order.getShippingId());
        if(!serverResponse.isSuccess()){

            return serverResponse;
        }
        OrderVO orderVO = (OrderVO) serverResponse.getData();
        return ServerResponse.createServerResponseBySuccess(orderVO);
    }

    @Override
    public ServerResponse concel(Integer userId, Long orderNo) {
        //step1:参数非空校验
        if(orderNo==null){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }//step2:根据userId和orderNO查询订单
        Order order=orderMapper.findOrderByUserIdAndOrderNo(userId, orderNo);
        if(order==null){
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR,"订单不存在");
        }
        if(order.getStatus()!=OrderStatusEnum.ORDER_NO_PAY.getStatus()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单状态不是未付款 不可取消");
        }
        order.setStatus(OrderStatusEnum.ORDER_CANCEL.getStatus());
        int result = orderMapper.updateByPrimaryKey(order);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单取消失败");
        }
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
        //遍历订单明细
        for (OrderItem orderItem:orderItemList
             ) {
          ServerResponse<Product> serverResponse = productService.findProductById(orderItem.getProductId());
            Product product = serverResponse.getData();
            //去修改库存
            int stock=  product.getStock()+orderItem.getQuantity();
            ServerResponse serverResponse1 = productService.reduceProductStock(orderItem.getProductId(), stock);
            if(!serverResponse1.isSuccess()){
                return serverResponse1;
            }
        }

        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse send_goods(Long orderNo) {
        //非空判断
        if(orderNo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "订单号需要传");
        }
        Order order= orderMapper.findOrderByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "该订单不存在");
        }
        //判断该订单是否是已经付款状态
        if(order.getStatus()!=OrderStatusEnum.ORDER_PAYED.getStatus()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "还没付款呢或者订单取消了");
        }
        //修改订单状态
        order.setStatus(OrderStatusEnum.ORDER_SEND.getStatus());
       int result= orderMapper.updateOrderSend(order);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "发货失败");

        }

        return ServerResponse.createServerResponseBySuccess("发货成功");
    }

    private static Log log = LogFactory.getLog(Main.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService   tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }
    public ServerResponse prepay(Order order) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo =String.valueOf(order.getOrderNo());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "【小彩虹】品牌门店付款消费";
        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品共"+order.getPayment()+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
        //查询订单明细根据订单号orderNo
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
         if(orderItemList==null||orderItemList.size()==0){
        return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "没有购买的商品");
         }

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        for (OrderItem orderItem:orderItemList
             ) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，
            GoodsDetail goods = GoodsDetail.newInstance((String.valueOf(orderItem.getId())),
                    orderItem.getProductName() , orderItem.getCurrentUnitPrice().intValue(),
                    orderItem.getQuantity());
                goodsDetailList.add(goods);
        }


        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                 .setNotifyUrl("http://yjzppj.natappfree.cc/order/callback.do")
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("e:/upload/qr-%s.png",
                        response.getOutTradeNo());
                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                PayVO payVO=new PayVO(order.getOrderNo(),imageHost+"qr-"+response.getOutTradeNo()+".png");
                return  ServerResponse.createServerResponseBySuccess(payVO);

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "失败");
    }

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
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
        orderVO.setPaymentTime(DateUtils.dateToStr(order.getPaymentTime()));
        orderVO.setCreateTime(DateUtils.dateToStr(order.getCreateTime()));
        orderVO.setCloseTime(DateUtils.dateToStr(order.getCloseTime()));
        orderVO.setEndTime(DateUtils.dateToStr(order.getEndTime()));
        orderVO.setSendTime(DateUtils.dateToStr(order.getSendTime()));
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

