package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.api.message.dto.NotifyMessageDTO;
import com.zhihao.newretail.api.message.feign.MessageFeignService;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.order.enums.OrderStatusEnum;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.rabbitmq.dto.order.OrderCloseMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
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
public class OrderCloseMsgListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageFeignService messageFeignService;

    @RabbitListener(queues = RabbitMQConst.ORDER_DELAYED_QUEUE_NAME)
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
            AtomicInteger atomicInteger = new AtomicInteger(order.getMqVersion());
            if (atomicInteger.compareAndSet(version, atomicInteger.get() + RabbitMQConst.CONSUME_VERSION)) {
                order.setIsDelete(DeleteEnum.DELETE.getCode());
                order.setMqVersion(atomicInteger.get());
                try {
                    orderService.updateOrder(order);
                    sendStockUnLockNotifyMessage(order.getId());    // 发送消息解锁库存
                    if (!ObjectUtils.isEmpty(order.getCouponsId())) {
                        sendCouponsUnSubNotifyMessage(order.getCouponsId());    // 发送消息回滚优惠券
                    }
                    log.info("当前时间:{},订单号:{},关闭订单", new Date(), order.getId());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        } else {
            try {
                sendStockUnLockNotifyMessage(order.getId());
                if (!ObjectUtils.isEmpty(order.getCouponsId())) {
                    sendCouponsUnSubNotifyMessage(order.getCouponsId());
                }
                log.info("当前时间:{},订单号:{},关闭订单", new Date(), order.getId());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            }
        }
    }

    /*
    * 发送解锁库存消息
    * */
    private void sendStockUnLockNotifyMessage(Long orderNo) throws InterruptedException {
        String stockUnLockMessage = getStockUnLockMessage(orderNo);
        NotifyMessageDTO notifyMessageDTO = new NotifyMessageDTO();
        notifyMessageDTO.setContent(stockUnLockMessage);
        notifyMessageDTO.setExchange(RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME);
        notifyMessageDTO.setRoutingKey(RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_ROUTING_KEY);
        Long sendStockUnLockMessageId;
        try {
            /* 发送消息，成功正常返回 */
            sendStockUnLockMessageId = messageFeignService.sendNotifyMessage(notifyMessageDTO);     // 发送消息
            if (ObjectUtils.isEmpty(sendStockUnLockMessageId)) {
                throw new ServiceException("发送库存解锁消息异常");
            }
        } catch (Exception e) {
            /* 出现异常，自旋尝试 */
            do {
                Thread.sleep(15000);    // 间隔15秒
                sendStockUnLockMessageId = messageFeignService.sendNotifyMessage(notifyMessageDTO);     // 重新发送消息
            } while (ObjectUtils.isEmpty(sendStockUnLockMessageId));
        }
    }

    /*
    * 发送优惠券回退消息
    * */
    private void sendCouponsUnSubNotifyMessage(Integer couponsId) throws InterruptedException {
        String couponsUnSubMessage = getCouponsUnSubMessage(couponsId);
        NotifyMessageDTO notifyMessageDTO = new NotifyMessageDTO();
        notifyMessageDTO.setContent(couponsUnSubMessage);
        notifyMessageDTO.setExchange(RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME);
        notifyMessageDTO.setRoutingKey(RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_ROUTING_KEY);
        Long couponsUnSubMessageId;
        try {
            couponsUnSubMessageId = messageFeignService.sendNotifyMessage(notifyMessageDTO);
            if (ObjectUtils.isEmpty(couponsUnSubMessageId)) {
                throw new ServiceException("发送优惠券回退消息异常");
            }
        } catch (Exception e) {
            do {
                Thread.sleep(15000);
                couponsUnSubMessageId = messageFeignService.sendNotifyMessage(notifyMessageDTO);
            } while (ObjectUtils.isEmpty(couponsUnSubMessageId));
        }
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

}
