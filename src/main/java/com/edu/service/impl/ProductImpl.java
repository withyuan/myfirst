package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.ProductMapper;
import com.edu.pojo.Category;
import com.edu.pojo.Product;
import com.edu.service.ICategoryService;
import com.edu.service.IProductService;
import com.edu.untils.DateUtils;
import com.edu.vo.ProductDetailVO;
import com.edu.vo.ProductListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductImpl implements IProductService {


    @Value("${business.imageHost}")
    private String imageHost;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ICategoryService categoryService;
    @Override
    public ServerResponse addOrUpdate(Product product) {
        if(product==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //sub_images图片地址图片地址，json格式main_image产品主图，url相对地址
        //设置商品主图  用sub_images 1.png,2.png 第一个作为主图
        String subImages=product.getSubImages();
        if(subImages!=null&&!subImages.equals("")){
            String[] subImageArr = subImages.split(",");//根据逗号分隔
            if (subImageArr.length>0){
                    //设置商品主图
                    product.setMainImage(subImageArr[0]);
            }

        }




        Integer id = product.getId();
        System.out.println(product.getCategoryId());
        if (id==null){
            int result = productMapper.insert(product);
            if (result<=0){
                return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加失败");
            }else {
                return ServerResponse.createServerResponseBySuccess("添加成功");
            }
            //添加
        }else {
            int result = productMapper.updateByPrimaryKey(product);
            //更新
            if (result<=0){
                return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新失败");
            }else {
                return ServerResponse.createServerResponseBySuccess("更新成功");
            }
        }

    }

    @Override
    public ServerResponse setSaleStatus(Integer Id, Integer status) {
        //参数不能为空
        if(Id==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if(status==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product p = productMapper.selectByPrimaryKey(Id);
        if(p==null){
            return  ServerResponse.createServerResponseByError(ResponseCode.UNEXIST_P, "该商品不存在");
        }
      int result=  productMapper.updateStatusById(Id,status);
     if(result<=0){
        return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "修改失败");
    }

    return ServerResponse.createServerResponseBySuccess("修改产品状态成功");




    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        if(productName!=null&&!productName.equals("")) {
            productName = "%" + productName + "%";
        }
        //执行之前
        PageHelper.startPage(pageNum,pageSize);

        List<Product> products = productMapper.findProductsByNameAndId(productId, productName);
        List<ProductListVO> productListVOS= Lists.newArrayList();
        // List<Product>---> List<ProductListVO>
        if(products!=null&&products.size()>0){
            for (Product product:products
                 ) {
                ProductListVO productListVO = assembleProductListVO(product);
                productListVOS.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(productListVOS);


        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse detail(Integer productId) {
        if(productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return  ServerResponse.createServerResponseBySuccess();
        }
        //product-->productDetailVo
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        return ServerResponse.createServerResponseBySuccess(productDetailVO);
    }

    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO=new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return  productListVO;
    }
    private ProductDetailVO assembleProductDetailVO(Product product){

        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(imageHost);
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
//        Category category= categoryMapper.selectByPrimaryKey(product.getCategoryId());
        ServerResponse<Category> serverResponse = categoryService.selectCategoryById(product.getCategoryId());
        Category category = serverResponse.getData();
       productDetailVO.setParentCategoryId(category.getParentId());

        return productDetailVO;
    }
}
