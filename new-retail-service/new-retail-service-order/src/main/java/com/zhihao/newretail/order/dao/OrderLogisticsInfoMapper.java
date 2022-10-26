package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderLogisticsInfo;

public interface OrderLogisticsInfoMapper {

    int deleteByOrderId(Long orderId);

    int insert(OrderLogisticsInfo record);

    int insertSelective(OrderLogisticsInfo record);

    OrderLogisticsInfo selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(OrderLogisticsInfo record);

    int updateByOrderId(OrderLogisticsInfo record);

}
