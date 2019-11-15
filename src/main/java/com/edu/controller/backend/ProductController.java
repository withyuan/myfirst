package com.edu.controller.backend;

import com.edu.common.ServerResponse;
import com.edu.pojo.Product;
import com.edu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品接口
 */
@CrossOrigin
@RestController
@RequestMapping("/manage/product")
public class ProductController {
    @Autowired
    IProductService productService;

    /**
     * 商品添加/product/save.do
     */
    @RequestMapping("save.do")
    public ServerResponse addOrUpdate(Product product){

        return productService.addOrUpdate(product);


    }
    /**
     * 商品上下架
     * /manage/product/set_sale_status.do
     */
    @RequestMapping("set_sale_status.do")
    public ServerResponse setSaleStatus(Integer productId,Integer status){

        return productService.setSaleStatus(productId, status);


    }

    /**
     * 搜索接口/manage/product/search.do
     * @param productName 商品名字
     * @param productId 商品ID
     * @param pageNum   默认1 第几页
     * @param pageSize 默认10 一页几个
     * @return
     */
    @RequestMapping(value = "search.do")
    public  ServerResponse  search(@RequestParam(name = "productName",required = false)String productName,
                                   @RequestParam(name="productId",required =false )Integer productId,
                                   @RequestParam(name="pageNum",required = false ,defaultValue = "1")Integer pageNum,
                                   @RequestParam(name="pageSize",required = false ,defaultValue = "10")Integer pageSize
                                  ) {

        return   productService.search(productName, productId, pageNum, pageSize);

    }

    /**
     * 查看商品详情
     *  /manage/product/detail.do
     */
    @RequestMapping(value = "/{productId}")
    public ServerResponse detail(@PathVariable("productId") Integer productId){

        return  productService.detail(productId);



    }







}


