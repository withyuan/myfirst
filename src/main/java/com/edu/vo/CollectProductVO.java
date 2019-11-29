package com.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CollectProductVO implements Serializable {

    private Integer id;//收藏品ID
    private Integer userId;//用户ID
    private Integer productId;//商品ID
    private String productName;//商品名称
    private String productSubtitle;//商品的副标题
    private String productMainImage;//商品的主图
    private BigDecimal productPrice;//商品的价格
    private Integer productStatus;//商品的状态


}
