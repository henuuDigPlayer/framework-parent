package com.zjdex.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    /**
     * 连接超时 毫秒
     */
    private Integer connectTimeout;
    /**
     * 命令等待超时
     */
    private Integer timeout;
    private String password;
    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * 主节点连接池大小
     */
    private Integer masterConnectionPoolSize;
    /**
     * 主节点最小空闲连接数
     */

    private Integer masterConnectionMinimumIdleSize;
    /**
     * 连接空闲超时
     */
    private Integer idleConnectionTimeout;
    /**
     * 重新连接时间间隔
     */
    private Integer reconnectionTimeout;
    /**
     * 从节点连接池大小
     */
    private Integer slaveConnectionPoolSize;
    /**
     * 从节点最小空闲连接数
     */
    private Integer slaveConnectionMinimumIdleSize;

    private String[] sentinelAddresses;
    private Integer database;
    private String masterName;

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

    public Integer getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public Integer getMasterConnectionMinimumIdleSize() {
        return masterConnectionMinimumIdleSize;
    }

    public void setMasterConnectionMinimumIdleSize(Integer masterConnectionMinimumIdleSize) {
        this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
    }

    public Integer getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public Integer getReconnectionTimeout() {
        return reconnectionTimeout;
    }

    public void setReconnectionTimeout(Integer reconnectionTimeout) {
        this.reconnectionTimeout = reconnectionTimeout;
    }

    public Integer getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public Integer getSlaveConnectionMinimumIdleSize() {
        return slaveConnectionMinimumIdleSize;
    }

    public void setSlaveConnectionMinimumIdleSize(Integer slaveConnectionMinimumIdleSize) {
        this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
    }

    public String[] getSentinelAddresses() {
        return sentinelAddresses;
    }

    public void setSentinelAddresses(String[] sentinelAddresses) {
        this.sentinelAddresses = sentinelAddresses;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
