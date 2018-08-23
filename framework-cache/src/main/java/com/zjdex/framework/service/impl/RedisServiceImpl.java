package com.zjdex.framework.service.impl;

import com.zjdex.framework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * @param key String
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
     * @param key String
     * @param value String
     * @return boolean
     */
    @Override
    public boolean setNX(String key, String value, Long expireTime) {
        if(this.redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(),
                value.getBytes())){
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 获取缓存
     * 注：该方法暂不支持Character数据类型
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T)redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存
     * @param key String
     * @return String
     */
    @Override
    public String get(String key) {
        return redisTemplate.boundValueOps(key).get().toString();
    }

    /**
     * 删除缓存
     * @param key String
     */
    @Override
    public void del(String... key) {
        if(key!=null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}
