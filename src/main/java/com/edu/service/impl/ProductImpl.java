package com.edu.service.impl;

import com.edu.common.ProductStatusEnum;
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
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

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
        if (product == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //sub_images图片地址图片地址，json格式main_image产品主图，url相对地址
        //设置商品主图  用sub_images 1.png,2.png 第一个作为主图
        String subImages = product.getSubImages();
        if (subImages != null && !subImages.equals("")) {
            String[] subImageArr = subImages.split(",");//根据逗号分隔
            if (subImageArr.length > 0) {
                //设置商品主图
                product.setMainImage(subImageArr[0]);
            }

        }


        Integer id = product.getId();
        System.out.println(product.getCategoryId());
        if (id == null) {
            int result = productMapper.insert(product);
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加失败");
            } else {
                return ServerResponse.createServerResponseBySuccess("添加成功");
            }
            //添加
        } else {
            int result = productMapper.updateByPrimaryKey(product);
            //更新
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "更新失败");
            } else {
                return ServerResponse.createServerResponseBySuccess("更新成功");
            }
        }

    }

    @Override
    public ServerResponse setSaleStatus(Integer Id, Integer status) {
        //参数不能为空
        if (Id == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if (status == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product p = productMapper.selectByPrimaryKey(Id);
        if (p == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.UNEXIST_P, "该商品不存在");
        }
        int result = productMapper.updateStatusById(Id, status);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "修改失败");
        }

        return ServerResponse.createServerResponseBySuccess("修改产品状态成功");


    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        if (productName != null && !productName.equals("")) {
            productName = "%" + productName + "%";
        }
        //执行之前
        PageHelper.startPage(pageNum, pageSize);

        List<Product> products = productMapper.findProductsByNameAndId(productId, productName);
        List<ProductListVO> productListVOS = Lists.newArrayList();
        // List<Product>---> List<ProductListVO>
        if (products != null && products.size() > 0) {
            for (Product product : products
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
        if (productId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createServerResponseBySuccess();
        }
        //product-->productDetailVo
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        return ServerResponse.createServerResponseBySuccess(productDetailVO);
    }


    /**
     * 前台搜索
     *
     * @param categoryId 类别ID
     * @param keyword    产品名字
     * @param pageNum    第几页
     * @param pageSize   一页多少个
     * @param orderBy    什么顺序 price_desc，price_asc
     * @return
     */
    @Override
    public ServerResponse front_list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        //step1:参数校验 categoryId和keyword不能同时为空
        if (categoryId == null && (keyword == null || keyword.equals(""))) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数错误");
        }
        //categoryId
        //建立一个存放ID的容器
        Set<Integer> integers = Sets.newHashSet();
        if (categoryId != null) {
            //去查询这个类别ID的类别
            ServerResponse<Category> categoryServerResponse = categoryService.selectCategoryById(categoryId);
            Category category = categoryServerResponse.getData();
            if (category == null && (keyword == null || keyword.equals(""))) {
                //商品不存在
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.createServerResponseBySuccess(pageInfo);
            }
            //去查询所有该类别的类别号及其子类
            ServerResponse serverResponse = categoryService.deepCategory(categoryId);
            if (serverResponse.isSuccess()) {
                integers = (Set<Integer>) serverResponse.getData();//把这些类别号存到容器中
            }
        }
        //keyword
        if (keyword != null && !keyword.equals("")) {
            keyword = "%" + keyword + "%";//模糊查询拼接字符串
        }
        if (orderBy.equals("")) {
            //如果是默认的查询顺序
            PageHelper.startPage(pageNum, pageSize);
        } else {
            //有规定的查询顺序如：price_desc，price_asc
            String[] orderByArr = orderBy.split("_");//通过_分隔字符串
            if (orderByArr.length > 1) {
                //拼接字符串 按照price desc查询(降序)或者 price asc(升序)
                PageHelper.startPage(pageNum, pageSize, orderByArr[0] + " " + orderByArr[1]);
            } else {
                PageHelper.startPage(pageNum, pageSize);//否则的话只能默认了
            }
        }
        //开始放入数据并且把POJO转换成VO
        //查询通过上边的
        //获得存放POJO的容器
        List<Product> productList = productMapper.searchProduct(integers, keyword);
        //定义VO容器
        List<ProductListVO> productListVOS = Lists.newArrayList();
        if (productList != null && productList.size() > 0) {
            for (Product p : productList
            ) {
                ProductListVO productListVO = assembleProductListVO(p);//调用方法把POJO转换成VO
                productListVOS.add(productListVO);//转换好的存放到VO容器中
            }
        }
        //分页
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productListVOS);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse front_detail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品不存在");
        }
        //产品状态验证
        if (product.getStatus() != ProductStatusEnum.PRODUCT_ONLINE.getCode()) {
            return ServerResponse.createServerResponseByError(ResponseCode.NO_PRODUCT, "商品已下架或删除");
        }

        //product-->productDetailVo
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        return ServerResponse.createServerResponseBySuccess(productDetailVO);


    }

    /**
     * 查询商品的库存
     * @param productId 商品ID
     * @return
     */
    @Override
    public ServerResponse<Product> findProductById(Integer productId) {
        if(productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品Id必须传");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品不存在");
        }
        return ServerResponse.createServerResponseBySuccess(product);
    }

    @Override
    public ServerResponse reduceProductStock(Integer productId, Integer stock) {
        if(productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品Id必须传");
        }
        if(stock==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "库存参数必须传");
        }
        int result = productMapper.reduceProductStock(productId, stock);
        if(result<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "扣库存失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse<Product> selectProduct(Integer productId) {
        if (productId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseBySuccess(product);
    }


    private ProductListVO assembleProductListVO(Product product) {
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return productListVO;
    }

    private ProductDetailVO assembleProductDetailVO(Product product) {

        ProductDetailVO productDetailVO = new ProductDetailVO();
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
        if (category != null) {
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        return productDetailVO;
    }
}
