package com.zjdex.framework.service;


import java.util.Map;

/**
 * @author: lindj
 * @date: 2018/6/1 10:19
 * @description: redis操作
 */
public interface RedisService {


    /**
     * 写入缓存
     *
     * @param key        String
     * @param value      Object
     * @param expireTime Long
     * @return boolean
     */
    boolean set(final String key, Object value, Long expireTime);


    /**
     * 写入缓存
     *
     * @param key        String
     * @param value      Object
     * @param expireTime Long
     * @return boolean
     */
    boolean setNX(final String key, String value, Long expireTime);


    /**
     * 获取缓存
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 获取缓存
     *
     * @param key String
     * @return String
     */
    String get(String key);

    /**
     * 删除缓存
     *
     * @param key String
     */
    void del(String... key);

    /**
     * 计数器
     *
     * @param key    String
     * @param offset 递增数字
     * @return 当前计数value值
     */
    long increment(String key, Long offset);

    /**
     * 设置过期时间
     *
     * @param key     String
     * @param timeout Long
     */
    void expire(String key, Long timeout);

    /**
     * hash操作
     *
     * @param key     String
     * @param map     Map<String, Object>
     * @param timeout 过期时间
     */
    void setHash(String key, Map<String, Object> map, Long timeout);

    /**
     * 获取hashMap值
     *
     * @param key  String key
     * @param item String 属性名
     * @return Object
     */
    Object getHash(String key, String item);


    /**
     * 获取hashMap值
     *
     * @param key String key
     * @return Object
     */
    Map<String, Object> getHash(String key);


    /**
     * 批量删除key
     *
     * @param keyPattern String
     */
    void delBatch(String keyPattern);
}
