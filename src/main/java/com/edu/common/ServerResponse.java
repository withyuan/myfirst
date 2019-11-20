package com.edu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * 服务端返回到前端的高复用的相应对象
 *{
 *
 *     status:0
 *     data:{}
 *     msg:null
 *     //JsonSerialize作用：如果有null 就会移除该字段
 *}
 *
 *
 * */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {

    private int  status;//返回到前端的状态码
    private T data;//返回给前端的数据
    private String msg;//当status！=0时，封装了错误信息
    private int count;//返回总数

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private ServerResponse() {

    }

    public ServerResponse(int status, T data, String msg, int count) {
        this.status = status;
        this.data = data;
        this.msg = msg;
        this.count = count;
    }

    private ServerResponse(int status) {
        this.status = status;
    }
    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;

    }
    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }
    private ServerResponse(int status, T data, int  count) {
        this.status = status;
        this.data = data;
        this.count =count;
    }

    /**
     *调用接口成功时回调
     * */
    public  static  ServerResponse createServerResponseBySuccess(){
        return new ServerResponse(ResponseCode.SUCESS);
    }
    public  static  <T> ServerResponse createServerResponseBySuccess(T data){
        return new ServerResponse(ResponseCode.SUCESS,data);
    }
    public  static  <T> ServerResponse createServerResponseBySuccess(T data,String msg){
        return new ServerResponse(ResponseCode.SUCESS,data,msg);
    }

    public  static  <T> ServerResponse createServerResponseBySuccess(T data,int count){
        return new ServerResponse(ResponseCode.SUCESS,data,count);
    }

    /**
     * 接口调用失败时回调
     * */

    public static  ServerResponse createServerResponseByError(){
        return new ServerResponse(ResponseCode.ERROR);
    }

    public static  ServerResponse createServerResponseByError(String msg){
        return new ServerResponse(ResponseCode.ERROR,msg);
    }
    public static  ServerResponse createServerResponseByError(int status){
        return new ServerResponse(status);
    }
    public static  ServerResponse createServerResponseByError(int status,String msg){
        return new ServerResponse(status,msg);
    }



    /**
     * 判断接口是否正确返回
     *  status==0
     *
     * */
    //JsonIgnore忽略这个字段
    @JsonIgnore
    public    boolean isSuccess(){
        return  this.status==ResponseCode.SUCESS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}