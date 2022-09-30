package com.zhihao.newretail.pay.config;

import com.zhihao.newretail.pay.pojo.PayInfoMQLog;
import com.zhihao.newretail.pay.service.PayInfoMQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Date;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Slf4j
@Configuration
public class PayMessageCallbackConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PayInfoMQLogService payInfoMqLogService;

    @PostConstruct
    public void initRabbitTemplate() {
        /* 消息发布确认 */
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if (!ObjectUtils.isEmpty(correlationData)) {
                Long messageId = Long.valueOf(correlationData.getId());
                PayInfoMQLog payInfoMqLog = payInfoMqLogService.getMQLog(messageId);
                if (!ObjectUtils.isEmpty(payInfoMqLog)) {
                    if (ack) {
                        payInfoMqLog.setStatus(MessageStatusEnum.SEND_SUCCESS.getCode());
                        payInfoMqLogService.updateMessage(payInfoMqLog);
                        log.info("支付服务，消息发送成功，messageId：{}，messageContent：{}", messageId, payInfoMqLog.getContent());
                    } else {
                        /* 发送失败，重新发送 */
                        payInfoMqLog.setStatus(MessageStatusEnum.SEND_ERROR.getCode());
                        int updateMessageRow = payInfoMqLogService.updateMessage(payInfoMqLog);
                        if (updateMessageRow > 0) {
                            rabbitTemplate.convertAndSend(payInfoMqLog.getExchange(), payInfoMqLog.getRoutingKey(), payInfoMqLog.getContent());
                            log.info("支付服务，消息发送失败，尝试重新发送，messageId：{}，messageContent：{}", messageId, payInfoMqLog.getContent());
                        }
                    }
                }
            }
        });

        /* 回退消息 */
        rabbitTemplate.setReturnsCallback((ReturnedMessage returnedMessage) -> {
            String message = new String(returnedMessage.getMessage().getBody());
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            log.info("支付服务，消息回退");
            log.info("time:{},message:{},replyText:{},exchange:{},routingKey:{}", new Date(), message, replyText, exchange, routingKey);
        });
    }

}
