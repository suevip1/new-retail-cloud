package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.user.pojo.UserCoupons;

import java.util.List;

public interface UserCouponsService {

    /*
    * 获取优惠券列表
    * */
    List<CouponsApiVO> listUserCouponsVOs(Integer userId);

    /*
    * 获取优惠券信息
    * */
    CouponsApiVO getUserCouponsVO(Integer couponsId);

    /*
    * 获取优惠券列表
    * */
    List<UserCouponsApiVO> listUserCouponsApiVOS(Integer userId);

    /*
    * 更新优惠券数量
    * */
    int updateUserCoupons(UserCouponsApiDTO userCouponsApiDTO);

    /*
    * 优惠券信息
    * */
    UserCoupons getUserCouponsByCouponsId(Integer couponsId);

    /*
    * 更新优惠券信息
    * */
    void updateUserCoupons(UserCoupons userCoupons);

}
