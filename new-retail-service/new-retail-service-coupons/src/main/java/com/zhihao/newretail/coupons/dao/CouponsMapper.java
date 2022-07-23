package com.zhihao.newretail.coupons.dao;

import com.zhihao.newretail.coupons.pojo.Coupons;

public interface CouponsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupons record);

    int insertSelective(Coupons record);

    Coupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);

}
