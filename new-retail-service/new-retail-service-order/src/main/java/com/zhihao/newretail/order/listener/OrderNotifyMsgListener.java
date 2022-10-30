package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.enums.OrderStatusEnum;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.rabbitmq.dto.order.OrderCloseMQDTO;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zhihao.newretail.rabbitmq.consts.RabbitMQConst.*;

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
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = ORDER_CLOSE_QUEUE)
    public void orderCloseQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("订单服务, 接收关闭订单消息:{}.", msgStr);
        OrderCloseMQDTO orderCloseMQDTO = GsonUtil.json2Obj(msgStr, OrderCloseMQDTO.class);
        Integer version = orderCloseMQDTO.getMqVersion();
        Order order = orderService.getOrder(orderCloseMQDTO.getOrderNo());
        /*
        * 关闭未支付的订单
        * */
        if (!ObjectUtils.isEmpty(order) && OrderStatusEnum.NOT_PAY.getCode().equals(order.getStatus()) &&
                DeleteEnum.NOT_DELETE.getCode().equals(order.getIsDelete())) {
            AtomicInteger orderVersion = new AtomicInteger(order.getMqVersion());   // 消费消息版本号
            if (orderVersion.compareAndSet(version, orderVersion.get() + CONSUME_VERSION)) {
                order.setIsDelete(DeleteEnum.DELETE.getCode());
                order.setMqVersion(orderVersion.get());
                try {
                    orderService.updateOrder(order);
                    sendStockUnLockNotifyMessage(order.getId());
                    sendPayCloseNotifyMessage(order);
                    if (!ObjectUtils.isEmpty(order.getCouponsId())) {
                        sendCouponsUnSubNotifyMessage(order.getCouponsId());    // 发送消息回滚优惠券
                    }
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("当前时间:{}, 订单号:{}, 关闭订单.", new Date(), order.getId());
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        } else {
            log.info("订单号:{}, 无需处理.", orderCloseMQDTO.getOrderNo());
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /*
    * 发送解锁库存消息
    * */
    private void sendStockUnLockNotifyMessage(Long orderNo) {
        String content = getStockUnLockMessage(orderNo);
        String exchange = ORDER_NOTIFY_EXCHANGE;
        String routingKey = ORDER_STOCK_UNLOCK_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务, 发送解锁库存消息:{}.", content);
        }
    }

    /*
    * 发送优惠券回退消息
    * */
    private void sendCouponsUnSubNotifyMessage(Integer couponsId) {
        String content = getCouponsUnSubMessage(couponsId);
        String exchange = ORDER_NOTIFY_EXCHANGE;
        String routingKey = ORDER_COUPONS_UNSUB_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务, 发送优惠券回退消息:{}.", content);
        }
    }

    /*
    * 发送关闭支付通道消息
    * */
    private void sendPayCloseNotifyMessage(Order order) {
        String content = getPayCloseMessage(order);
        String exchange = ORDER_NOTIFY_EXCHANGE;
        String routingKey = ORDER_CLOSE_PAY_ROUTING_KEY;
        Long messageId = orderMqLogService.getMessageId();
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, exchange, routingKey);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(exchange, routingKey, content, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务, 发送关闭支付消息:{}.", content);
        }
    }

    private String getStockUnLockMessage(Long orderNo) {
        StockUnLockMQDTO stockUnLockMQDTO = new StockUnLockMQDTO();
        stockUnLockMQDTO.setOrderNo(orderNo);
        stockUnLockMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(stockUnLockMQDTO);
    }

    private String getCouponsUnSubMessage(Integer couponsId) {
        CouponsUnSubMQDTO couponsUnSubMQDTO = new CouponsUnSubMQDTO();
        couponsUnSubMQDTO.setCouponsId(couponsId);
        couponsUnSubMQDTO.setQuantity(1);
        couponsUnSubMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(couponsUnSubMQDTO);
    }

    private String getPayCloseMessage(Order order) {
        PayNotifyMQDTO payNotifyMQDTO = new PayNotifyMQDTO();
        payNotifyMQDTO.setOrderNo(order.getId());
        payNotifyMQDTO.setUserId(order.getUserId());
        payNotifyMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(payNotifyMQDTO);
    }

}
