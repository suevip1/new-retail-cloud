package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.core.util.PageUtil;

public interface SysOrderService {

    /*
    * 订单列表
    * */
    PageUtil<OrderApiVO> listOrderApiVOSByPage(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize);

}
