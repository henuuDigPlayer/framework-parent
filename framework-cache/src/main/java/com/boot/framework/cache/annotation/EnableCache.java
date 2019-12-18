package com.boot.framework.cache.annotation;

import com.boot.framework.cache.config.RedissonAutoConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lindongjie
 * @date 2019/12/17 3:53 下午
 * @describe
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(RedissonAutoConfig.class)
public @interface EnableCache {
}
