package com.zhihao.newretail.message.service.impl;

import com.zhihao.newretail.message.dao.MQLogMapper;
import com.zhihao.newretail.message.pojo.MQLog;
import com.zhihao.newretail.message.service.MQLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class MQLogServiceImpl implements MQLogService {

    @Autowired
    private MQLogMapper mqLogMapper;

    @Override
    public void insetMessage(MQLog mqLog) {
        mqLogMapper.insertSelective(mqLog);
    }

    @Override
    public void updateMessage(MQLog mqLog) {
        mqLogMapper.updateByPrimaryKeySelective(mqLog);
    }

    @Override
    public void deleteMessage(Long messageId) {
        mqLogMapper.deleteByPrimaryKey(messageId);
    }

    @Override
    public MQLog getMQLog(Long messageId) {
        return mqLogMapper.selectByPrimaryKey(messageId);
    }

}
