package com.zjdex.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lindj
 * @date 2019/1/4
 * @description redisson配置信息
 */

@Configuration
@ConditionalOnProperty(name = "spring.redission.enable", havingValue = "true")
@ConfigurationProperties(prefix="spring.redission")
public class RedissionConfig {
    /**
     * 连接空闲超时
     */
    private Integer idleConnectionTimeout;
    /**
     * 连接超时 毫秒
     */
    private Integer connectTimeout;
    /**
     * 命令等待超时 毫秒
     */
    private Integer timeout;
    /**
     * 命令失败重试次数
     */
    private Integer retryAttempts;
    /**
     * 命令重试发送时间间隔
     */
    private Integer retryInterval;
    private String password;
    /**
     * 单个连接最大订阅数量
     */
    private Integer subscriptionsPerConnection;
    private String clientName;
    private String address;
    private Integer subscriptionConnectionMinimumIdleSize;
    private Integer subscriptionConnectionPoolSize;
    /**
     * 最小空闲连接数
     */
    private Integer connectionMinimumIdleSize;
    private Integer connectionPoolSize;
    private Integer database;

    /**
     * redission配置
     * @return
     */
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
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setTimeout(timeout);
        return Redisson.create(config);
    }

    public Integer getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(Integer retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }

    public void setSubscriptionsPerConnection(Integer subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
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

    public Integer getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionMinimumIdleSize(Integer subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public Integer getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public void setSubscriptionConnectionPoolSize(Integer subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
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

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }
}
