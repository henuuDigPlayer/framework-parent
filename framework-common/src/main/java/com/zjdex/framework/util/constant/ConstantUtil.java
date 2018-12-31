package com.zjdex.framework.util.constant;

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

    long CONNECTION_TIMEOUT = 10 * 1000L;

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

    /**
     * 上传文件 content-type
     */
    String FILE_CONTENT_TYPE = "multipart/form-data";


    String METHOD_GET = "GET";

    String METHOD_POST = "POST";

}
