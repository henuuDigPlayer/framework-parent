package com.zjdex.framework.service.impl;

import com.zjdex.framework.service.RedissonService;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RLongAdder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/1/9
 * @description redisson基本操作
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    private RedissonClient redissonClient;


    public <T> RBucket<T> getRBucket(String bucketName) {
        RBucket<T> bucket = redissonClient.getBucket(bucketName);
        return bucket;
    }

    public <T> RBucket<T> setRBucket(String bucketName, T value, Long timeout, TimeUnit unit) {
        RBucket<T> bucket = getRBucket(bucketName);
        bucket.set(value, timeout, unit);
        return bucket;
    }

    /**
     * 加锁
     *
     * @param lockKey String
     * @return RLock
     */
    @Override
    public RLock lock(String lockKey) {
        RLock rLock = this.redissonClient.getLock(lockKey);
        rLock.lock();
        return rLock;
    }

    /**
     * 加锁
     *
     * @param lockKey String
     * @param unit    单位
     * @param timeout long 过期时间
     * @return RLock
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock rLock = this.redissonClient.getLock(lockKey);
        rLock.lock(timeout, unit);
        return rLock;
    }

    /**
     * 公平锁
     *
     * @param lockKey String
     * @return RLock
     */
    @Override
    public RLock fairLock(String lockKey) {
        RLock rLock = this.redissonClient.getFairLock(lockKey);
        rLock.lock();
        return rLock;
    }

    /**
     * 公平锁
     *
     * @param lockKey String
     * @param unit    单位
     * @param timeout long 过期时间
     * @return RLock
     */
    @Override
    public RLock fairLock(String lockKey, TimeUnit unit, long timeout) {
        RLock rLock = this.redissonClient.getFairLock(lockKey);
        rLock.lock(timeout, unit);
        return rLock;
    }

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
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) throws InterruptedException {
        RLock rLock = this.redissonClient.getLock(lockKey);
        return rLock.tryLock(waitTime, leaseTime, unit);
    }

    /**
     * 解锁
     *
     * @param lockKey String
     */
    @Override
    public void unlock(String lockKey) {
        RLock rLock = this.redissonClient.getLock(lockKey);
        rLock.unlock();
    }

    /**
     * 解锁
     *
     * @param lock
     */
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
