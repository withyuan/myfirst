package com.edu.controller.front;

import com.edu.common.ServerResponse;
import com.edu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product/")
public class FrontProductController {

    @Autowired
    IProductService productService;
    /**
         * 前台产品搜索/product/list.do
     * categoryId
     * keyword
     * pageNum(default=1)
     * pageSize(default=10)
     * orderBy(default="")：排序参数：例如price_desc，price_asc
     */
    @RequestMapping("list.do")
    public ServerResponse list(@RequestParam(required = false) Integer categoryId,
                               @RequestParam(required = false)  String keyword,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                               @RequestParam(required = false,defaultValue = "")String orderBy){

            return  productService.front_list(categoryId, keyword, pageNum, pageSize, orderBy);

    }
    /**
     * 前台查看商品详情
     * /product/detail.do
     * productId
     * response
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse front_detail(Integer productId){

        return productService.front_detail(productId);
    }


















}
