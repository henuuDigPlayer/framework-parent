package com.zjdex.framework.service;

/**
 * @author: lindj
 * @date: 2018/6/1 10:19
 * @description: redis操作
 */
//@Service
public class RedisService {

//    @Autowired
  /*  private RedisTemplate redisTemplate;

    *//**
     * 写入缓存
     *
     * @param key   String
     * @param value Object
     * @return boolean
     *//*
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

    *//**
     * 获取缓存
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     *//*
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    *//**
     * 获取缓存
     *
     * @param key String
     * @return String
     *//*
    public String get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }


    *//**
     * 删除缓存
     *
     * @param key String
     *//*
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    *//**
     * 计数器
     *
     * @param key    String
     * @param offset 递增数字
     * @return 当前计数value值
     *//*
    public long increment(String key, Long offset) {
        return this.redisTemplate.opsForValue().increment(key, offset);
    }

    *//**
     * 设置过期时间
     *
     * @param key     String
     * @param timeout Long
     *//*
    public void expire(String key, Long timeout) {
        this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    *//**
     * hash操作
     *
     * @param key     String
     * @param map     Map<String, Object>
     * @param timeout 过期时间
     *//*
    public void setHash(String key, Map<String, Object> map, Long timeout) {
        this.redisTemplate.opsForHash().putAll(key, map);
        this.expire(key, timeout);
    }

    *//**
     * 获取hashMap值
     *
     * @param key  String key
     * @param item String 属性名
     * @return Object
     *//*
    public Object getHash(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    *//**
     * 获取hashMap值
     *
     * @param key String key
     * @return Object
     *//*
    public Map<String, Object> getHash(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    *//**
     * 批量删除key
     *
     * @param keyPattern String
     *//*
    public void delBatch(String keyPattern) {
        this.redisTemplate.delete(this.redisTemplate.keys(keyPattern));
    }*/
}
