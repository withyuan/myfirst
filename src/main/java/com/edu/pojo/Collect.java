package com.edu.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏类
 */
@Data
public class Collect implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private Date createTime;
    private Date updateTime;
    private static final long serialVersionUID = 1L;

}
