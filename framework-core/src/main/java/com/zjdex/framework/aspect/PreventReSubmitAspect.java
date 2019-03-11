package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.PreventReSubmitAnnotation;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.service.RedisService;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.data.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author lindj
 * @date 2018/12/29
 * @description key为spl表达式
 */

@Aspect
@Component
public class PreventReSubmitAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.zjdex.framework.annotation.PreventReSubmitAnnotation)")
    public void execute() {

    }

    /**
     * 前置处理
     *
     * @param joinPoint              JoinPoint
     * @param preventReSubmitAnnotation PreventReSubmitAnnotation
     */
    @Before("execute() && @annotation(preventReSubmitAnnotation)")
    public void doBefore(JoinPoint joinPoint, PreventReSubmitAnnotation preventReSubmitAnnotation) throws Exception {
        if (preventReSubmitAnnotation != null) {
            String key = preventReSubmitAnnotation.key();
            Long timeout = preventReSubmitAnnotation.timeout();
            long limit = preventReSubmitAnnotation.limit();
            Object[] objects = joinPoint.getArgs();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            if(!StringUtil.isEmpty(objects)){
                Parameter[] parameters = method.getParameters();
                ExpressionParser parser = new SpelExpressionParser();
                EvaluationContext context = new StandardEvaluationContext();
                for(int i = 0; i <= objects.length-1;  i ++){
                    System.out.println(parameters[i].getName() + "" );
                    context.setVariable(parameters[i].getName(), objects[i]);
                }
                key = parser.parseExpression(key).getValue(context, String.class);
            }
            StringBuilder builder = new StringBuilder();
            builder.append(method.getName()).append(":").append(key);
            Long count = this.redisService.increment(builder.toString(), 1L);
            if(count ==1){
                this.redisService.expire(builder.toString(), timeout);
            }
            if(count > limit) {
                throw new CodeException(ResultCode.Codes.SINLE_RATE_LIMIT);
            }
        }

    }
}
