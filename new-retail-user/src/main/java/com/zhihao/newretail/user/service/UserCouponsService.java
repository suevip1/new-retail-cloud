package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.product.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.user.pojo.UserCoupons;

import java.util.List;

public interface UserCouponsService {

    /*
     * 获取用户优惠券信息
     * */
    CouponsApiVO getCouponsApiVO(Integer couponsId);

    /*
     * 获取用户优惠券列表
     * */
    List<CouponsApiVO> listCouponsApiVOS(Integer userId);

    /*
     * 获取优惠券信息
     * */
    UserCoupons getUserCoupons(Integer couponsId);

    /*
     * 更新优惠券数量
     * */
    int updateUserCoupons(UserCouponsApiDTO userCouponsApiDTO);

    /*
     * 更新优惠券信息
     * */
    void updateUserCoupons(UserCoupons userCoupons);

    /*
    * 获取优惠券列表(Feign)
    * */
    List<UserCouponsApiVO> listUserCouponsApiVOS(Integer userId);

}
