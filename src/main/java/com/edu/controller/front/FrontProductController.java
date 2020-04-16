package com.edu.controller.front;

import com.edu.common.ServerResponse;
import com.edu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/product/")
@CrossOrigin
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
     * 前台查看热销商品
     *
     */
    @RequestMapping("hot.do")
    public ServerResponse hotList(
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize
                               ){

        return  productService.hotList(pageNum,pageSize);

    }

    /**
     * 前台查看商品详情
     * /product/detail.do
     * productId
     * response
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse front_detail(
            Integer productId, HttpServletRequest req,
            HttpServletResponse resp){
        String id=productId+"";
        String producthistoryid="";
        Cookie[] cookies=req.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                String name=cookie.getName();
                String value=cookie.getValue();
                if(name.equals("producthistoryid")){
                    //存在producthistoryid这个cookie的话，就将它赋值给producthistoryid这个变量
                    producthistoryid=value;
                }
            }
        }
        if(producthistoryid==null||producthistoryid.equals("")){
            id+="#";
            System.out.println(id);
            Cookie producthistoryCookie=new Cookie("producthistoryid",id);
            resp.addCookie(producthistoryCookie);
        }else{
            //存在producthistoryid这个Cookie时，就将最近浏览的id添加到字符串的最前面
            //要保证最新浏览的商品id在最前面，有两种实现方式，当最下面哪种更简便
            //方式一：
//			String historyid="";
//			List<String> maskList=new ArrayList<String>();
//			String[] masks=producthistoryid.split(",");
//			for (String mask: masks){
//				if(!mask.equals(id)){
//					maskList.add(mask);
//				}
//			}
//         //ConvertUtil是将ArrayList里面的内容转换成字符串的一个帮助类
//			String rs=ConvertUtil.convertByMask(maskList, ",");
//			rs=id+","+rs;
            //或者这样实现，三行代码就可以搞定
            producthistoryid="#"+producthistoryid;
            producthistoryid=producthistoryid.replace("#"+id+"#", "#");
            String rs=id+producthistoryid;

            Cookie producthistoryCookie=new Cookie("producthistoryid",rs);

            System.out.println(producthistoryCookie.getValue());
            System.out.println(producthistoryCookie.getName());
            resp.addCookie(producthistoryCookie);
        }


        return productService.front_detail(productId);
    }

    /**
     * 获取历史
     * @param req
     * @return
     */
    @RequestMapping(value = "history")
    public ServerResponse history(HttpServletRequest req,@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                  @RequestParam(required = false,defaultValue = "10")Integer pageSize) {
        Cookie[] cookies = req.getCookies();
        String[] pids = null;
        for (int i = 0; cookies != null && i < cookies.length; i++)
        {
            System.out.println(cookies[i].getName());
            //找到我们想要的cookie
            if (cookies[i].getName().equals("producthistoryid")) {
                System.out.println(cookies[i].getValue());
                pids = cookies[i].getValue().split("#");
                //得到cookie中存在的id，展现浏览过的商品
            }


        }
        return productService.history(pids, pageNum, pageSize);

    }
    }



















