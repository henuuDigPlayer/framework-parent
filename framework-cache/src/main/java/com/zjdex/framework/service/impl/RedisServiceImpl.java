package com.zjdex.framework.service.impl;

import com.zjdex.framework.constant.RedisConstant;
import com.zjdex.framework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import java.util.Collections;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource(name = "lockRedisScript")
    private DefaultRedisScript<Long> lockRedisScript;

    /**
     * 写入缓存
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    @Override
    public boolean set(String key, Object value, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForValue().set(key, value);
                return true;
            }
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 写入缓存
     *
     * @param key        String
     * @param value      String
     * @param expireTime long 毫秒
     * @return boolean
     */
    @Override
    public boolean setNX(String key, String value, Long expireTime) {
        return (Boolean) stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis commands = (Jedis) redisConnection.getNativeConnection();
            String result = commands.set(key, value, RedisConstant.SET_IF_NOT_EXIST,
                    RedisConstant.SET_WITH_EXPIRE_TIME, expireTime);
            if (RedisConstant.LOCK_MSG.equals(result)) {
                return true;
            }
            return false;
        });
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
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
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

    /**
     * 获取hashMap值
     *
     * @param key  String key
     * @param item String 属性名
     * @return Object
     */
    @Override
    public Object getHash(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashMap值
     *
     * @param key String key
     * @return Object
     */
    @Override
    public Map<String, Object> getHash(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 批量删除key
     *
     * @param keyPattern String
     */
    @Override
    public void delBatch(String keyPattern) {
        this.redisTemplate.delete(this.redisTemplate.keys(keyPattern));
    }

}
