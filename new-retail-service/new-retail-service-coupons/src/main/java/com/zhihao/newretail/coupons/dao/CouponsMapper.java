package com.zhihao.newretail.coupons.dao;

import com.zhihao.newretail.coupons.pojo.Coupons;
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

    /*
    * 批量查询
    * */
    List<Coupons> selectListByCouponsIdSet(@Param("couponsIdSet") Set<Integer> couponsIdSet);

}
