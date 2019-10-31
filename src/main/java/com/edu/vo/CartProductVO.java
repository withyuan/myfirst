package com.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartProductVO implements Serializable {

    private Integer id;//购物车ID
    private Integer userId;//用户ID
    private Integer productId;//商品ID
    private Integer quantity;//商品数量
    private String productName;//商品名称
    private String productSubtitle;//商品的副标题
    private String productMainImage;//商品的主图
    private BigDecimal productPrice;//商品的价格
    private Integer productStatus;//商品的状态
    private BigDecimal productTotalPrice;//商品的总价格
    private Integer productStock;//商品的库存
    private Integer productChecked;//商品是否选中
    private String limitQuantity;//商品的库存描述信息


}
