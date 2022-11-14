package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockSubLockMQDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class PayNotifyMsgListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMQLogService orderMqLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = PAY_SUCCESS_QUEUE)
    public void payNotifyQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("订单服务, 接收支付成功通知消息:{}.", msgStr);
        PayNotifyMQDTO payNotifyMQDTO = GsonUtil.json2Obj(msgStr, PayNotifyMQDTO.class);
        Order order = orderService.getOrder(payNotifyMQDTO.getOrderNo());

        if (!ObjectUtils.isEmpty(order) &&
                DeleteEnum.NOT_DELETE.getCode().equals(order.getIsDelete()) &&
                payNotifyMQDTO.getUserId().equals(order.getUserId()) &&
                payNotifyMQDTO.getPayAmount().equals(order.getActualAmount())) {
            AtomicInteger orderVersion = new AtomicInteger(order.getMqVersion());
            if (orderVersion.compareAndSet(payNotifyMQDTO.getMqVersion(),
                    orderVersion.get() + CONSUME_VERSION) ||
                    StringUtils.isEmpty(order.getOrderCode())) {
                /* 更新订单状态 */
                order.setOrderCode(payNotifyMQDTO.getPlatformNumber());
                order.setPaymentType(payNotifyMQDTO.getPayPlatform());
                order.setStatus(payNotifyMQDTO.getStatus());
                order.setMqVersion(orderVersion.get());
                try {
                    orderService.updateOrder(order);
                    String content = stockSubLockMessageContent(order.getId());
                    sendStockSubLockNotifyMessage(content);     // 通知删减库存
                    log.info("当前时间:{}, 订单号:{}, 付款成功.", new Date(), order.getId());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    log.info("当前时间:{}, 订单号:{}, 付款通知消费失败, 消息回退.", new Date(), order.getId());
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                    throw e;
                }
            }
        } else {
            // TODO 订单不存在、订单支付金额不对待处理
            log.info("当前时间:{}, 订单号:{}, 付款通知消息异常, 待处理.", new Date(), order.getId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private void sendStockSubLockNotifyMessage(String content) {
        Long messageId = orderMqLogService.getMessageId();
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, ORDER_NOTIFY_EXCHANGE, ORDER_STOCK_SUB_ROUTING_KEY);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(ORDER_NOTIFY_EXCHANGE, ORDER_STOCK_SUB_ROUTING_KEY, content, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务, 发送删减库存消息:{}.", content);
        }
    }

    private String stockSubLockMessageContent(Long orderNo) {
        StockSubLockMQDTO stockSubLockMQDTO = new StockSubLockMQDTO();
        stockSubLockMQDTO.setOrderNo(orderNo);
        stockSubLockMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(stockSubLockMQDTO);
    }

}
