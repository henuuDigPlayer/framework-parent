package com.boot.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: lindj
 * @date: 2018/12/17
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventReqByIpAnnotation {

    /**
     * key
     * @return
     */
    String key() default "";

    /**
     * 过期时间 秒
     * @return
     */
    long timeout() default 0L;

    /**
     * 限制次数
     * @return
     */
    long limit() default 0L;

    /**
     * 描述
     * @return
     */
    String describe() default "";
}
