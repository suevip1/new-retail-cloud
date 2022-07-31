package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {

    int deleteByOrderId(Long orderId);

    int deleteBySkuId(Integer skuId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByOrderId(Long orderId);

    OrderItem selectBySkuId(Integer skuId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    /*
    * 批量插入
    * */
    int insertBatch(@Param("orderItemList") List<OrderItem> orderItemList);

}
