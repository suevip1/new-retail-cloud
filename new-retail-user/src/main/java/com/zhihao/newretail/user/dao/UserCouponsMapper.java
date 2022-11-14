package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.UserCoupons;

import java.util.List;

public interface UserCouponsMapper {

    int deleteByUserId(Integer userId);

    int insert(UserCoupons record);

    int insertSelective(UserCoupons record);

    UserCoupons selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserCoupons record);

    int updateByPrimaryKey(UserCoupons record);

    /*
    * 查询用户优惠券列表
    * */
    List<UserCoupons> selectListByUserId(Integer userId);

    /*
    * 优惠券id查询
    * */
    UserCoupons selectByCouponsId(Integer couponsId);

}
