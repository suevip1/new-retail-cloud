package com.zhihao.newretail.pay.dao;

import com.zhihao.newretail.pay.pojo.PayInfo;

public interface PayInfoMapper {

    int deleteByOrderId(Long orderId);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

}
