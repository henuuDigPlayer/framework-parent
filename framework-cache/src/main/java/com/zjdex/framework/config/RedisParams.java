package com.zjdex.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: lindj
 * @date: 2018/11/23
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisParams {

    private String host;
    private Integer database;
    private Integer port;
    private String password;
    private Map<String, Integer> pool;
    private Long timeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Map<String, Integer> getPool() {
        return pool;
    }

    public void setPool(Map<String, Integer> pool) {
        this.pool = pool;
    }
}
