package com.boot.framework.util.data;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: lindj
 * @date: 2018/4/18 11:05
 * @description: JSON操作类
 */
public class JsonUtil {

    /**
     * 对象转JSON
     *
     * @param object Object
     * @return String
     */
    public static String objectToJson(Object object) {
        return object != null ? JSON.toJSONString(object) : null;
    }

    /**
     * json 转对象
     *
     * @param json   json字符串
     * @param tClass 具体
     * @param <T>    tClass
     * @return T
     */
    public static <T> T parseToObject(String json, Class<T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    /**
     * json字符串转 jsonObject
     *
     * @param json json字符串 String
     * @return JSONObject
     */
    public static JSONObject parseToJSONObject(String json) {
        return JSON.parseObject(json);
    }
}
