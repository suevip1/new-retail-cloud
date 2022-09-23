package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.form.CouponsForm;
import com.zhihao.newretail.admin.service.SysCouponsService;
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

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer updateCoupons(Integer couponsId, CouponsForm form) {
        CouponsUpdateApiDTO couponsUpdateApiDTO = new CouponsUpdateApiDTO();
        BeanUtils.copyProperties(form, couponsUpdateApiDTO);
        return couponsFeignService.updateCoupons(couponsId, couponsUpdateApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public Integer deleteCoupons(Integer couponsId) {
        return couponsFeignService.deleteCoupons(couponsId);
    }

}
