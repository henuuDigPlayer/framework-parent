package com.boot.framework.util;


import com.boot.framework.bean.BaseResponse;
import com.boot.framework.exception.CodeException;
import com.boot.framework.enums.CodeEnum;

/**
 * @author: lindj
 * @date: 2018/4/12 14:15
 * @description: 返回实体工具类
 */
public class ResponseUtil {
    /**
     * 成功，参数为具体数据
     *
     * @param object Object
     * @return BaseResponse
     */
    public static BaseResponse success(Object object) {
        return new BaseResponse(object);
    }

    /**
     * 成功，无参数
     *
     * @return
     */
    public static BaseResponse success() {
        return success(null);
    }

    /**
     * 自定义返回信息
     *
     * @param code    状态码
     * @param message 处理结果
     * @return BaseResponse
     */
    public static BaseResponse error(Integer code, String message) {
        return new BaseResponse(code, message);
    }

    /**
     * 失败，自定义错误信息
     *
     * @param codeEnum 结果编码
     * @return BaseResponse
     */
    public static BaseResponse error(CodeEnum codeEnum) {
        return new BaseResponse(codeEnum);
    }

    /**
     * 失败，参数为自定义异常类
     *
     * @param codeException 自定义异常
     * @return BaseResponse
     */
    public static BaseResponse error(CodeException codeException) {
        return error(codeException.getCode(), codeException.getMessage());
    }
}
