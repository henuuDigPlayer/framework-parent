package com.zjdex.framework.holder;

import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.StringUtil;
import com.zjdex.framework.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(RequestHolder.class.getName());


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
     * 获取token
     *
     * @return String
     */
    public static String getToken() {
        return getHeader("token");
    }

    /**
     * 获取token
     *
     * @return String
     */
    public static String getTokenWithException() {
        String token = getToken();
        if (!StringUtil.isEmpty(token)) {
            try {
                TokenUtil.parseToken(token);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CodeException(ResultCode.Codes.NOT_LOGIN);
            }
        }
        return token;
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
