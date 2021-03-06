package com.boot.framework.core.config;

import com.boot.framework.core.model.SysConstant;
import com.boot.framework.common.util.data.StringUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.boot.framework.core.service.SysConstantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/2/27
 * @description
 */
@Component
public class SysConstantConfig {

    private static final Logger logger = LoggerFactory.getLogger(SysConstantConfig.class);

    @Autowired
    private SysConstantService sysConstantService;

    @Resource(name = "scheduledExecutorService")
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private ParamsConfig paramsConfig;

    private LoadingCache<String, String> readWriteCacheMap;


    @PostConstruct
    private void init() {
        this.readWriteCacheMap =
                CacheBuilder.newBuilder().initialCapacity(paramsConfig.getWriteMapSize())
                        .expireAfterWrite(paramsConfig.getWriteMapExpire(), TimeUnit.SECONDS)
                        .removalListener((notification) -> {
                            logger.info("{} was removed, cause is {}", notification.getKey(), notification.getCause());
                        })
                        // 缓存加载器，当缓存不存在时，会自动执行load方法，进行缓存加载。同时返回缓存数据
                        .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) throws Exception {
                                SysConstant sysConstant = sysConstantService.getContent(key);
                                if (sysConstant != null) {
                                    return sysConstant.getContent();
                                }
                                return null;
                            }
                        });
        scheduledExecutorService.scheduleAtFixedRate(getReadOnlyMapUpdateTask(),
                paramsConfig.getReadMapExpire(),
                paramsConfig.getReadMapExpire(), TimeUnit.SECONDS);
    }

    /**
     * 比较readOnlyMap与writeMap值，不一致时以readOnlyMap为准
     *
     * @return Runnable
     */
    private Runnable getReadOnlyMapUpdateTask() {
        return () -> {
            ConcurrentHashMap<String, String> readOnlyMap = SingletonEnum.INSTANCE.getReadMap();
            ConcurrentHashMap.KeySetView<String, String> keySetView = readOnlyMap.keySet();
            Iterator<String> iterator = keySetView.iterator();
            while (iterator.hasNext()) {
                try {
                    String key = iterator.next();
                    String readValue = readOnlyMap.get(key);
                    String writeValue = readWriteCacheMap.get(key);
                    if (!readValue.equals(writeValue)) {
                        readOnlyMap.put(key, writeValue);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 从readOnlyMap获取缓存
     *
     * @param name String
     * @return String
     */
    public String getValue(String name) {
        String value = SingletonEnum.INSTANCE.getContent(name);
        if (StringUtil.isEmpty(value)) {
            try {
                value = readWriteCacheMap.get(name);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            SingletonEnum.INSTANCE.getReadMap().put(name, value);
        }
        return value;
    }

    private enum SingletonEnum {
        /**
         * SingletonEnum
         */
        INSTANCE;
        private ConcurrentHashMap<String, String> readOnlyMap;

        SingletonEnum() {
            readOnlyMap =
                    new ConcurrentHashMap<String, String>();
        }

        public ConcurrentHashMap<String, String> getReadMap() {
            return readOnlyMap;
        }

        public String getContent(String name) {
            return readOnlyMap.get(name);
        }


    }

}
