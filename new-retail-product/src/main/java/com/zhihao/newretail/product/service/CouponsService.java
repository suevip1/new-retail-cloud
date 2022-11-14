package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.product.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;

import java.util.List;
import java.util.Set;

public interface CouponsService {

    /*
    * 获取优惠券信息
    * */
    CouponsApiVO getCouponsApiVO(Integer couponsId);

    /*
    * 优惠券列表
    * */
    PageUtil<CouponsApiVO> listCouponsApiVOS(Integer saleable, Integer pageNum, Integer pageSize);

    /*
    * 获取优惠券列表
    * */
    List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet);

    /*
    * 新增优惠券
    * */
    int insertCoupons(CouponsAddApiDTO couponsAddApiDTO);

    /*
    * 修改优惠券
    * */
    int updateCoupons(Integer couponsId, CouponsUpdateApiDTO couponsUpdateApiDTO);

    /*
    * 删除优惠券
    * */
    int deleteCoupons(Integer couponsId);

}
