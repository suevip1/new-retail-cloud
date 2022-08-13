package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderMQLog;

public interface OrderMQLogMapper {

    int deleteByPrimaryKey(Long messageId);

    int insert(OrderMQLog record);

    int insertSelective(OrderMQLog record);

    OrderMQLog selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(OrderMQLog record);

    int updateByPrimaryKey(OrderMQLog record);

}
