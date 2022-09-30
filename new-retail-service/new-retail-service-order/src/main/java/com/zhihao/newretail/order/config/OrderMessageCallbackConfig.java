package com.zhihao.newretail.order.config;

import com.zhihao.newretail.order.pojo.OrderMQLog;
import com.zhihao.newretail.order.service.OrderMQLogService;
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
public class OrderMessageCallbackConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMQLogService orderMqLogService;

    @PostConstruct
    public void initRabbitTemplate() {
        /* 消息发布确认 */
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if (!ObjectUtils.isEmpty(correlationData)) {
                Long messageId = Long.valueOf(correlationData.getId());
                OrderMQLog orderMqLog = orderMqLogService.getMQLog(messageId);
                if (!ObjectUtils.isEmpty(orderMqLog)) {
                    if (ack) {
                        orderMqLog.setStatus(MessageStatusEnum.SEND_SUCCESS.getCode());
                        orderMqLogService.updateMessage(orderMqLog);
                        log.info("订单服务，消息发送成功，messageId：{}，messageContent：{}", messageId, orderMqLog.getContent());
                    } else {
                        /* 发送失败，重新发送 */
                        orderMqLog.setStatus(MessageStatusEnum.SEND_ERROR.getCode());
                        int updateMessageRow = orderMqLogService.updateMessage(orderMqLog);
                        if (updateMessageRow > 0) {
                            rabbitTemplate.convertAndSend(orderMqLog.getExchange(), orderMqLog.getRoutingKey(), orderMqLog.getContent());
                            log.info("订单服务，消息发送失败，尝试重新发送，messageId：{}，messageContent：{}", messageId, orderMqLog.getContent());
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
            log.info("订单服务，消息回退");
            log.info("time:{},message:{},replyText:{},exchange:{},routingKey:{}", new Date(), message, replyText, exchange, routingKey);
        });
    }
}
