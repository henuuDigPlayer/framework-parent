package com.zjdex.framework.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.zjdex.framework.util.ResponseUtil;
import com.zjdex.framework.util.ResultCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: lindj
 * @date: 2018/12/4
 * @description:
 */
@Aspect
@Component
public class SingleRateLimitAspect {

    private RateLimiter rateLimiter = RateLimiter.create(5);

    @Pointcut("@annotation(com.zjdex.framework.annotation.SingleRateLimitAnnotation)")
    public void execute() {

    }

    @Around("execute()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Boolean flag = rateLimiter.tryAcquire();
        Object object = null;
        if (flag) {
            object = proceedingJoinPoint.proceed();
            return object;
        }
        return ResponseUtil.error(ResultCode.Codes.SINLE_RATE_LIMIT);
    }

}
