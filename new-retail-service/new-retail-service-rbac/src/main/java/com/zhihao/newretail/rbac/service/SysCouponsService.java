package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rbac.form.CouponsForm;

public interface SysCouponsService {

    /*
     * 优惠券列表
     * */
    PageUtil<CouponsApiVO> listCouponsApiVOS(Integer saleable, Integer pageNum, Integer pageSize);

    /*
    * 新增优惠券
    * */
    Integer insertCoupons(CouponsForm form);

    /*
    * 修改优惠券
    * */
    Integer updateCoupons(Integer couponsId, CouponsForm form);

}
