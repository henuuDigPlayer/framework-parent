package com.zjdex.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lindj
 * @date 2019/1/4
 * @description redisson配置信息
 */

@Configuration
@ConfigurationProperties(prefix="redisson")
public class RedissonConfig {
    @Autowired
    private RedissonProperties redissonProperties;

    @Bean(value = "redissonClient", destroyMethod = "shutdown")
    public RedissonClient getClient(){
        Config config = new Config();
        config.useSentinelServers()
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setTimeout(redissonProperties.getTimeout())
                .setPassword(redissonProperties.getPassword())
                .setClientName(redissonProperties.getClientName())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setDatabase(redissonProperties.getDatabase());

        return Redisson.create(config);
    }
}
