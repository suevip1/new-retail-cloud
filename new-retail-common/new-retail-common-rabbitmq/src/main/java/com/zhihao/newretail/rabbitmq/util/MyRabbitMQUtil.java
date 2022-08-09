package com.zhihao.newretail.rabbitmq.util;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Component
public class MyRabbitMQUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, Object content, CorrelationData correlationData) {
        this.sendMessage(exchange, routingKey, content, null, correlationData);
    }

    public void sendMessage(String exchange, String routingKey, Object content, Integer delay, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                content,
                message -> {
                    message.getMessageProperties().setDelay(delay);
                    return message;
                },
                correlationData
        );
    }

}
