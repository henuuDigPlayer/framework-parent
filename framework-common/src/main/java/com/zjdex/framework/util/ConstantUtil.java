package com.zjdex.framework.util;

/**
 * @author: lindj
 * @date: 2018/6/5 13:39
 * @description: 常量
 */
public interface ConstantUtil {

    /**
     * token过期时间 30分钟
     */
    long TIMEOUT = 1800;
    /**
     * 请求过期时间 10秒
     */
    long REQUEST_TIMEOUT = 10;

    /**
     * 缓存验证码前缀
     */
    String VALIFY_CODE = "valifyCode";

    /**
     * 缓存短信计时器前缀
     */
    String MESSAGE_LIMIT = "messageLimit";

    /**
     * token前缀
     */
    String TOKEN = "token";

}
