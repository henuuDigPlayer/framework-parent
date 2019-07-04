package com.zjdex.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private String mode;

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
    private String address;

}
