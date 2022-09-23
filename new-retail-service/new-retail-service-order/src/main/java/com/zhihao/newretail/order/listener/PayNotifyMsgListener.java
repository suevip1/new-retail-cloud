package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockSubLockMQDTO;
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
public class PayNotifyMsgListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMQLogService orderMqLogService;

    @Autowired
    private MyRabbitMQUtil rabbitMQUtil;

    @Autowired
    private ThreadPoolExecutor executor;

    @RabbitListener(queues = RabbitMQConst.PAY_SUCCESS_QUEUE)
    public void payNotifyQueue(String msgStr, Message message, Channel channel) throws IOException {
        PayNotifyMQDTO payNotifyMQDTO = GsonUtil.json2Obj(msgStr, PayNotifyMQDTO.class);
        Integer version = payNotifyMQDTO.getMqVersion();

        Order order = orderService.getOrder(payNotifyMQDTO.getOrderNo());
        if (!ObjectUtils.isEmpty(order)
                && DeleteEnum.NOT_DELETE.getCode().equals(order.getIsDelete())
                && payNotifyMQDTO.getUserId().equals(order.getUserId())
                && payNotifyMQDTO.getPayAmount().equals(order.getActualAmount())) {
            AtomicInteger orderVersion = new AtomicInteger(order.getMqVersion());
            if (orderVersion.compareAndSet(version, orderVersion.get() + RabbitMQConst.CONSUME_VERSION)) {
                /* 更新订单状态 */
                order.setOrderCode(payNotifyMQDTO.getPlatformNumber());
                order.setPaymentType(payNotifyMQDTO.getPayPlatform());
                order.setStatus(payNotifyMQDTO.getStatus());
                order.setMqVersion(orderVersion.get());
                try {
                    CompletableFuture<Void> updateOrderFuture = CompletableFuture.runAsync(() -> {
                        orderService.updateOrder(order);
                    }, executor);
                    CompletableFuture<Void> sendStockSubLockNotifyMessageFuture = CompletableFuture.runAsync(() -> {
                        String content = stockSubLockMessageContent(order.getId());
                        sendStockSubLockNotifyMessage(content);     // 通知删减库存
                    }, executor);
                    CompletableFuture.allOf(updateOrderFuture, sendStockSubLockNotifyMessageFuture).join();
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("当前时间:{},订单号:{},付款成功", new Date(), order.getId());
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    private void sendStockSubLockNotifyMessage(String content) {
        String exchange = RabbitMQConst.ORDER_NOTIFY_EXCHANGE;
        String routingKey = RabbitMQConst.ORDER_STOCK_SUB_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        rabbitMQUtil.sendMessage(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
    }

    private String stockSubLockMessageContent(Long orderNo) {
        StockSubLockMQDTO stockSubLockMQDTO = new StockSubLockMQDTO();
        stockSubLockMQDTO.setOrderNo(orderNo);
        stockSubLockMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(stockSubLockMQDTO);
    }

}
