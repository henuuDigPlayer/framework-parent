package com.zjdex.framework.aspect;

import com.alibaba.fastjson.JSON;
import com.zjdex.framework.bean.BaseResponse;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.util.JsonUtil;
import com.zjdex.framework.util.ResponseUtil;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lindj
 * @date: 2018/4/12 14:43
 * @description: 数据统一处理类
 */
@Aspect
@Component
public class HttpRequestAspect {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestAspect.class);

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void execute() {
    }

    /**
     * 前置，输出请求日志
     *
     * @param joinPoint JoinPoint
     */
    @Before("execute()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute("begin", System.currentTimeMillis());
        // 请求地址
        logger.info("url = {}", request.getRequestURL());
        // 请求类型
        String method = request.getMethod();

        logger.info("method = {}", method);
        // 来源ip
        logger.info("ip = {}", request.getRemoteAddr());
        // 请求处理方法
        logger.info("class_method = {}", joinPoint.getSignature()
                .getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 请求参数
        try {
            if(!StringUtil.isEmpty(joinPoint.getArgs())){
                logger.info("args = {}",  JSON.toJSONString(joinPoint.getArgs()[0]));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    /**
     * 统一处理数据返回格式
     *
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return BaseResponse 处理结果或异常信息
     * @throws Throwable Throwable
     */
    @Around("execute()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return dealException(e);
        }
        return ResponseUtil.success(object);
    }

    /**
     * 后置，输出结果信息
     * @param object 环绕处理结果
     */
    @AfterReturning(pointcut = "execute()", returning = "object")
    public void doAfterReturing(Object object) {
        logger.info("response = {}", JsonUtil.objectToJson(object));
        HttpServletRequest request = this.getRequest();
        long begin = Long.parseLong(request.getAttribute("begin").toString());
        long end = System.currentTimeMillis();
        logger.info("completed = {}ms", (end - begin));

    }

    /**
     * 异常处理
     *
     * @param e Exception对象
     * @return BaseResponse 处理结果
     */
    public BaseResponse dealException(Exception e) {
        if (e instanceof CodeException) {
            return ResponseUtil.error((CodeException) e);
        }
        return ResponseUtil.error(ResultCode.Codes.BUSINESS_ERROR);
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
