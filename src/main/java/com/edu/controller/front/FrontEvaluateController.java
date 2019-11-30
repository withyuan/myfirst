package com.edu.controller.front;

import com.edu.common.ServerResponse;
import com.edu.pojo.Evaluate;
import com.edu.pojo.User;
import com.edu.service.IEvaluateService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/evaluate/")
@CrossOrigin
public class FrontEvaluateController {
    @Autowired
    IEvaluateService evaluateService;

    /**
         * 前台产品搜索/product/list.do
     * categoryId
     * keyword
     * pageNum(default=1)
     * pageSize(default=10)
     * orderBy(default="")：排序参数：例如price_desc，price_asc
     */
   /* @RequestMapping("list.do")
    public ServerResponse list(@RequestParam(required = false) Integer categoryId,
                               @RequestParam(required = false)  String keyword,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                               @RequestParam(required = false,defaultValue = "")String orderBy){

            return  productService.front_list(categoryId, keyword, pageNum, pageSize, orderBy);

    }*/
    /**
     * 添加评价/evaluate/save.do
     */
    @RequestMapping("save.do")
    public ServerResponse add(Evaluate evaluate, HttpSession session){
        if(evaluate.getUserId()==null){
            User user = (User) session.getAttribute(Const.CURRENT_USER);
            Integer userId = user.getId();
            evaluate.setUserId(userId);
        }else{
            evaluate.setUserId(1);
        }

        return evaluateService.add(evaluate);
    }
    /**
     * 查看评价 查看该商品所有人对他的评价
     */
    @RequestMapping("show")
    public  ServerResponse show(Integer productId){
        return evaluateService.show(productId);
    }
















}
