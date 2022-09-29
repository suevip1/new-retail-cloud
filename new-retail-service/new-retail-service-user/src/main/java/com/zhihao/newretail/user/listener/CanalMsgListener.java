package com.zhihao.newretail.user.listener;

import com.google.gson.*;
import com.rabbitmq.client.Channel;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;

import static com.zhihao.newretail.user.consts.UserCacheKeyConst.USER_INFO;

@Slf4j
@Component
public class CanalMsgListener {

    @Autowired
    private MyRedisUtil redisUtil;

    private static final Gson gson = new GsonBuilder().create();

    @RabbitListener(queues = RabbitMQConst.CANAL_USER_QUEUE)
    public void canalMsgQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("用户服务，接收canal消息: {}", msgStr);
        JsonObject jsonObject = gson.fromJson(msgStr, JsonObject.class);
        JsonArray data = jsonObject.get("data").getAsJsonArray();
        JsonArray old = jsonObject.get("old").getAsJsonArray();
        for (JsonElement element : data) {
            HashMap<String, Object> dataMap = gson.fromJson(element, HashMap.class);
            Integer userId = Integer.valueOf(String.valueOf(dataMap.get("user_id")));
            redisUtil.deleteObject(String.format(USER_INFO, userId));
            /* 删除旧数据 */
            for (JsonElement oldData : old) {
                HashMap<String, Object> oldDataMap = gson.fromJson(oldData, HashMap.class);
                /* 抽取关键字段，删除旧 id 数据 */
                Object oldUserId = oldDataMap.get("user_id");
                if (!ObjectUtils.isEmpty(oldUserId)) {
                    redisUtil.deleteObject(String.format(USER_INFO, Integer.valueOf(String.valueOf(oldUserId))));
                }
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
