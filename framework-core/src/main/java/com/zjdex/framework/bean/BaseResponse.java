package com.zjdex.framework.bean;


import com.zjdex.framework.enums.CodeEnum;
import com.zjdex.framework.util.ResultCode;

/**
 * @author: lindj
 * @date: 2018/4/12 13:50
 * @description: 封装返回实体,未完待续
 */
public class BaseResponse {

    private Integer code;
    private String message;
    private Object data;

    public BaseResponse() {
    }

    /**
     * 返回成功，有返回处理结果
     */
    public BaseResponse(Object object) {
        this.data = object;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
