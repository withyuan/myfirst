package com.edu.dao;

import com.edu.pojo.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {

    int deleteByPrimaryKey(Integer id);


    Collect findCollectByUserIdAndProductId(@Param("userid") Integer userId, @Param("productid") Integer productId);

    int insert(Collect record);


    Collect selectByPrimaryKey(Integer id);

    List<Collect> selectAll();


    int updateByPrimaryKey(Collect record);


    /**
     * 根据用户ID查询收藏
     */
    List<Collect> findCollectByUserId(@Param("userid") Integer userId);


    /**
     * 通过用户名和产品号删除某个产品
     */
    Integer deleteByUserIdAndProductIds(@Param("userid") Integer userId, @Param("productIds") List<Integer> productIds);



}