package com.edu.controller.front;

import com.edu.common.ServerResponse;
import com.edu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台查看类别类别接口
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
public class FrontCategoryController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查看类别接口
     * @param
     * @return
     */
    @RequestMapping("/show")
    public ServerResponse showCategroy( @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                        @RequestParam(required = false,defaultValue = "20")Integer pageSize){

        return  categoryService.showCategory(pageNum,pageSize);
    }
}
