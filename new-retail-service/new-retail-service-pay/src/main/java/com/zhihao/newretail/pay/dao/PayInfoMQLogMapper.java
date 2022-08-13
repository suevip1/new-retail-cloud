package com.zhihao.newretail.pay.dao;

import com.zhihao.newretail.pay.pojo.PayInfoMQLog;

public interface PayInfoMQLogMapper {

    int deleteByPrimaryKey(Long messageId);

    int insert(PayInfoMQLog record);

    int insertSelective(PayInfoMQLog record);

    PayInfoMQLog selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(PayInfoMQLog record);

    int updateByPrimaryKey(PayInfoMQLog record);

}
