package com.zhihao.newretail.order.config;

import com.zhihao.newretail.order.pojo.OrderMQLog;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Slf4j
@Configuration
public class OrderMessageCallbackConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMQLogService orderMqLogService;

    @PostConstruct
    public void initRabbitTemplate() {
        /* 消息发布确认 */
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            assert correlationData != null;
            Long messageId = Long.valueOf(correlationData.getId());
            OrderMQLog orderMqLog = orderMqLogService.getMQLog(messageId);
            if (ack) {
                orderMqLog.setStatus(MessageStatusEnum.SEND_SUCCESS.getCode());
                orderMqLogService.updateMessage(orderMqLog);
                log.info("messageId:{},messageContent:{},消息发送成功", messageId, orderMqLog.getContent());
            } else {
                /* 发送失败，重新发送 */
                orderMqLog.setStatus(MessageStatusEnum.SEND_ERROR.getCode());
                orderMqLogService.updateMessage(orderMqLog);
                rabbitTemplate.convertAndSend(orderMqLog.getExchange(), orderMqLog.getRoutingKey(), orderMqLog.getContent());
            }
        });

        /* 回退消息 */
        rabbitTemplate.setReturnsCallback((ReturnedMessage returnedMessage) -> {
            String message = new String(returnedMessage.getMessage().getBody());
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            log.info("time:{},message:{},replyText:{},exchange:{},routingKey:{}", new Date(), message, replyText, exchange, routingKey);
        });
    }
}
