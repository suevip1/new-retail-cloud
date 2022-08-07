package com.zhihao.newretail.pay.config;

import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Configuration
public class PayNotifyRabbitMQConfig {

    @Bean(name = "notifyExchange")
    public DirectExchange notifyExchange() {
        return new DirectExchange(RabbitMQConst.PAY_NOTIFY_EXCHANGE_NAME, true, false);
    }

    @Bean(name = "payNotifyQueue")
    public Queue payNotifyQueue() {
        return new Queue(RabbitMQConst.PAY_NOTIFY_QUEUE_NAME);
    }

    @Bean(name = "payNotifyQueueBindNotifyExchange")
    public Binding payNotifyQueueBindNotifyExchange(@Qualifier("payNotifyQueue") Queue payNotifyQueue,
                                                    @Qualifier("notifyExchange") DirectExchange notifyExchange) {
        return BindingBuilder.bind(payNotifyQueue).to(notifyExchange).with(RabbitMQConst.PAY_NOTIFY_ROUTING_KEY);
    }

}
