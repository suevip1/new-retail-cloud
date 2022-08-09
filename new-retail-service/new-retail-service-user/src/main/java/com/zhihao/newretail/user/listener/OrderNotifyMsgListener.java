package com.zhihao.newretail.user.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.user.pojo.UserCoupons;
import com.zhihao.newretail.user.service.UserCouponsService;
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
public class OrderNotifyMsgListener {

    @Autowired
    private UserCouponsService userCouponsService;

    @RabbitListener(queues = RabbitMQConst.ORDER_COUPONS_UNSUB_QUEUE)
    public void couponsUnSubQueue(String msgStr, Message message, Channel channel) throws IOException {
        CouponsUnSubMQDTO couponsUnSubMQDTO = GsonUtil.json2Obj(msgStr, CouponsUnSubMQDTO.class);
        Integer version = couponsUnSubMQDTO.getMqVersion();
        UserCoupons userCoupons = userCouponsService.getUserCouponsByCouponsId(couponsUnSubMQDTO.getCouponsId());

        if (!ObjectUtils.isEmpty(userCoupons)) {
            AtomicInteger userCouponsVersion = new AtomicInteger(userCoupons.getMqVersion());
            if (userCouponsVersion.compareAndSet(version, userCouponsVersion.get() + RabbitMQConst.CONSUME_VERSION)) {
                userCoupons.setQuantity(userCoupons.getQuantity() + couponsUnSubMQDTO.getQuantity());
                userCoupons.setMqVersion(userCouponsVersion.get());
                try {
                    userCouponsService.updateUserCoupons(userCoupons);
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("当前时间:{},优惠券id:{},回退优惠券", new Date(), couponsUnSubMQDTO.getCouponsId());
                } catch (Exception e) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                }
            }
        }
    }
}
