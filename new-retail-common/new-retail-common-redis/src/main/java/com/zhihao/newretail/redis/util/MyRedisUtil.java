package com.zhihao.newretail.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
* redis 常用api
* */
@Component
public class MyRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /*
    * Redis 存对象(无时间限制)
    * */
    public void setObject(String key, Object value) {
        setObject(key, value, null);
    }

    /*
    * Redis 存对象(时间限制)
    * */
    public void setObject(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value);

        if (timeout != null) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /*
    * Redis 存 Hash(无时间限制)
    * */
    public void setHash(String key, Object hashKey, Object value) {
        setHash(key, hashKey, value, null);
    }

    /*
    * Redis 存 Hash(时间限制)
    * */
    public void setHash(String key, Object hashKey, Object value, Long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);

        if (timeout != null) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /*
    * 判断 key 是否存在
    * */
    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /*
    * Redis 获取对象
    * */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /*
    * Redis 获取 Map
    * */
    public Map<Object, Object> getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /*
    * Redis 获取 MapValue
    * */
    public Object getMapValue(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /*
    * Redis 删除Key
    * */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /*
    * Redis 删除一个Map键值对
    * */
    public void deleteEntry(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /*
    * 执行脚本
    * */
    public Long executeScript(String script, String key, Object value) {
        return redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(key), value);
    }

}
