package com.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageVo {
    private String uri;//图片的名字
    private  String url;//图片的完整路径

}
