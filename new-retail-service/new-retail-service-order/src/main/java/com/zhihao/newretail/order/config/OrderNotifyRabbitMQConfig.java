package com.zhihao.newretail.order.config;

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
public class OrderNotifyRabbitMQConfig {

    @Bean(name = "notifyExchange")
    public DirectExchange notifyExchange() {
        return new DirectExchange(RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME, true, false);
    }

    @Bean(name = "stockSubQueue")
    public Queue stockSubQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_STOCK_SUB_QUEUE_NAME);
    }

    @Bean(name = "stockUnlockQueue")
    public Queue stockUnlockQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_QUEUE_NAME);
    }

    @Bean(name = "couponsUnsubQueue")
    public Queue couponsUnsubQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_QUEUE_NAME);
    }

    @Bean(name = "stockSubQueueBindNotifyExchange")
    public Binding stockSubQueueBindNotifyExchange(@Qualifier("stockSubQueue") Queue stockSubQueue,
                                                   @Qualifier("notifyExchange") DirectExchange notifyExchange) {
        return BindingBuilder.bind(stockSubQueue).to(notifyExchange).with(RabbitMQConst.ORDER_NOTIFY_STOCK_SUB_ROUTING_KEY);
    }

    @Bean(name = "stockUnlockQueueBindNotifyExchange")
    public Binding stockUnlockQueueBindNotifyExchange(@Qualifier("stockUnlockQueue") Queue stockUnlockQueue,
                                                      @Qualifier("notifyExchange") DirectExchange notifyExchange) {
        return BindingBuilder.bind(stockUnlockQueue).to(notifyExchange).with(RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_ROUTING_KEY);
    }

    @Bean(name = "couponsUnsubQueueBindNotifyExchange")
    public Binding couponsUnsubQueueBindNotifyExchange(@Qualifier("couponsUnsubQueue") Queue couponsUnsubQueue,
                                                       @Qualifier("notifyExchange") DirectExchange notifyExchange) {
        return BindingBuilder.bind(couponsUnsubQueue).to(notifyExchange).with(RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_ROUTING_KEY);
    }

}
