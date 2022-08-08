package com.zhihao.newretail.message.feign;

import com.zhihao.newretail.api.message.dto.DelayedMessageDTO;
import com.zhihao.newretail.api.message.feign.MessageFeignService;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.message.pojo.MQLog;
import com.zhihao.newretail.message.service.MQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
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
    public void sendDelayedMessage(DelayedMessageDTO delayedMessageDTO) {
        String content = delayedMessageDTO.getContent();
        String exchange = delayedMessageDTO.getExchange();
        String routingKey = delayedMessageDTO.getRoutingKey();
        Integer delayedTime = delayedMessageDTO.getDelayedTime();
        Long messageId = getMessageId();

        /* 发送消息前持久化，保证消息可靠性投递 */
        MQLog mqLog = new MQLog();
        mqLog.setMessageId(messageId);
        mqLog.setContent(content);
        mqLog.setExchange(exchange);
        mqLog.setRoutingKey(routingKey);
        mqLog.setStatus(MessageStatusEnum.NEW_MESSAGE.getCode());
        mqLogService.insetMessage(mqLog);

        CorrelationData correlationData = new CorrelationData(String.valueOf(messageId));
        rabbitTemplate.convertAndSend(exchange, routingKey, content, message -> {
            message.getMessageProperties().setDelay(delayedTime);
            return message;
        }, correlationData);
    }

    /*
    * 消息唯一id，雪花算法生成
    * */
    private Long getMessageId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 2);
        return snowflakeIdWorker.nextId();
    }

}
