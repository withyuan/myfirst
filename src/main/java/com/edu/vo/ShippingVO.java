package com.edu.vo;


import lombok.Data;

import java.io.Serializable;
@Data
public class ShippingVO  implements Serializable{

    private String receiverName;
    private String receiverPhone;


    private String receiverMobile;


    private String receiverProvince;


    private String receiverCity;


    private String receiverDistrict;


    private String receiverAddress;


    private String receiverZip;



}