package com.zhihao.newretail.pay.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.pay.enums.OrderStatusEnum;
import com.zhihao.newretail.pay.pojo.PayInfo;
import com.zhihao.newretail.pay.service.PayInfoService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.pay.PayNotifyMQDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
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
    private PayInfoService payInfoService;

    @RabbitListener(queues = RabbitMQConst.ORDER_CLOSE_PAY_QUEUE)
    public void orderClosePayQueue(Message message, Channel channel) throws IOException {
        String msgStr = new String(message.getBody());
        log.info("支付服务，接收关闭支付消息：{}", msgStr);
        PayNotifyMQDTO payNotifyMQDTO = GsonUtil.json2Obj(msgStr, PayNotifyMQDTO.class);
        Integer version = payNotifyMQDTO.getMqVersion();
        PayInfo payInfo = payInfoService.getPayInfo(payNotifyMQDTO.getOrderNo());

        if (!ObjectUtils.isEmpty(payInfo)
                && DeleteEnum.NOT_DELETE.getCode().equals(payInfo.getIsDelete())
                && OrderStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            AtomicInteger payVersion = new AtomicInteger(payInfo.getMqVersion());

            if (payVersion.compareAndSet(version, payVersion.get() + RabbitMQConst.CONSUME_VERSION)) {
                payInfo.setIsDelete(DeleteEnum.DELETE.getCode());
                payInfo.setMqVersion(payVersion.get());
                try {
                    payInfoService.updatePayInfo(payInfo);
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
