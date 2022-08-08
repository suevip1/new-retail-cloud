package com.zhihao.newretail.message.config;

import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Configuration
public class RabbitMQConfig {

    /* 延迟交换机 */
    @Bean(name = "delayedExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMQConst.ORDER_DELAYED_EXCHANGE_NAME,
                RabbitMQConst.ORDER_DELAYED_EXCHANGE_TYPE, true, false, arguments);
    }

    /* 订单异步通知交换机 */
    @Bean(name = "orderNotifyExchange")
    public DirectExchange orderNotifyExchange() {
        return new DirectExchange(RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME);
    }

    /* 支付异步通知交换机 */
    @Bean(name = "payNotifyExchange")
    public DirectExchange payNotifyExchange() {
        return new DirectExchange(RabbitMQConst.PAY_NOTIFY_EXCHANGE_NAME, true, false);
    }

    /* 订单关闭消息队列 */
    @Bean(name = "orderCloseQueue")
    public Queue orderCloseQueue() {
        return new Queue(RabbitMQConst.ORDER_DELAYED_QUEUE_NAME);
    }

    /* 删减库存消息队列 */
    @Bean(name = "stockSubQueue")
    public Queue stockSubQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_STOCK_SUB_QUEUE_NAME);
    }

    /* 解锁库存消息队列 */
    @Bean(name = "stockUnLockQueue")
    public Queue stockUnLockQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_QUEUE_NAME);
    }

    /* 优惠券回退消息队列 */
    @Bean(name = "couponsUnSubQueue")
    public Queue couponsUnSubQueue() {
        return new Queue(RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_QUEUE_NAME);
    }

    /* 支付异步通知消息队列 */
    @Bean(name = "payNotifyQueue")
    public Queue payNotifyQueue() {
        return new Queue(RabbitMQConst.PAY_NOTIFY_QUEUE_NAME);
    }

    /* 订单关闭消息队列绑定延迟交换机 */
    @Bean(name = "orderCloseQueueBindDelayedExchange")
    public Binding orderCloseQueueBindDelayedExchange(@Qualifier("orderCloseQueue") Queue orderCloseQueue,
                                                      @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(orderCloseQueue).to(delayedExchange).with(RabbitMQConst.ORDER_DELAYED_ROUTING_KEY).noargs();
    }

    /* 删减库存消息队列绑定订单异步通知交换机 */
    @Bean(name = "stockSubQueueBindOrderNotifyExchange")
    public Binding stockSubQueueBindOrderNotifyExchange(@Qualifier("stockSubQueue") Queue stockSubQueue,
                                                        @Qualifier("orderNotifyExchange") DirectExchange orderNotifyExchange) {
        return BindingBuilder.bind(stockSubQueue).to(orderNotifyExchange).with(RabbitMQConst.ORDER_NOTIFY_STOCK_SUB_ROUTING_KEY);
    }

    /* 解锁库存消息队列绑定订单异步通知交换机 */
    @Bean(name = "stockUnLockQueueBindOrderNotifyExchange")
    public Binding stockUnLockQueueBindOrderNotifyExchange(@Qualifier("stockUnLockQueue") Queue stockUnLockQueue,
                                                           @Qualifier("orderNotifyExchange") DirectExchange orderNotifyExchange) {
        return BindingBuilder.bind(stockUnLockQueue).to(orderNotifyExchange).with(RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_ROUTING_KEY);
    }

    /* 优惠券回退消息队列绑定订单异步通知交换机 */
    @Bean(name = "couponsUnSubQueueBindOrderNotifyExchange")
    public Binding couponsUnsubQueueBindOrderNotifyExchange(@Qualifier("couponsUnSubQueue") Queue couponsUnSubQueue,
                                                            @Qualifier("orderNotifyExchange") DirectExchange orderNotifyExchange) {
        return BindingBuilder.bind(couponsUnSubQueue).to(orderNotifyExchange).with(RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_ROUTING_KEY);
    }

    /* 支付异步通知消息队列绑定支付异步通知交换机 */
    @Bean(name = "payNotifyQueueBindPayNotifyExchange")
    public Binding payNotifyQueueBindPayNotifyExchange(@Qualifier("payNotifyQueue") Queue payNotifyQueue,
                                                       @Qualifier("payNotifyExchange") DirectExchange payNotifyExchange) {
        return BindingBuilder.bind(payNotifyQueue).to(payNotifyExchange).with(RabbitMQConst.PAY_NOTIFY_ROUTING_KEY);
    }

}
