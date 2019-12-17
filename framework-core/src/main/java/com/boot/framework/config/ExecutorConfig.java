package com.boot.framework.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ParamsConfig paramsConfig;

    /**
     * 线程池
     * @return
     */
    @Bean(name = "scheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        logger.info("ScheduledExecutorService start init");
        Integer corePoolSize = 5;
        Object value = this.paramsConfig.getThreadPool().get("corePoolSize");
        if(value != null){
            corePoolSize = Integer.parseInt(value.toString());
        }
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat("scheduled-thread-pool-%d").build();
        return new ScheduledThreadPoolExecutor(corePoolSize,
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
