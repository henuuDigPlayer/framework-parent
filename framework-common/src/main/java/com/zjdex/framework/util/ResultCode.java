package com.zjdex.framework.util;


import com.zjdex.framework.enums.CodeEnum;

/**
 * @author: lindj
 * @date: 2018/4/17 13:32
 * @description: 状态码
 */
public class ResultCode {

    public enum Codes implements CodeEnum {
        /**
         * 控制层、service层参数校验异常
         */
        PARRAMS_ERROR(20000, "操作失败"),
        /**
         * url地址异常
         */
        NOT_FIND(404, "接口不存在"),
        /**
         * 正常请求提示
         */
        SUCCESS(10000, "成功"),
        /**
         * 逻辑处理异常
         */
        BUSINESS_ERROR(60000, "处理异常"),
        /**
         * 文件上传下载异常
         */
        FILE_NOT_FIND(70001, "文件不存在"),
        /**
         * 未登录
         */
        NOT_LOGIN(30001, "未登录"),
        /**
         * 内容未找到
         */
        UNKNOW(50000, "内容未找到"),

        /**
         * 重复提交
         */
        SUBMIT_REPEAT(90001, "已经提交请求，不能重复提交"),
        /**
         * 数据过期
         */
        DATATIMEOUT(3002, "数据已过期"),
        /**
         * 未注册
         */
        NOT_REGIST(3003, "未注册"),
        /**
         * 已注册
         */
        REGISTED(3004, "账号已注册"),
        SQL_INJECTION(90002, "请求数据存在安全风险");

        private Integer code;
        private String message;

        Codes(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String getMesssage() {
            return message;
        }

        public void setMesssage(String messsage) {
            this.message = messsage;
        }
    }
}
