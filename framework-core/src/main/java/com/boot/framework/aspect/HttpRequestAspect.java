package com.boot.framework.aspect;

import com.boot.framework.bean.BaseResponse;
import com.boot.framework.exception.CodeException;
import com.boot.framework.util.ResponseUtil;
import com.boot.framework.util.ResultCode;
import com.boot.framework.util.constant.ConstantUtil;
import com.boot.framework.util.data.JsonUtil;
import com.boot.framework.util.data.StringUtil;
import com.boot.framework.holder.RequestHolder;
import com.boot.framework.util.http.HttpRequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
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
        logger.info("ip = {}", HttpRequestUtil.getIpAddr(request));
        // 请求处理方法
        logger.info("class_method = {}", joinPoint.getSignature()
                .getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 请求参数
        try {
            logger.info(request.getContentType());
            if (!request.getContentType().contains(ConstantUtil.FILE_CONTENT_TYPE) && !method.equals(ConstantUtil.METHOD_GET) && !StringUtil.isEmpty(joinPoint.getArgs())) {
                String requestParams = JsonUtil.objectToJson(joinPoint.getArgs()[0]);
                logger.info("args = {}", (requestParams));
            }
        } catch (
                Exception e) {
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
        BaseResponse baseResponse = ResponseUtil.success(object);
        logger.info("result = {}", JsonUtil.objectToJson(baseResponse));
        return baseResponse;
    }

    @After("execute()")
    public void complete() {
        HttpServletRequest request = RequestHolder.getRequest();
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
        logger.error(e.getMessage());
        return ResponseUtil.error(ResultCode.Codes.BUSINESS_ERROR.getCode(), "连接超时,请稍后重试");
    }
}
