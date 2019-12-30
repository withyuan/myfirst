package com.edu.controller.seller;

import com.edu.common.ServerResponse;
import com.edu.pojo.Seller;
import com.edu.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家控制类
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/seller/")
public class SellerController {
    @Autowired
    ISellerService sellerService;
    /**
     * 入驻接口  添加
     *
     */
    @RequestMapping(value= "add")
    public ServerResponse add(Seller seller){
        return  sellerService.add(seller);
    }

}
