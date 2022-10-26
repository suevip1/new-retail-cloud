package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.admin.form.OrderShippedForm;
import com.zhihao.newretail.api.order.dto.OrderLogisticsInfoAddApiDTO;
import com.zhihao.newretail.api.order.feign.OrderFeignService;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOrderServiceImpl implements SysOrderService {

    @Autowired
    private OrderFeignService orderFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public OrderApiVO getOrderApiVO(Long orderNo) {
        return orderFeignService.getOrderApiVO(orderNo);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<OrderApiVO> listOrderApiVOSByPage(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        return orderFeignService.listOrderApiVOSByPage(orderNo, userId, status, pageNum, pageSize);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer deliverGoods(OrderShippedForm form) {
        return orderFeignService.deliverGoods(form.getOrderId(), form2OrderLogisticsInfoAddApiDTO(form));
    }

    private OrderLogisticsInfoAddApiDTO form2OrderLogisticsInfoAddApiDTO(OrderShippedForm form) {
        OrderLogisticsInfoAddApiDTO orderLogisticsInfoAddApiDTO = new OrderLogisticsInfoAddApiDTO();
        orderLogisticsInfoAddApiDTO.setLogisticsId(form.getLogisticsId());
        orderLogisticsInfoAddApiDTO.setLogisticsCompany(form.getLogisticsCompany());
        return orderLogisticsInfoAddApiDTO;
    }

}
