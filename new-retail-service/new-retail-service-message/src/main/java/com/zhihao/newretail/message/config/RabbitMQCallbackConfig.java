package com.zhihao.newretail.message.config;

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
public class RabbitMQCallbackConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        /* 消息发布确认 */
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("correlationData:{}", correlationData);
//            assert correlationData != null;
//            Long messageId = Long.valueOf(correlationData.getId());
//            MQLog mqLog = orderMQLogService.getMQLog(messageId);
//            if (ack) {
//                mqLog.setStatus(MessageStatusEnum.SEND_SUCCESS.getCode());
//                orderMQLogService.updateMessage(mqLog);
//            } else {
//                /* 发送失败，重新发送 */
//                mqLog.setStatus(MessageStatusEnum.SEND_ERROR.getCode());
//                orderMQLogService.updateMessage(mqLog);
//                rabbitTemplate.convertAndSend(mqLog.getExchange(), mqLog.getRoutingKey(), mqLog.getContent());
//            }
        });

        rabbitTemplate.setReturnsCallback((ReturnedMessage returnedMessage) -> {
            String message = new String(returnedMessage.getMessage().getBody());
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            log.info("time{},message:{},replyText:{},exchange:{},routingKey:{}", new Date(), message, replyText, exchange, routingKey);
        });
    }

}
