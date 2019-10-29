package com.edu.common;

/**
 * 购物车商品是否选择枚举类
 */
public enum CheckEnum {

    CART_PRODUCT_CHECK(1,"已选中"),
    CART_PRODUCT_UNCHECK(0,"未选中")

    ;
    private  int check;
    private  String desc;

    CheckEnum(int role, String desc) {
        this.check = role;
        this.desc = desc;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getCheck() {
        return check;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
