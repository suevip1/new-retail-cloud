package com.zhihao.newretail.pay.dao;

import com.zhihao.newretail.pay.pojo.MQLog;

public interface MQLogMapper {

    int deleteByPrimaryKey(Long messageId);

    int insert(MQLog record);

    int insertSelective(MQLog record);

    MQLog selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(MQLog record);

    int updateByPrimaryKey(MQLog record);

}
