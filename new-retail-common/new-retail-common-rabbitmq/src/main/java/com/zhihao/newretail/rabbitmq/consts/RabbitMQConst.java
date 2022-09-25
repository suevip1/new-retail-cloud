package com.zhihao.newretail.rabbitmq.consts;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class RabbitMQConst {

    /* 幂等性防重复消费字段(乐观锁) */
    public static final Integer CONSUME_VERSION = 1;

    /* 订单异步通知交换机 */
    public static final String ORDER_NOTIFY_EXCHANGE = "order.notify";

    /* 支付成功通知交换机 */
    public static final String PAY_NOTIFY_EXCHANGE = "pay.notify";

    /* canal */
    public static final String CANAL_EXCHANGE = "canal.direct";

    /* 订单异步通知交换机类型 */
    public static final String ORDER_NOTIFY_EXCHANGE_TYPE = "x-delayed-message";

    /* 订单延迟队列路由key */
    public static final String ORDER_CLOSE_ROUTING_KEY = "order.delayed.close";

    /* 订单删减库存路由key */
    public static final String ORDER_STOCK_SUB_ROUTING_KEY = "order.stock.sub";

    /* 订单解锁库存(回滚)路由key */
    public static final String ORDER_STOCK_UNLOCK_ROUTING_KEY = "order.stock.unlock";

    /* 订单回滚优惠券路由key */
    public static final String ORDER_COUPONS_UNSUB_ROUTING_KEY = "order.coupons.unsub";

    /* 订单关闭支付路由key */
    public static final String ORDER_CLOSE_PAY_ROUTING_KEY = "order.close.pay";

    /* 支付成功路由key */
    public static final String PAY_SUCCESS_ROUTING_KEY = "pay.success";

    /* canal */
    public static String CANAL_ROUTING_KEY = "canal.routing.key";

    /* 订单延迟队列名 */
    public static final String ORDER_CLOSE_QUEUE = "order.close.queue";

    /* 订单删减库存队列 */
    public static final String ORDER_STOCK_SUB_QUEUE = "order.stock.sub.queue";

    /* 订单解锁(归滚)库存队列 */
    public static final String ORDER_STOCK_UNLOCK_QUEUE = "order.stock.unlock.queue";

    /* 订单回滚优惠券队列 */
    public static final String ORDER_COUPONS_UNSUB_QUEUE = "order.coupons.unsub.queue";

    /* 订单关闭支付消息队列 */
    public static final String ORDER_CLOSE_PAY_QUEUE = "order.close.pay.queue";

    /* 支付成功通知队列 */
    public static final String PAY_SUCCESS_QUEUE = "pay.success.queue";

    /* canal */
    public static final String CANAL_QUEUE = "canal.queue";

}
