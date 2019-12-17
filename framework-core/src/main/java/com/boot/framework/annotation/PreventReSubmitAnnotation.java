package com.boot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lindj
 * @date: 2018/10/29
 * @description: 重复提交注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventReSubmitAnnotation {

    /**
     * 过期时间 单位秒
     * @return
     */

    long timeout() default 0L;

    /**
     * 关键字
     * @return
     */
    String key() default "";

    /**
     * 描述
     * @return
     */
    String describe() default "";

    /**
     * 限制次数
     * @return
     */
    long limit() default 1L;


}
