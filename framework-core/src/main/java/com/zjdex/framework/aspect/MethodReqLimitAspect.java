package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.MethodReqLimitAnnotation;
import com.zjdex.framework.annotation.RepeatSubmitAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.ConstantUtil;
import com.zjdex.framework.util.HttpRequestUtil;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: lindj
 * @date: 2018/12/17
 * @description:
 */
@Aspect
@Component
public class MethodReqLimitAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.zjdex.framework.annotation.MethodReqLimitAnnotation)")
    public void execute() {

    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param methodReqLimitAnnotation MethodReqLimitAnnotation
     */
    @Before("execute() && @annotation(methodReqLimitAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodReqLimitAnnotation methodReqLimitAnnotation) throws IOException {
        if (methodReqLimitAnnotation != null) {
            String key = methodReqLimitAnnotation.key();
            Long timeout = methodReqLimitAnnotation.timeout();
            Long limit = methodReqLimitAnnotation.limit();
            String ip = HttpRequestUtil.getIpAddr(RequestHolder.getRequest());
            StringBuilder builder = new StringBuilder();
            builder.append(ip).append(":").append(key);
            long count = this.redisService.increment(builder.toString(), 1L);
            if(count == 1){
                this.redisService.expire(builder.toString(), timeout);
            }
            if(count > limit){
                throw new CodeException(ResultCode.Codes.SINLE_RATE_LIMIT);
            }
        }
    }
}
