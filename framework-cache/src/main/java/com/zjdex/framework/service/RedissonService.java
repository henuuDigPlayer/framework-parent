package com.zjdex.framework.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/1/9
 * @description
 */
public interface RedissonService {

    /**
     * 加锁
     *
     * @param lockKey String
     * @return RLock
     */
    RLock lock(String lockKey);

    /**
     * 加锁
     *
     * @param lockKey String
     * @param unit    单位
     * @param timeout long 过期时间
     * @return RLock
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     * 公平锁
     *
     * @param lockKey String
     * @return RLock
     */
    RLock fairLock(String lockKey);


    /**
     * 公平锁
     *
     * @param lockKey String
     * @param unit    单位
     * @param timeout long 过期时间
     * @return RLock
     */
    RLock fairLock(String lockKey, TimeUnit unit, long timeout);

    /**
     * 尝试加锁，最多等待waitTime，上锁以后leaseTime自动解锁
     *
     * @param lockKey   String
     * @param unit
     * @param waitTime  long
     * @param leaseTime long
     * @return boolean
     * @throws InterruptedException
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) throws InterruptedException;

    void unlock(String lockKey);

    void unlock(RLock lock);
}
