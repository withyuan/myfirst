package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.CollectMapper;
import com.edu.pojo.Collect;
import com.edu.pojo.Product;
import com.edu.service.ICollectService;
import com.edu.service.IProductService;
import com.edu.vo.CollectProductVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车实现类
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    IProductService productService;
    @Autowired
    CollectMapper collectMapper;

    @Override
    public ServerResponse addProductToCollect(Integer userId, Integer productId) {
        //1.参数的非空判断
        if (productId == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品ID必须传");
        }
        //2.判断商品是否存在
        ServerResponse<Product> serverResponse = productService.findProductById(productId);
        if (!serverResponse.isSuccess()) {
            return ServerResponse.createServerResponseByError(serverResponse.getStatus(), serverResponse.getMsg());
        } else {
            Product product = serverResponse.getData();
            if (product.getStock() <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "商品售空");
            }
        }
        //3.判断商品是否在收藏夹里，如果在 增加数量，如果不在添加到购物车
        Collect collect = collectMapper.findCollectByUserIdAndProductId(userId, productId);
        if (collect == null) {
            //用户没有添加购物车
            Collect newCollect=new Collect();
            newCollect.setUserId(userId);
            newCollect.setProductId(productId);
            //默认未选中
            int result = collectMapper.insert(newCollect);
            if (result <= 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "添加失败");
            }
        } else {
            //该商品已经在您的收藏夹里了
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "该商品已经在您的收藏夹里了");
        }
        //4.封装购物车对象CartVO
        List<CollectProductVO> collectProductVOS = getCollectVO(userId);
        //5.返回VO
        return ServerResponse.createServerResponseBySuccess(collectProductVOS,"收藏成功！");
    }

    @Override
    public ServerResponse list(Integer userId) {
        //查看该用户购物车根据用户号
        List<CollectProductVO> collectProductVOS = getCollectVO(userId);
        return ServerResponse.createServerResponseBySuccess(collectProductVOS);
    }


    @Override
    public ServerResponse deleteProduct(String productIds, Integer userId) {
        //step1:参数非空校验
        if (productIds == null || productIds.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //productIds-->List<Integer>
        List<Integer> productList = Lists.newArrayList();
        String[] productIdArr = productIds.split(",");
        if (productIdArr != null && productIdArr.length > 0) {
            for (String p : productIdArr
            ) {
                //将字符串转成INT
                Integer productId = Integer.parseInt(p);
                productList.add(productId);
            }
        }
        //调用DAO
        collectMapper.deleteByUserIdAndProductIds(userId, productList);
        return ServerResponse.createServerResponseBySuccess(getCollectVO(userId));
    }






    private List<CollectProductVO> getCollectVO(Integer userId) {
        //根据userID查询购物信息-->List<Cart>
        List<Collect> collectList = collectMapper.findCollectByUserId(userId);
        if (collectList == null || collectList.size() == 0) {
            return null;
        }
        //List<Cart>---List<CartProductVO>
        List<CollectProductVO> collectProductVOS = Lists.newArrayList();
        for (Collect collect : collectList
        ) {
            //collect--?collectProductVO
            CollectProductVO collectProductVO = new CollectProductVO();

            collectProductVO.setId(collect.getId());
            collectProductVO.setUserId(userId);
            collectProductVO.setProductId(collect.getProductId());
            ServerResponse<Product> serverResponse = productService.findProductById(collect.getProductId());
            if (serverResponse.isSuccess()) {
                Product product = serverResponse.getData();
                collectProductVO.setProductName(product.getName());
                collectProductVO.setProductStatus(product.getStatus());
                collectProductVO.setProductSubtitle(product.getSubtitle());
                collectProductVO.setProductMainImage(product.getMainImage());
                collectProductVO.setProductPrice(product.getPrice());

                collectProductVOS.add(collectProductVO);
            }
        }
        //计算购物车总价格

        return collectProductVOS;

    }


}
