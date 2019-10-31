package com.edu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class OrderVO {

    private Long orderNo;//订单号
    private BigDecimal payment;//实际付款金额
    private Integer paymentType;//支付类型
    private String paymentTypeDesc;//支付类型描述
    private Integer postage;//运费
    private Integer status;//订单状态
    private String statusDesc;//状态描述
    private String paymentTime;//支付时间
    private String sendTime;//发货时间
    private String endTime;//结束时间
    private String closeTime;//订单关闭时间
    private String createTime;//订单创建时间

    private List<OrderItemVO> orderItemVoList;//订单明细
    private String  imageHost;//
    private Integer shippingId;//地址号
    private String receiverName;
    private ShippingVO shippingVo;


}