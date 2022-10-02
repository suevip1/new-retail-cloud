package com.zhihao.newretail.product.listener;

import com.google.gson.*;
import com.rabbitmq.client.Channel;
import com.zhihao.newretail.product.consts.TableNameConst;
import com.zhihao.newretail.product.factory.ProductCacheSyncFactory;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CanalMsgListener {

    @Autowired
    private ProductCacheSyncFactory productCacheSyncFactory;

    private static final Gson gson = new GsonBuilder().create();

    @RabbitListener(queues = RabbitMQConst.CANAL_PRODUCT_QUEUE)
    public void canalMsgQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("商品服务，接收canal消息: {}", msgStr);
        JsonObject json = gson.fromJson(msgStr, JsonObject.class);
        JsonElement data = json.get("data");
        if (!data.isJsonNull()) {
            for (JsonElement element : data.getAsJsonArray()) {
                JsonObject jsonData = element.getAsJsonObject();
                String table = json.get("table").getAsString();
                if (!TableNameConst.TB_CATEGORY.equals(table)) {
                    productCacheSyncFactory.productCacheSyncService(table).productCacheRemove(jsonData.get("spu_id").getAsInt());
                    log.info("商品服务，当前同步数据处理完成");
                    JsonElement old = json.get("old");
                    if (!old.isJsonNull()) {
                        for (JsonElement oldData : old.getAsJsonArray()) {
                            JsonObject jsonOldData = oldData.getAsJsonObject();
                            productCacheSyncFactory.productCacheSyncService(table).productCacheRemove(jsonOldData.get("spu_id").getAsInt());
                            log.info("商品服务，旧数据删除成功");
                        }
                    }
                } else {
                    productCacheSyncFactory.productCacheSyncService(table).productCacheRemove(null);
                    log.info("商品服务，首页缓存同步数据处理完成");
                }
            }
        } else {
            log.info("商品服务，同步数据为空，无需处理");
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
