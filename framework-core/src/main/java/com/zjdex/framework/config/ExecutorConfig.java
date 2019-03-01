package com.zjdex.framework.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lindj
 * @date: 2018/12/10
 * @description:
 */
@Configuration
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Value("${threadPool.corePoolSize}")
    private Integer corePoolSize;

    /**
     * 线程池
     * @return
     */
    @Bean(name = "scheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        logger.info("ScheduledExecutorService start init");
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        return new ScheduledThreadPoolExecutor(corePoolSize,
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
