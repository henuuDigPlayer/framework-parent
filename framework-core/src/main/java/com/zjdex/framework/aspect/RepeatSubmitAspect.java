package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.RepeatSubmitAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.ConstantUtil;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private static final String REPEAT_SUBMIT = "repeat_submit";

    @Autowired
    private RedisService redisService;

    @Pointcut("execution(public * com.zjdex.controller..*(..))")
    public void execute() {
    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @Before("execute() && @annotation(repeatSubmitAnnotation)")
    public void doBefore(JoinPoint joinPoint, RepeatSubmitAnnotation repeatSubmitAnnotation) throws IOException {
        if (repeatSubmitAnnotation != null) {
            Boolean remove = repeatSubmitAnnotation.remove();
            Boolean create = repeatSubmitAnnotation.create();
            // 删除标识
            if (remove) {
                String flag = RequestHolder.getHeader("flag");
                if (StringUtil.isEmpty(flag) || StringUtil.isEmpty(redisService.get(flag))) {
                    throw new CodeException(ResultCode.Codes.DATATIMEOUT);
                }
                boolean isExist = this.redisService.setNX(this.getRepeatSubmitKey(flag),
                        UUID.randomUUID().toString(),
                        ConstantUtil.REQUEST_TIMEOUT);
                if (!isExist) {
                    throw new CodeException(ResultCode.Codes.SUBMIT_REPEAT);
                }
            }
        }
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint    ProceedingJoinPoint
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     * @return Object
     * @throws Throwable
     */
    @Around("execute() && @annotation(repeatSubmitAnnotation)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, RepeatSubmitAnnotation repeatSubmitAnnotation) throws Throwable {
        if (repeatSubmitAnnotation != null) {
            Boolean create = repeatSubmitAnnotation.create();
            if (create) {
                String flag = UUID.randomUUID().toString();
                redisService.set(flag, flag, ConstantUtil.TIMEOUT);
                return flag;
            }
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 请求处理完成
     *
     * @param joinPoint              JoinPoint
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @AfterReturning(value = "execute() && @annotation(repeatSubmitAnnotation)")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmitAnnotation repeatSubmitAnnotation) {
        if (repeatSubmitAnnotation != null) {
            Boolean remove = repeatSubmitAnnotation.remove();
            if (remove) {
                deleteKey(RequestHolder.getHeader("flag"));
            }
        }
    }

    /**
     * 请求其他异常
     *
     * @param joinPoint              JoinPoint
     * @param e
     * @param repeatSubmitAnnotation RepeatSubmitAnnotation
     */
    @AfterThrowing(pointcut = "execute() && @annotation(repeatSubmitAnnotation)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e,
                                RepeatSubmitAnnotation repeatSubmitAnnotation) {
        if (repeatSubmitAnnotation != null && (e instanceof CodeException == false)) {
            deleteKey(RequestHolder.getHeader("flag"));
        }
    }

    /**
     * 删除缓存
     *
     * @param flag String key
     */
    private void deleteKey(String flag) {
        String key = getRepeatSubmitKey(flag);
        String flagValue = redisService.get(flag);
        String value = redisService.get(key);
        if (!StringUtil.isEmpty(value)) {
            this.redisService.del(key);
        }
        if (!StringUtil.isEmpty(flagValue)) {
            this.redisService.del(flag);
        }
    }

    /**
     * 获取重复提交key
     *
     * @return String
     */
    private String getRepeatSubmitKey(String flag) {
        StringBuilder builder = new StringBuilder();
        builder.append(REPEAT_SUBMIT).append(":").append(flag);
        logger.info(builder.toString());
        return builder.toString();
    }
}
