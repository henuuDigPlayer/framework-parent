package com.zjdex.framework.holder;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lindj
 * @create 2018/9/25
 * @desc 获取HttpServletRequest
 **/
public class RequestHolder {

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request;
    }


    /**
     * 获取请求头信息
     *
     * @param key String
     * @return String
     */
    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

}
