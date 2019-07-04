package com.zjdex.framework.util.constant;

/**
 * @author: lindj
 * @date: 2018/6/5 13:39
 * @description: 常量
 */
public interface ConstantUtil {

    /**
     * 缓存验证码前缀
     */
    String VALIFY_CODE = "valifyCode";


    /**
     * 上传文件 content-type
     */
    String FILE_CONTENT_TYPE = "multipart/form-data";


    String METHOD_GET = "GET";

    String METHOD_POST = "POST";

    String LOGIN_SALT = "storyweb-bp";
    //request请求头属性
    String REQUEST_AUTH_HEADER="Authorization";

    //JWT-account
    String ACCOUNT = "account";

    //Shiro redis 前缀
    String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";

    //redis-key-前缀-shiro:refresh_token
    String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    //JWT-currentTimeMillis
    String CURRENT_TIME_MILLIS = "currentTimeMillis";


}
