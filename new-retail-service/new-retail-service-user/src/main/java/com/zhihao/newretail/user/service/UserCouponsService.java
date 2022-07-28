package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;

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
    List<UserCouponsApiVO> listUserCouponsApiVOs(Integer userId);

}
