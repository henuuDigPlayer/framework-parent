package com.zjdex.framework.service.impl;

import com.zjdex.framework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: lindj
 * @date: 2018/6/1 10:19
 * @description: redis操作
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key   String
     * @param value String
     * @return boolean
     */
    @Override
    public boolean setNX(String key, String value, Long expireTime) {
        if (this.redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(),
                value.getBytes())) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 获取缓存
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存
     *
     * @param key String
     * @return String
     */
    @Override
    public String get(String key) {
        Object value = redisTemplate.boundValueOps(key).get();
        if(value != null){
            return value.toString();
        }
        return null;
    }

    /**
     * 删除缓存
     *
     * @param key String
     */
    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 计数器
     *
     * @param key    String
     * @param offset 递增数字
     * @return 当前计数value值
     */
    @Override
    public long increment(String key, Long offset) {
        return this.redisTemplate.opsForValue().increment(key, offset);
    }

    /**
     * 设置过期时间
     *
     * @param key     String
     * @param timeout Long
     */
    @Override
    public void expire(String key, Long timeout) {
        this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * hash操作
     *
     * @param key     String
     * @param map     Map<String, Object>
     * @param timeout 过期时间
     */
    @Override
    public void setHash(String key, Map<String, Object> map, Long timeout) {
        this.redisTemplate.opsForHash().putAll(key, map);
        this.expire(key, timeout);
    }

}
