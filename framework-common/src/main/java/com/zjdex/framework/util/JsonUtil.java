package com.zjdex.framework.util;


import com.alibaba.fastjson.JSON;

/**
 * @author: lindj
 * @date: 2018/4/18 11:05
 * @description: JSON操作类
 */
public class JsonUtil {

    /**
     * 对象转JSON
     * @param object Object
     * @return String
     */
    public static String objectToJson(Object object) {
        return object != null ? JSON.toJSONString(object) : null;
    }
}
