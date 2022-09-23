package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.enums.OrderStatusEnum;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.rabbitmq.dto.order.OrderCloseMQDTO;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
import com.zhihao.newretail.rabbitmq.util.MyRabbitMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Slf4j
@Component
public class OrderNotifyMsgListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMQLogService orderMqLogService;

    @Autowired
    private MyRabbitMQUtil rabbitMQUtil;

    @Autowired
    private ThreadPoolExecutor executor;

    @RabbitListener(queues = RabbitMQConst.ORDER_CLOSE_QUEUE)
    public void orderCloseQueue(String msgStr, Message message, Channel channel) throws IOException {
        OrderCloseMQDTO orderCloseMQDTO = GsonUtil.json2Obj(msgStr, OrderCloseMQDTO.class);
        Integer version = orderCloseMQDTO.getMqVersion();
        Order order = orderService.getOrder(orderCloseMQDTO.getOrderNo());
        /*
        * 关闭未支付的订单
        * */
        if (!ObjectUtils.isEmpty(order)
                && OrderStatusEnum.NOT_PAY.getCode().equals(order.getStatus())
                && DeleteEnum.NOT_DELETE.getCode().equals(order.getIsDelete())) {
            AtomicInteger orderVersion = new AtomicInteger(order.getMqVersion());   // 消费消息版本号
            if (orderVersion.compareAndSet(version, orderVersion.get() + RabbitMQConst.CONSUME_VERSION)) {
                order.setIsDelete(DeleteEnum.DELETE.getCode());
                order.setMqVersion(orderVersion.get());
                try {
                    CompletableFuture<Void> updateOrderFuture = CompletableFuture.runAsync(() -> {
                        orderService.updateOrder(order);
                    }, executor);
                    CompletableFuture<Void> sendStockUnLockNotifyMessageFuture = CompletableFuture.runAsync(() -> {
                        sendStockUnLockNotifyMessage(order.getId());    // 发送消息解锁库存
                    }, executor);
                    CompletableFuture<Void> sendPayCloseNotifyMessageFuture = CompletableFuture.runAsync(() -> {
                        sendPayCloseNotifyMessage(order);               // 发送消息关闭支付通道
                    }, executor);
                    CompletableFuture<Void> sendCouponsUnSubNotifyMessageFuture = CompletableFuture.runAsync(() -> {
                        if (!ObjectUtils.isEmpty(order.getCouponsId())) {
                            sendCouponsUnSubNotifyMessage(order.getCouponsId());    // 发送消息回滚优惠券
                        }
                    }, executor);
                    CompletableFuture.allOf(updateOrderFuture, sendStockUnLockNotifyMessageFuture, sendPayCloseNotifyMessageFuture, sendCouponsUnSubNotifyMessageFuture).join();
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("当前时间:{},订单号:{},关闭订单", new Date(), order.getId());
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /*
    * 发送解锁库存消息
    * */
    private void sendStockUnLockNotifyMessage(Long orderNo) {
        String content = getStockUnLockMessage(orderNo);
        String exchange = RabbitMQConst.ORDER_NOTIFY_EXCHANGE;
        String routingKey = RabbitMQConst.ORDER_STOCK_UNLOCK_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        rabbitMQUtil.sendMessage(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
    }

    /*
    * 发送优惠券回退消息
    * */
    private void sendCouponsUnSubNotifyMessage(Integer couponsId) {
        String content = getCouponsUnSubMessage(couponsId);
        String exchange = RabbitMQConst.ORDER_NOTIFY_EXCHANGE;
        String routingKey = RabbitMQConst.ORDER_COUPONS_UNSUB_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        rabbitMQUtil.sendMessage(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
    }

    /*
    * 发送关闭支付通道消息
    * */
    private void sendPayCloseNotifyMessage(Order order) {
        String content = getPayCloseMessage(order);
        String exchange = RabbitMQConst.ORDER_NOTIFY_EXCHANGE;
        String routingKey = RabbitMQConst.ORDER_CLOSE_PAY_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        rabbitMQUtil.sendMessage(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
    }

    private String getStockUnLockMessage(Long orderNo) {
        StockUnLockMQDTO stockUnLockMQDTO = new StockUnLockMQDTO();
        stockUnLockMQDTO.setOrderNo(orderNo);
        stockUnLockMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(stockUnLockMQDTO);
    }

    private String getCouponsUnSubMessage(Integer couponsId) {
        CouponsUnSubMQDTO couponsUnSubMQDTO = new CouponsUnSubMQDTO();
        couponsUnSubMQDTO.setCouponsId(couponsId);
        couponsUnSubMQDTO.setQuantity(1);
        couponsUnSubMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(couponsUnSubMQDTO);
    }

    private String getPayCloseMessage(Order order) {
        PayNotifyMQDTO payNotifyMQDTO = new PayNotifyMQDTO();
        payNotifyMQDTO.setOrderNo(order.getId());
        payNotifyMQDTO.setUserId(order.getUserId());
        payNotifyMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(payNotifyMQDTO);
    }

}
