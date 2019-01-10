package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.PreventReqByIpAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.http.HttpRequestUtil;
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
public class PreventRequestByIpAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.zjdex.framework.annotation.PreventReqByIpAnnotation)")
    public void execute() {

    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param preventReqByIpAnnotation PreventReqByIpAnnotation
     */
    @Before("execute() && @annotation(preventReqByIpAnnotation)")
    public void doBefore(JoinPoint joinPoint, PreventReqByIpAnnotation preventReqByIpAnnotation) throws IOException {
        if (preventReqByIpAnnotation != null) {
            String key = preventReqByIpAnnotation.key();
            Long timeout = preventReqByIpAnnotation.timeout();
            Long limit = preventReqByIpAnnotation.limit();
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
