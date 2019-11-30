package com.edu.pojo;
import lombok.Data;

import java.util.Date;
/**
 * 评价POJO
 */
@Data
public class Evaluate {
    private  Integer id;//主键ID
    private  Integer userId;//用户名
    private  Integer productId;//商品ID
    private  String detail;//评价内容
    private  String img;//评价图片
    private  Integer grade;//评价等级
    private Long orderNo;//订单号
    private  Date createTime;//创建日期
}
