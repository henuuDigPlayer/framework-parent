package com.zjdex.framework.util.limit;

import java.util.concurrent.TimeUnit;

/**
 * @author: lindj
 * @date: 2018/12/4
 * @description:
 */
public class MethodLimitUtil {
    private static com.google.common.util.concurrent.RateLimiter reateLimiter =
            com.google.common.util.concurrent.RateLimiter.create(5);

    public static boolean tryLimit() {
        return reateLimiter.tryAcquire(1, TimeUnit.SECONDS);
    }
}