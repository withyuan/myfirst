package com.edu.vo;

import lombok.Data;

@Data
public class CategoryVO {
    private Integer id;
    private Integer parentId;
    private String name;
    private Boolean status;
    private String mainImage;
    private Integer sortOrder;
    private String createTime;
    private String updateTime;
}
