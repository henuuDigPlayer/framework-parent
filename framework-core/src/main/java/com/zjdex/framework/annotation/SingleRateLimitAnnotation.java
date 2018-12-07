package com.zjdex.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: lindj
 * @date: 2018/12/4
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleRateLimitAnnotation {
}
