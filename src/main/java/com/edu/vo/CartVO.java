package com.edu.vo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 购物车实体类CarVO
 * */
@Data
@NoArgsConstructor
public class CartVO  implements Serializable{
    //购物信息集合
    private List<CartProductVO> cartProductVOList;
    //是否全选
    private boolean isallchecked;
    //总价格
    private BigDecimal carttotalprice;


}