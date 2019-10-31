package com.edu.common;

public enum PayTypeEnum {
    ONLINE_PAYMENT(1,"在线支付"),
    PAY_DELIVERY(2,"货到付款")
    ;
    private  int  code;
    private String desc;
    private PayTypeEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public  static PayTypeEnum codeOf(Integer code){
        for (PayTypeEnum payTypeEnum:values()
             ) {
            if(payTypeEnum.getCode()==code){
                return payTypeEnum;
            }

        }
        return  null;

    }
}
