package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.order.feign.OrderFeignService;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOrderServiceImpl implements SysOrderService {

    @Autowired
    private OrderFeignService orderFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<OrderApiVO> listOrderApiVOSByPage(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        return orderFeignService.listOrderApiVOSByPage(orderNo, userId, status, pageNum, pageSize);
    }

}
