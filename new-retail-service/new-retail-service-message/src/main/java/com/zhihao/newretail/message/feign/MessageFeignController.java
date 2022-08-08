package com.zhihao.newretail.message.feign;

import com.zhihao.newretail.api.message.dto.DelayedMessageDTO;
import com.zhihao.newretail.api.message.dto.NotifyMessageDTO;
import com.zhihao.newretail.api.message.feign.MessageFeignService;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.message.service.MQLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class MessageFeignController implements MessageFeignService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MQLogService mqLogService;

    @Override
    public Long sendDelayedMessage(DelayedMessageDTO delayedMessageDTO) {
        String content = delayedMessageDTO.getContent();
        String exchange = delayedMessageDTO.getExchange();
        String routingKey = delayedMessageDTO.getRoutingKey();
        Integer delayedTime = delayedMessageDTO.getDelayedTime();
        Long messageId = getMessageId();
        /* 发送消息前持久化，保证消息可靠性投递 */
        mqLogService.insetMessage(messageId, content, exchange, routingKey);
        CorrelationData correlationData = new CorrelationData(String.valueOf(messageId));
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setDelay(delayedTime);
            return message;
        }, correlationData);
        return messageId;
    }

    @Override
    public Long sendNotifyMessage(NotifyMessageDTO notifyMessageDTO) {
        String content = notifyMessageDTO.getContent();
        String exchange = notifyMessageDTO.getExchange();
        String routingKey = notifyMessageDTO.getRoutingKey();
        Long messageId = getMessageId();
        mqLogService.insetMessage(messageId, content, exchange, routingKey);
        CorrelationData correlationData = new CorrelationData(String.valueOf(messageId));
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationData);
        return messageId;
    }

    /*
    * 消息唯一id，雪花算法生成
    * */
    private Long getMessageId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 2);
        return snowflakeIdWorker.nextId();
    }

}
