package com.zhihao.newretail.pay.service.impl;

import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.pay.dao.PayInfoMQLogMapper;
import com.zhihao.newretail.pay.pojo.PayInfoMQLog;
import com.zhihao.newretail.pay.service.PayInfoMQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class PayInfoMQLogServiceImpl implements PayInfoMQLogService {

    @Autowired
    private PayInfoMQLogMapper payInfoMqLogMapper;

    @Override
    public int insetMessage(Long messageId, String content, String exchange, String routingKey) {
        PayInfoMQLog payInfoMqLog = new PayInfoMQLog();
        payInfoMqLog.setMessageId(messageId);
        payInfoMqLog.setContent(content);
        payInfoMqLog.setExchange(exchange);
        payInfoMqLog.setRoutingKey(routingKey);
        payInfoMqLog.setStatus(MessageStatusEnum.NEW_MESSAGE.getCode());
        return payInfoMqLogMapper.insertSelective(payInfoMqLog);
    }

    @Override
    public int updateMessage(PayInfoMQLog payInfoMqLog) {
        return payInfoMqLogMapper.updateByPrimaryKeySelective(payInfoMqLog);
    }

    @Override
    public void deleteMessage(Long messageId) {
        payInfoMqLogMapper.deleteByPrimaryKey(messageId);
    }

    @Override
    public PayInfoMQLog getMQLog(Long messageId) {
        return payInfoMqLogMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public Long getMessageId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 3);
        return snowflakeIdWorker.nextId();
    }

}
