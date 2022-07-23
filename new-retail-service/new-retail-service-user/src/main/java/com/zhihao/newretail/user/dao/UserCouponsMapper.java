package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.UserCoupons;

public interface UserCouponsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupons record);

    int insertSelective(UserCoupons record);

    UserCoupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupons record);

    int updateByPrimaryKey(UserCoupons record);

}
