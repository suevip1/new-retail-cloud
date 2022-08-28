package com.zhihao.newretail.coupons.service;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;

import java.util.List;
import java.util.Set;

public interface CouponsService {

    /*
    * 获取优惠券信息
    * */
    CouponsApiVO getCouponsApiVO(Integer couponsId);

    /*
    * 获取优惠券列表
    * */
    List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet);

    /*
    * 新增优惠券
    * */
    int insertCoupons(CouponsAddApiDTO couponsAddApiDTO);

}
