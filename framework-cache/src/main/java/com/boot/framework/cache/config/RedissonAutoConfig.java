package com.boot.framework.cache.config;

import com.boot.framework.cache.service.RedissonService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lindongjie
 * @date 2019/12/18 2:52 下午
 * @describe
 **/
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnMissingBean(RedissonService.class)
public class RedissonAutoConfig {

    @Autowired
    private RedissonProperties redissonProperties;


    @Bean(value = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = "sentinel")
    public RedissonClient getClient(){
        Config config = new Config();
        config.setCodec(new FastjsonCodec());
        config.useSentinelServers()
                .setPassword(redissonProperties.getPassword())
                .setClientName(redissonProperties.getClientName())
                .addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setDatabase(redissonProperties.getDatabase());

        return Redisson.create(config);
    }
    @Bean(value = "redissonClient", destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.mode", havingValue = "single")
    public RedissonClient getSingleClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setPassword(redissonProperties.getPassword())
                .setClientName(redissonProperties.getClientName())
                .setDatabase(redissonProperties.getDatabase());
        return Redisson.create(config);
    }

    @Bean("redissonService")
    public RedissonService getRedissonService(RedissonClient redissonClient){
        return new RedissonService(redissonClient);
    }
}
