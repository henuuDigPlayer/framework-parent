package com.zjdex.framework.annotation;

import java.lang.annotation.*;

/**
 * @author lindj
 * @date 2019/7/4 0004
 * @description 基于redisson分布式锁
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheLock {

    /**
     * key前缀
     *
     * @return String
     */
    String prefix() default "";

    /**
     * 轮询时间 单位毫秒
     *
     * @return long
     */
    long timeout() default 3000L;

    /**
     * 过期时间 单位毫秒
     *
     * @return long
     */
    long expireTime() default 2000L;

    /**
     * spel表达式
     *
     * @return String
     */
    String value() default "";

    /**
     * 描述
     *
     * @return String
     */
    String describe() default "";

}
