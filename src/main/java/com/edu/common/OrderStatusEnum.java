package com.edu.common;

public enum OrderStatusEnum {

    ORDER_CANCEL(0,"已经取消"),
    ORDER_NO_PAY(10,"未付款"),
    ORDER_PAYED(20,"已付款"),
    ORDER_SEND(40,"已发货"),
    ORDER_SUCCESS(50,"交易成功"),
    ORDER_CLOSE(60,"交易关闭")


    ;

    private  int status;
    private  String desc;
    OrderStatusEnum(int status,String desc){
        this.status=status;
        this.desc=desc;
    }
    //枚举的遍历

    public  static  OrderStatusEnum codeOf(Integer status){
//        values()枚举的数组
        for (OrderStatusEnum orderStatusEnum :values()
                ) {
            if (orderStatusEnum.getStatus()==status){
                return orderStatusEnum;
            }

        }
        return null;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
