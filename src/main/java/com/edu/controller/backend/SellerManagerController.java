package com.edu.controller.backend;

import com.edu.common.ServerResponse;
import com.edu.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理商家控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/manage/seller/")
public class SellerManagerController {
        @Autowired
    ISellerService sellerService;
    //查看商家
    @RequestMapping(value = "/show")
    public ServerResponse showAllSellers(  @RequestParam(name="pageNum",required = false ,defaultValue = "1")Integer pageNum,
                                           @RequestParam(name="pageSize",required = false ,defaultValue = "10")Integer pageSize){

        return sellerService.showAllSellers(pageNum,pageSize);
    }
    //根据商家店铺或者公司名字查询商家
    @RequestMapping(value = "/search")
    public ServerResponse searchByName( @RequestParam(name = "name",required = false)String name,
                                        @RequestParam(name="nickName",required =false )String  nickName,
            @RequestParam(name="pageNum",required = false ,defaultValue = "1")Integer pageNum,
                                           @RequestParam(name="pageSize",required = false ,defaultValue = "10")Integer pageSize){

        return sellerService.searchByName(name,nickName,pageNum,pageSize);
    }
    //更新状态
    @RequestMapping(value = "/update")
    public ServerResponse updateStatus(String sellerId,String status){

        return  sellerService.updateStatus(sellerId,status);
    }


}
