package com.zhihao.newretail.user.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static com.zhihao.newretail.user.consts.UserCacheKeyConst.USER_INFO;

@Slf4j
@Component
public class CanalMsgListener {

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private ThreadPoolExecutor executor;

    private static final Gson gson = new GsonBuilder().create();

    @RabbitListener(queues = RabbitMQConst.CANAL_USER_QUEUE)
    public void canalMsgQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("用户服务, 接收canal消息:{}.", msgStr);
        JsonObject json = gson.fromJson(msgStr, JsonObject.class);
        CompletableFuture<Void> syncCurrentDataFuture = CompletableFuture.runAsync(() -> {
            JsonElement data = json.get("data");
            if (!data.isJsonNull()) {
                JsonArray currentDataList = data.getAsJsonArray();
                for (JsonElement jsonElement : currentDataList) {
                    JsonObject currentData = jsonElement.getAsJsonObject();
                    redisUtil.deleteObject(String.format(USER_INFO, currentData.get("user_id").getAsInt()));
                    log.info("用户服务, 当前数据处理完成.");
                }
            } else {
                log.info("用户服务, 当前数据为空, 无需处理.");
            }
        }, executor);
        CompletableFuture<Void> syncOldDataFuture = CompletableFuture.runAsync(() -> {
            JsonElement old = json.get("old");
            if (!old.isJsonNull()) {
                JsonArray oldDataList = old.getAsJsonArray();
                for (JsonElement jsonElement : oldDataList) {
                    JsonObject oldData = jsonElement.getAsJsonObject();
                    if (!ObjectUtils.isEmpty(oldData.get("user_id"))) {
                        redisUtil.deleteObject(String.format(USER_INFO, oldData.get("user_id").getAsInt()));
                        log.info("用户服务, 旧数据删除成功.");
                    } else {
                        log.info("用户服务, 旧数据为空, 无需处理.");
                    }
                }
            } else {
                log.info("用户服务, 旧数据为空, 无需处理.");
            }
        }, executor);
        CompletableFuture.allOf(syncCurrentDataFuture, syncOldDataFuture).join();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
