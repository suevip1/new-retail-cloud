package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderAddress;

public interface OrderAddressMapper {

    int deleteByPrimaryKey(Long orderId);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);

}
