package com.boot.framework.common.bean;

import com.boot.framework.common.util.data.JsonUtil;

import java.io.Serializable;

/**
 * @author: lindj
 * @date: 2018/6/15 17:20
 * @description:
 */
public class Bean implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
