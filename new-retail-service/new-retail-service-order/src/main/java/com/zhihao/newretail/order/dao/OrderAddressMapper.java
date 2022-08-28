package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderAddressMapper {

    int deleteByPrimaryKey(Long orderId);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);

}
