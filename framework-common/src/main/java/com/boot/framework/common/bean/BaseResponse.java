package com.boot.framework.common.bean;


import com.boot.framework.common.enums.CodeEnum;
import com.boot.framework.common.util.ResultCode;

import java.io.Serializable;

/**
 * @author: lindj
 * @date: 2018/4/12 13:50
 * @description: 封装返回实体,未完待续
 */
public class BaseResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public BaseResponse() {
    }

    /**
     * 返回成功，有返回处理结果
     */
    public BaseResponse(T t) {
        this.data = t;
        this.code = ResultCode.Codes.SUCCESS.getCode();
        this.message = ResultCode.Codes.SUCCESS.getMesssage();
    }

    /**
     * 自定义信息
     *
     * @param code    结果编码
     * @param message 处理结果
     */
    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回异常信息
     *
     * @param exceptionEnum CodeEnum
     * @return
     */
    public BaseResponse(CodeEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMesssage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
