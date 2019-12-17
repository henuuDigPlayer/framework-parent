package com.boot.framework.constant;

/**
 * @author: lindj
 * @date: 2018/11/23
 * @description:
 */
public interface RedisConstant {
    String LOCK_MSG = "OK";

    Long UNLOCK_MSG = 1L;

    String SET_IF_NOT_EXIST = "NX";
    String SET_WITH_EXPIRE_TIME = "PX";
}
