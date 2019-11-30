package com.edu.dao;

import com.edu.pojo.Evaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluateMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加评价
     * @param record
     * @return
     */
    int insert(Evaluate record);

    /**
     * 查询评价
     * @param productId
     * @return
     */
    List<Evaluate> findByProductId(@Param("productId") Integer productId
    );
    /**
     * 查看该商品有多少评价
     */
    Integer count(@Param("productId")Integer productId);

   /* Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);
    *//**
     * 根据ID修改status
     *//*
    int updateStatusById(@Param("id") Integer id, @Param("status") Integer status);

    *//**
     * 通过给定的类别ID集合来查询该类的产品
     * @param integers 类别ID集合
     * @param keyword      名字
     * @return
     *//*
    List<Product> searchProduct(@Param("integerSet") Set<Integer> integers, @Param("keyword") String keyword);

    *//**
     * 扣库存
     *//*
    int reduceProductStock(@Param("productId") Integer productId, @Param("stock") Integer stock);

    *//**
     * 是否热销
     *//*

    List<Product>   selectIsNotHot();
    *//**
     * 根据ID和name查询数量
     *//*
    Integer selectCount(@Param("productId") Integer productId,
                        @Param("productName") String productName);
*/
}