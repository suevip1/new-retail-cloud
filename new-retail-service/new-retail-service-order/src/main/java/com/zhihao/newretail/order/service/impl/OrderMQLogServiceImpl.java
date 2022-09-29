package com.zhihao.newretail.order.service.impl;

import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.order.dao.OrderMQLogMapper;
import com.zhihao.newretail.order.pojo.OrderMQLog;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class OrderMQLogServiceImpl implements OrderMQLogService {

    @Autowired
    private OrderMQLogMapper orderMqLogMapper;

    @Override
    public int insetMessage(Long messageId, String content, String exchange, String routingKey) {
        OrderMQLog orderMqLog = new OrderMQLog();
        orderMqLog.setMessageId(messageId);
        orderMqLog.setContent(content);
        orderMqLog.setExchange(exchange);
        orderMqLog.setRoutingKey(routingKey);
        orderMqLog.setStatus(MessageStatusEnum.NEW_MESSAGE.getCode());
        return orderMqLogMapper.insertSelective(orderMqLog);
    }

    @Override
    public int updateMessage(OrderMQLog orderMqLog) {
        return orderMqLogMapper.updateByPrimaryKeySelective(orderMqLog);
    }

    @Override
    public void deleteMessage(Long messageId) {
        orderMqLogMapper.deleteByPrimaryKey(messageId);
    }

    @Override
    public OrderMQLog getMQLog(Long messageId) {
        return orderMqLogMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public Long getMessageId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 2);
        return snowflakeIdWorker.nextId();
    }

}
