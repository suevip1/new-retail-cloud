package com.zhihao.newretail.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.zhihao.newretail.rabbitmq.consts.RabbitMQConst.*;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * RabbitMQ交换机、消息队列绑定关系配置
 * */
@Configuration
public class RabbitMQConfig {

    /* 订单异步通知交换机 */
    @Bean(name = "orderNotifyExchange")
    public CustomExchange orderNotifyExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(
                ORDER_NOTIFY_EXCHANGE,
                ORDER_NOTIFY_EXCHANGE_TYPE,
                true,
                false,
                arguments
        );
    }

    /* 支付异步通知交换机 */
    @Bean(name = "payNotifyExchange")
    public DirectExchange payNotifyExchange() {
        return new DirectExchange(PAY_NOTIFY_EXCHANGE);
    }

    /* canal 监听交换机 */
    @Bean(name = "canalExchange")
    public DirectExchange canalExchange() {
        return new DirectExchange(CANAL_EXCHANGE);
    }

    /* 订单关闭消息队列 */
    @Bean(name = "orderCloseQueue")
    public Queue orderCloseQueue() {
        return new Queue(ORDER_CLOSE_QUEUE);
    }

    /* 删减库存消息队列 */
    @Bean(name = "stockSubQueue")
    public Queue stockSubQueue() {
        return new Queue(ORDER_STOCK_SUB_QUEUE);
    }

    /* 解锁库存消息队列 */
    @Bean(name = "stockUnLockQueue")
    public Queue stockUnLockQueue() {
        return new Queue(ORDER_STOCK_UNLOCK_QUEUE);
    }

    /* 优惠券回退消息队列 */
    @Bean(name = "couponsUnSubQueue")
    public Queue couponsUnSubQueue() {
        return new Queue(ORDER_COUPONS_UNSUB_QUEUE);
    }

    /* 订单关闭支付消息队列 */
    @Bean(name = "orderClosePayQueue")
    public Queue orderClosePayQueue() {
        return new Queue(ORDER_CLOSE_PAY_QUEUE);
    }

    /* 支付异步通知消息队列 */
    @Bean(name = "paySuccessQueue")
    public Queue paySuccessQueue() {
        return new Queue(PAY_SUCCESS_QUEUE);
    }

    /* canal 监听指定队列 */
    @Bean(name = "canalProductQueue")
    public Queue canalProductQueue() {
        return new Queue(CANAL_PRODUCT_QUEUE);
    }

    @Bean(name = "canalUserQueue")
    public Queue canalUserQueue() {
        return new Queue(CANAL_USER_QUEUE);
    }

    /* 订单关闭消息队列绑定订单异步通知交换机 */
    @Bean(name = "orderCloseQueueBindOrderNotifyExchange")
    public Binding orderCloseQueueBindOrderNotifyExchange(@Qualifier("orderCloseQueue") Queue orderCloseQueue,
                                                          @Qualifier("orderNotifyExchange") CustomExchange orderNotifyExchange) {
        return BindingBuilder.bind(orderCloseQueue).to(orderNotifyExchange).with(ORDER_CLOSE_ROUTING_KEY).noargs();
    }

    /* 删减库存消息队列绑定订单异步通知交换机 */
    @Bean(name = "stockSubQueueBindOrderNotifyExchange")
    public Binding stockSubQueueBindOrderNotifyExchange(@Qualifier("stockSubQueue") Queue stockSubQueue,
                                                        @Qualifier("orderNotifyExchange") CustomExchange orderNotifyExchange) {
        return BindingBuilder.bind(stockSubQueue).to(orderNotifyExchange).with(ORDER_STOCK_SUB_ROUTING_KEY).noargs();
    }

    /* 解锁库存消息队列绑定订单异步通知交换机 */
    @Bean(name = "stockUnLockQueueBindOrderNotifyExchange")
    public Binding stockUnLockQueueBindOrderNotifyExchange(@Qualifier("stockUnLockQueue") Queue stockUnLockQueue,
                                                           @Qualifier("orderNotifyExchange") CustomExchange orderNotifyExchange) {
        return BindingBuilder.bind(stockUnLockQueue).to(orderNotifyExchange).with(ORDER_STOCK_UNLOCK_ROUTING_KEY).noargs();
    }

    /* 优惠券回退消息队列绑定订单异步通知交换机 */
    @Bean(name = "couponsUnSubQueueBindOrderNotifyExchange")
    public Binding couponsUnsubQueueBindOrderNotifyExchange(@Qualifier("couponsUnSubQueue") Queue couponsUnSubQueue,
                                                            @Qualifier("orderNotifyExchange") CustomExchange orderNotifyExchange) {
        return BindingBuilder.bind(couponsUnSubQueue).to(orderNotifyExchange).with(ORDER_COUPONS_UNSUB_ROUTING_KEY).noargs();
    }

    /* 订单关闭支付消息队列绑定订单异步通知交换机 */
    @Bean(name = "orderClosePayQueueBindOrderNotifyExchange")
    public Binding orderClosePayQueueBindOrderNotifyExchange(@Qualifier("orderClosePayQueue") Queue orderClosePayQueue,
                                                             @Qualifier("orderNotifyExchange") CustomExchange orderNotifyExchange) {
        return BindingBuilder.bind(orderClosePayQueue).to(orderNotifyExchange).with(ORDER_CLOSE_PAY_ROUTING_KEY).noargs();
    }

    /* 支付异步通知消息队列绑定支付异步通知交换机 */
    @Bean(name = "paySuccessQueueBindPayNotifyExchange")
    public Binding paySuccessQueueBindPayNotifyExchange(@Qualifier("paySuccessQueue") Queue paySuccessQueue,
                                                        @Qualifier("payNotifyExchange") DirectExchange payNotifyExchange) {
        return BindingBuilder.bind(paySuccessQueue).to(payNotifyExchange).with(PAY_SUCCESS_ROUTING_KEY);
    }

    /* canal 队列绑定交换机 */
    @Bean(name = "canalProductQueueBindCanalExchange")
    public Binding canalProductQueueBindCanalExchange(@Qualifier("canalProductQueue") Queue canalProductQueue,
                                                      @Qualifier("canalExchange") DirectExchange canalExchange) {
        return BindingBuilder.bind(canalProductQueue).to(canalExchange).with(CANAL_PRODUCT_ROUTING_KEY);
    }

    @Bean(name = "canalUserQueueBindCanalExchange")
    public Binding canalUserQueueBindCanalExchange(@Qualifier("canalUserQueue") Queue canalUserQueue,
                                                   @Qualifier("canalExchange") DirectExchange canalExchange) {
        return BindingBuilder.bind(canalUserQueue).to(canalExchange).with(CANAL_USER_ROUTING_KEY);
    }

}
