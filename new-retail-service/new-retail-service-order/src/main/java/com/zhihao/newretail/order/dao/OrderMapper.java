package com.zhihao.newretail.order.dao;

import com.zhihao.newretail.order.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int countByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    List<Order> selectList(@Param("userId") Integer userId,
                           @Param("status") Integer status,
                           @Param("pageNum") Integer pageNum,
                           @Param("pageSize") Integer pageSize);

}
