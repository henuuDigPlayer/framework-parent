package com.boot.framework.service;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/3/21
 * @description
 */
@Service
@ConditionalOnProperty(name = "redisson.enable", havingValue = "true")
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;


    /**
     * bucket 操作
     *
     * @param key      String
     * @param value    object
     * @param timeout  long
     * @param timeUnit
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        RBucket buctet = this.redissonClient.getBucket(key);
        buctet.set(value, timeout, timeUnit);
    }

    /**
     * bucket 操作
     *
     * @param key   String
     * @param clazz 类型
     */
    public <T> T get(String key, Class<T> clazz) {
        RBucket<T> buctet = this.redissonClient.getBucket(key);
        return buctet.get();
    }

    /**
     * 对象删除
     *
     * @param key String
     * @return boolean
     */
    public boolean delete(String key) {
        RBucket buctet = this.redissonClient.getBucket(key);
        return buctet.delete();
    }

    /**
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void setAsync(String key, Object value, long timeout, TimeUnit timeUnit) {
        RBucket buctet = this.redissonClient.getBucket(key);
        buctet.setAsync(value, timeout, timeUnit);
    }

    public boolean trySet(String key, Object value, long timeout, TimeUnit timeUnit) {
        RBucket buctet = this.redissonClient.getBucket(key);
        return buctet.trySet(value, timeout, timeUnit);
    }

    public long incrementAtomicLong(String key) {
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        return atomicLong.incrementAndGet();
    }

    public boolean expireAtomicLong(String key, long timeout, TimeUnit timeUnit) {
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        return atomicLong.expire(timeout, timeUnit);
    }

    public RFuture<Boolean> expireAsyncAtomicLong(String key, long timeout, TimeUnit timeUnit) {
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        return atomicLong.expireAsync(timeout, timeUnit);
    }

    public long getAtomicLong(String key) {
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        return atomicLong.get();
    }

    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }


}
