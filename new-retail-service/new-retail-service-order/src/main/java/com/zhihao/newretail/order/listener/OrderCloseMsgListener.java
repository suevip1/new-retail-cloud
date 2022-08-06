package com.zhihao.newretail.order.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RabbitTemplate rabbitTemplate;

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
                orderService.updateOrder(order);
                /*
                 * 发送消息解锁库存
                 * */
                StockUnLockMQDTO stockUnLockMQDTO = new StockUnLockMQDTO();
                stockUnLockMQDTO.setOrderNo(order.getId());
                stockUnLockMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
                rabbitTemplate.convertAndSend(
                        RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME,
                        RabbitMQConst.ORDER_NOTIFY_STOCK_UNLOCK_ROUTING_KEY,
                        GsonUtil.obj2Json(stockUnLockMQDTO)
                );
                /*
                 * 发送消息回滚优惠券
                 * */
                if (!ObjectUtils.isEmpty(order.getCouponsId())) {
                    CouponsUnSubMQDTO couponsUnSubMQDTO = new CouponsUnSubMQDTO();
                    couponsUnSubMQDTO.setCouponsId(order.getCouponsId());
                    couponsUnSubMQDTO.setQuantity(1);
                    couponsUnSubMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
                    rabbitTemplate.convertAndSend(
                            RabbitMQConst.ORDER_NOTIFY_EXCHANGE_NAME,
                            RabbitMQConst.ORDER_NOTIFY_COUPONS_UNSUB_ROUTING_KEY,
                            GsonUtil.obj2Json(couponsUnSubMQDTO)
                    );
                }
                log.info("当前时间:{},订单号:{},关闭订单", new Date(), order.getId());
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
