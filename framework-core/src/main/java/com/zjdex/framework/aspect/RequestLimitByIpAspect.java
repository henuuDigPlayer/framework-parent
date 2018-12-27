package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.RequestLimitByIpAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.HttpRequestUtil;
import com.zjdex.framework.util.ResultCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: lindj
 * @date: 2018/12/17
 * @description:
 */
@Aspect
@Component
public class RequestLimitByIpAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.zjdex.framework.annotation.RequestLimitByIpAnnotation)")
    public void execute() {

    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param requestLimitByIpAnnotation RequestLimitByIpAnnotation
     */
    @Before("execute() && @annotation(requestLimitByIpAnnotation)")
    public void doBefore(JoinPoint joinPoint, RequestLimitByIpAnnotation requestLimitByIpAnnotation) throws IOException {
        if (requestLimitByIpAnnotation != null) {
            String key = requestLimitByIpAnnotation.key();
            Long timeout = requestLimitByIpAnnotation.timeout();
            Long limit = requestLimitByIpAnnotation.limit();
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
