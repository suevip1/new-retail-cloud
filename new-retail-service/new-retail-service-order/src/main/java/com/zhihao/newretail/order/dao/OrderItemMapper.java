package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderItem;
import com.zhihao.newretail.order.pojo.OrderItemKey;

public interface OrderItemMapper {

    int deleteByPrimaryKey(OrderItemKey key);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(OrderItemKey key);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

}
