package com.boot.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lindj
 * @date 2019/3/1
 * @description
 */
@Component
@ConfigurationProperties(prefix = "constants")
public class ParamsConfig {

    private Long writeMapExpire;
    private Long readMapExpire;
    private Integer writeMapSize;
    private Map<String, Object> threadPool;

    public Long getWriteMapExpire() {
        return writeMapExpire;
    }

    public void setWriteMapExpire(Long writeMapExpire) {
        this.writeMapExpire = writeMapExpire;
    }

    public Long getReadMapExpire() {
        return readMapExpire;
    }

    public void setReadMapExpire(Long readMapExpire) {
        this.readMapExpire = readMapExpire;
    }

    public Integer getWriteMapSize() {
        return writeMapSize;
    }

    public void setWriteMapSize(Integer writeMapSize) {
        this.writeMapSize = writeMapSize;
    }

    public Map<String, Object> getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(Map<String, Object> threadPool) {
        this.threadPool = threadPool;
    }
}
