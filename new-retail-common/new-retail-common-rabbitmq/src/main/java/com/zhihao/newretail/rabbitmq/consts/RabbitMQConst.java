package com.zhihao.newretail.rabbitmq.consts;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class RabbitMQConst {

    /* 幂等性防重复消费字段(乐观锁) */
    public static final Integer CONSUME_VERSION = 1;

    /* 订单延迟交换机 */
    public static final String ORDER_DELAYED_EXCHANGE_NAME = "order.delayed";

    /* 订单延迟交换机类型 */
    public static final String ORDER_DELAYED_EXCHANGE_TYPE = "x-delayed-message";

    /* 订单延迟队列路由key */
    public static final String ORDER_DELAYED_ROUTING_KEY = "order.delayed.timing";

    /* 订单延迟队列名 */
    public static final String ORDER_DELAYED_QUEUE_NAME = "order.close.queue";

    /* 订单通知交换机 */
    public static final String ORDER_NOTIFY_EXCHANGE_NAME = "order.notify";

    /* 支付成功通知交换机 */
    public static final String PAY_NOTIFY_EXCHANGE_NAME = "pay.notify";

    /* 订单删减库存路由key */
    public static final String ORDER_NOTIFY_STOCK_SUB_ROUTING_KEY = "order.notify.stock.sub";

    /* 订单解锁库存(回滚)路由key */
    public static final String ORDER_NOTIFY_STOCK_UNLOCK_ROUTING_KEY = "order.notify.stock.unlock";

    /* 订单回滚优惠券路由key */
    public static final String ORDER_NOTIFY_COUPONS_UNSUB_ROUTING_KEY = "order.notify.coupons.unsub";

    /* 支付成功路由key */
    public static final String PAY_NOTIFY_ROUTING_KEY = "pay.notify.success";

    /* 订单删减库存队列 */
    public static final String ORDER_NOTIFY_STOCK_SUB_QUEUE_NAME = "order.notify.stock.sub.queue";

    /* 订单解锁(归滚)库存队列 */
    public static final String ORDER_NOTIFY_STOCK_UNLOCK_QUEUE_NAME = "order.notify.stock.unlock.queue";

    /* 订单回滚优惠券队列 */
    public static final String ORDER_NOTIFY_COUPONS_UNSUB_QUEUE_NAME = "order.notify.coupons.unsub.queue";

    /* 支付成功通知队列 */
    public static final String PAY_NOTIFY_QUEUE_NAME = "pay.notify.queue";

}
