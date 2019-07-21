package com.zjdex.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lindj
 * @date 2019/1/4
 * @description redisson配置信息
 */

@Configuration
@ConditionalOnProperty(name = "redisson.enable", havingValue = "true")
@ConditionalOnExpression("'${redisson.mode}'=='single' or '${redisson.mode}'=='sentinel'")
public class RedissonConfig {
    @Autowired
    private RedissonProperties redissonProperties;


    @Bean(value = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = "sentinel")
    public RedissonClient getClient(){
        Config config = new Config();
        config.setCodec(new FastjsonCodec());
        config.useSentinelServers()
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setTimeout(redissonProperties.getTimeout())
                .setPassword(redissonProperties.getPassword())
                .setClientName(redissonProperties.getClientName())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                .addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setDatabase(redissonProperties.getDatabase());

        return Redisson.create(config);
    }
    @Bean(value = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = "single")
    public RedissonClient getSingleClient() {
        Config config = new Config();
        config.setCodec(new FastjsonCodec());
        config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setTimeout(redissonProperties.getTimeout())
                .setPassword(redissonProperties.getPassword())
                .setClientName(redissonProperties.getClientName())
                .setDatabase(redissonProperties.getDatabase())
                .setConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize());
        return Redisson.create(config);
    }
}
