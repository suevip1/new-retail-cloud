package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.pay.feign.PayInfoFeignService;
import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysPayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPayInfoServiceImpl implements SysPayInfoService {

    @Autowired
    private PayInfoFeignService payInfoFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<PayInfoApiVO> listPayInfoApiVOS(Long orderId, Integer userId, Integer payPlatform,
                                                    Integer status, Integer platformNumber, Integer pageNum, Integer pageSize) {
        return payInfoFeignService.listPayInfoApiVOS(orderId, userId, payPlatform, status, platformNumber, pageNum, pageSize);
    }

}
