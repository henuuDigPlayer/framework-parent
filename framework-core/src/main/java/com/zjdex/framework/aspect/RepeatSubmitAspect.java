package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.RepeatSubmitAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.ConstantUtil;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: lindj
 * @date: 2018/10/31
 * @description: 重复提交处理
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(RepeatSubmitAspect.class.getName());

    @Autowired
    private RedisService redisService;

    @Pointcut("@within(com.zjdex.controller.*)")
    public void execute() {
    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @Before("execute() && @annotation(repeatSubmitAnnotation)")
    public void doBefore(JoinPoint joinPoint, RepeatSubmitAnnotation repeatSubmitAnnotation) {
        if (repeatSubmitAnnotation != null) {
            String key = getRepeatSubmitKey(joinPoint);
            String value = redisService.get(key);
            if (!StringUtil.isEmpty(value)) {
                throw new CodeException(ResultCode.Codes.SUBMIT_REPEAT);
            }
            boolean isExist = this.redisService.setNX(key, UUID.randomUUID().toString(),
                    ConstantUtil.REQUEST_TIMEOUT);
            if (!isExist) {
                throw new CodeException(ResultCode.Codes.BUSINESS_ERROR);
            }
        }
    }

    /**
     * 请求处理完成
     *
     * @param joinPoint              JoinPoint
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @AfterReturning("execute() && @annotation(repeatSubmitAnnotation)")
    public void doAfterReturning(JoinPoint joinPoint,
                                 RepeatSubmitAnnotation repeatSubmitAnnotation) {
        if (repeatSubmitAnnotation != null) {
            String key = getRepeatSubmitKey(joinPoint);
            String value = redisService.get(key);
            if (!StringUtil.isEmpty(value)) {
                this.redisService.del(key);
            }
        }
    }

    /**
     * 请求其他异常
     * @param joinPoint JoinPoint
     * @param e
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @AfterThrowing(pointcut = "execute() && @annotation(repeatSubmitAnnotation)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e,
                                RepeatSubmitAnnotation repeatSubmitAnnotation) {
        if (repeatSubmitAnnotation != null && (e instanceof CodeException == false)) {
            String key = getRepeatSubmitKey(joinPoint);
            String value = redisService.get(key);
            if (!StringUtil.isEmpty(value)) {
                this.redisService.del(key);
            }
        }
    }


    /**
     * 获取重复提交key
     *
     * @param joinPoint JoinPoint
     * @return String
     */
    private String getRepeatSubmitKey(JoinPoint joinPoint) {
        String token = RequestHolder.getTokenWithException();
        StringBuilder builder = new StringBuilder();
        builder.append(token)
                .append(".")
                .append(joinPoint.getSignature().getDeclaringTypeName())
                .append(".")
                .append(joinPoint.getSignature().getName());
        return builder.toString();
    }

}
