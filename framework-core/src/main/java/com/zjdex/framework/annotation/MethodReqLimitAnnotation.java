package com.zjdex.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: lindj
 * @date: 2018/12/17
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodReqLimitAnnotation {

    String key() default "";
    long timeout() default 0L;
    long limit() default 0L;
}
