package com.edu.common;

public enum EvaluateGradeEnum {

    GOOD_REPUTATION(0,"好评"),
    MEDIUM_REPUTATION(1,"中评"),
    BADE_REPUTATION(2,"差评"),


    ;

    private  int status;
    private  String desc;
    EvaluateGradeEnum(int status, String desc){
        this.status=status;
        this.desc=desc;
    }
    //枚举的遍历

    public  static EvaluateGradeEnum codeOf(Integer status){
//        values()枚举的数组
        for (EvaluateGradeEnum orderStatusEnum :values()
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
