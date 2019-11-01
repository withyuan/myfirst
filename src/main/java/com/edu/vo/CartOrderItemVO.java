package com.edu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartOrderItemVO {
    private List<OrderItemVO> orderItemVoList;
    private String imageHost;
    private BigDecimal productTotalPrice;
}
