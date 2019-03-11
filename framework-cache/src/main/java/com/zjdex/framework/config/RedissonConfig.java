package com.zjdex.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
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
@ConditionalOnProperty(name = "spring.redisson.enable", havingValue = "true")
@ConfigurationProperties(prefix="spring.redisson")
public class RedissonConfig {
    private Integer connectTimeout;
    private String password;
    private String clientName;
    private String address;
    private Integer connectionMinimumIdleSize;
    private Integer connectionPoolSize;
    private Integer database;
    private Long ttl;
    private Long maxIdleTime;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedissionClient() {
        Config config = new Config();
        config.useSingleServer()
                .setClientName(clientName)
                .setPassword(password)
                .setAddress(address)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
                .setConnectTimeout(connectTimeout);
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        config.put("testMap", new CacheConfig(ttl, maxIdleTime));
        return new RedissonSpringCacheManager(redissonClient, config);
    }



    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(Integer connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public Integer getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(Integer connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(Long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }
}
