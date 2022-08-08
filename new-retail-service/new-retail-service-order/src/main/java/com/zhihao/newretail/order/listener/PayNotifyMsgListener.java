package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.api.message.dto.NotifyMessageDTO;
import com.zhihao.newretail.api.message.feign.MessageFeignService;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockSubLockMQDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;
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
    private MessageFeignService messageFeignService;

    @RabbitListener(queues = RabbitMQConst.PAY_NOTIFY_QUEUE_NAME)
    public void payNotifyQueue(String msgStr, Message message, Channel channel) throws IOException {
        PayNotifyMQDTO payNotifyMQDTO = GsonUtil.json2Obj(msgStr, PayNotifyMQDTO.class);
        Integer version = payNotifyMQDTO.getMqVersion();

        Order order = orderService.getOrder(payNotifyMQDTO.getOrderNo());
        if (!ObjectUtils.isEmpty(order)
                && DeleteEnum.NOT_DELETE.getCode().equals(order.getIsDelete())
                && payNotifyMQDTO.getUserId().equals(order.getUserId())
                && payNotifyMQDTO.getPayAmount().equals(order.getActualAmount())) {
            AtomicInteger atomicInteger = new AtomicInteger(order.getMqVersion());
            if (atomicInteger.compareAndSet(version, atomicInteger.get() + RabbitMQConst.CONSUME_VERSION)) {
                /* 更新订单状态 */
                order.setOrderCode(payNotifyMQDTO.getPlatformNumber());
                order.setPaymentType(payNotifyMQDTO.getPayPlatform());
                order.setStatus(payNotifyMQDTO.getStatus());
                order.setMqVersion(atomicInteger.get());
                try {
                    orderService.updateOrder(order);
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
                /* 通知删减库存 */
                StockSubLockMQDTO stockSubLockMQDTO = new StockSubLockMQDTO();
                stockSubLockMQDTO.setOrderNo(order.getId());
                stockSubLockMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
                sendStockSubLockNotifyMessage(GsonUtil.obj2Json(stockSubLockMQDTO));
                log.info("当前时间:{},订单号:{},付款成功", new Date(), order.getId());
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    private void sendStockSubLockNotifyMessage(String content) {
        NotifyMessageDTO notifyMessageDTO = new NotifyMessageDTO();
        notifyMessageDTO.setContent(content);
        notifyMessageDTO.setExchange(RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME);
        notifyMessageDTO.setRoutingKey(RabbitMQConst.ORDER_NOTIFY_STOCK_SUB_ROUTING_KEY);
        messageFeignService.sendNotifyMessage(notifyMessageDTO);
    }

}
