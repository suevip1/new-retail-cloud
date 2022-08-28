package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.rbac.form.CouponsForm;

public interface SysCouponsService {

    /*
    * 新增优惠券
    * */
    Integer insertCoupons(CouponsForm form);

}
