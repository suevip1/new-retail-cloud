package com.zhihao.newretail.pay.dao;

import com.zhihao.newretail.pay.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInfoMapper {

    int deleteByOrderId(Long orderId);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

    int countByRecord(@Param("orderId") Long orderId,
                      @Param("userId") Integer userId,
                      @Param("payPlatform") Integer payPlatform,
                      @Param("status") Integer status,
                      @Param("platformNumber") Integer platformNumber);

    List<PayInfo> selectListByRecord(@Param("orderId") Long orderId,
                                     @Param("userId") Integer userId,
                                     @Param("payPlatform") Integer payPlatform,
                                     @Param("status") Integer status,
                                     @Param("platformNumber") Integer platformNumber,
                                     @Param("pageNum") Integer pageNum,
                                     @Param("pageSize") Integer pageSize);

}
