package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.OrderLogisticsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderLogisticsInfoMapper {

    int deleteByOrderId(Long orderId);

    int insert(OrderLogisticsInfo record);

    int insertSelective(OrderLogisticsInfo record);

    OrderLogisticsInfo selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(OrderLogisticsInfo record);

    int updateByOrderId(OrderLogisticsInfo record);

    /*
    * 订单号批量查询
    * */
    List<OrderLogisticsInfo> selectListByOrderIdSet(@Param("orderIdSet") Set<Long> orderIdSet);

}
