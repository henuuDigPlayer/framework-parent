package com.zjdex.framework.exception;


import com.zjdex.framework.enums.CodeEnum;

/**
 * @author: lindj
 * @date: 2018/4/12 13:35
 * @description: 封装异常类
 */
public class CodeException extends RuntimeException {

    private Integer code;
    private String message;

    /**
     * 构造方法
     *
     * @param exceptionEnum CodeEnum 自定义枚举
     */
    public CodeException(CodeEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMesssage();
    }

    /**
     * 构造方法
     * @param exceptionEnum CodeEnum 自定义枚举
     * @param message String 异常信息
     */
    public CodeException(CodeEnum exceptionEnum, String message) {
        this.code = exceptionEnum.getCode();
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param code    integer
     * @param message String
     */
    public CodeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeException(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
