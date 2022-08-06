package com.zhihao.newretail.order.config;

import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * RabbitMQ 配置
 * */
@Configuration
public class OrderCloseRabbitMQConfig {

    @Bean(name = "delayedExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMQConst.ORDER_DELAYED_EXCHANGE_NAME,
                RabbitMQConst.ORDER_DELAYED_EXCHANGE_TYPE, true, false, arguments);
    }

    @Bean(name = "orderCloseQueue")
    public Queue orderCloseQueue() {
        return new Queue(RabbitMQConst.ORDER_DELAYED_QUEUE_NAME);
    }

    @Bean(name = "orderCloseQueueBindDelayedExchange")
    public Binding orderCloseQueueBindDelayedExchange(@Qualifier("orderCloseQueue") Queue orderCloseQueue,
                                                      @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(orderCloseQueue).to(delayedExchange).with(RabbitMQConst.ORDER_DELAYED_ROUTING_KEY).noargs();
    }

}
