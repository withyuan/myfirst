package com.edu.dao;

import com.edu.pojo.Seller;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String sellerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    int insert(Seller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    Seller selectByPrimaryKey(String sellerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    List<Seller> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Seller record);
    /**
     * showCount查看总数
     */
   int  showCount();
    /**
     * findSellerByName
     */
    List<Seller> findSellerByName(@Param("name") String name,
                                  @Param("nickName") String nickName);
    //查看个人信息
    Seller show(@Param("sellerId")String sellerId);
}