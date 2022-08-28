package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.form.CouponsForm;
import com.zhihao.newretail.rbac.service.SysCouponsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCouponsServiceImpl implements SysCouponsService {

    @Autowired
    private CouponsFeignService couponsFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<CouponsApiVO> listCouponsApiVOS(Integer saleable, Integer pageNum, Integer pageSize) {
        return couponsFeignService.listCouponsApiVOS(saleable, pageNum, pageSize);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer insertCoupons(CouponsForm form) {
        CouponsAddApiDTO couponsAddApiDTO = new CouponsAddApiDTO();
        BeanUtils.copyProperties(form, couponsAddApiDTO);
        return couponsFeignService.insertCoupons(couponsAddApiDTO);
    }

}
