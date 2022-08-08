package com.zhihao.newretail.message.service.impl;

import com.zhihao.newretail.message.dao.MQLogMapper;
import com.zhihao.newretail.message.pojo.MQLog;
import com.zhihao.newretail.message.service.MQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
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
    public void insetMessage(Long messageId, String content, String exchange, String routingKey) {
        MQLog mqLog = new MQLog();
        mqLog.setMessageId(messageId);
        mqLog.setContent(content);
        mqLog.setExchange(exchange);
        mqLog.setRoutingKey(routingKey);
        mqLog.setStatus(MessageStatusEnum.NEW_MESSAGE.getCode());
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
