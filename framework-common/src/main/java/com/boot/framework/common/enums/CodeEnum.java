package com.boot.framework.common.enums;

/**
 * @author: lindj
 * @date: 2018/4/17 14:06
 * @description: 数据库转换enum
 */
public interface CodeEnum {
    /**
     * enums、db 转换编码
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * enums、db 转换
     *
     * @return String
     */
    String getMesssage();
}
