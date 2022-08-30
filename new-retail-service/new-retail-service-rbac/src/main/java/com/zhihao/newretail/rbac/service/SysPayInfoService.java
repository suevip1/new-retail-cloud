package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;

public interface SysPayInfoService {

    /*
    * 支付信息列表
    * */
    PageUtil<PayInfoApiVO> listPayInfoApiVOS(Long orderId, Integer userId, Integer payPlatform,
                                             Integer status, Integer platformNumber, Integer pageNum, Integer pageSize);

}
