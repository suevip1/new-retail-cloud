package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Coupons;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CouponsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupons record);

    int insertSelective(Coupons record);

    Coupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);

    int countBySaleable(Integer saleable);

    /*
    * 批量查询
    * */
    List<Coupons> selectListByCouponsIdSet(@Param("couponsIdSet") Set<Integer> couponsIdSet);

    List<Coupons> selectListBySaleable(@Param("saleable") Integer saleable,
                                       @Param("pageNum") Integer pageNum,
                                       @Param("pageSize") Integer pageSize);

}
