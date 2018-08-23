package com.zjdex.framework.util;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author: lindj
 * @date: 2018/4/17 11:20
 * @description: 数据校验
 */
public class StringUtil {

    /**
     * 容器校验
     *
     * @param collection Collection
     * @return boolean
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 字符串校验
     *
     * @param charSequence CharSequence
     * @return boolean
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return StringUtils.isEmpty(charSequence);
    }

    /**
     * 数组校验
     *
     * @param objects object数组
     * @return boolean
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length <= 0;
    }

    /**
     * 数组转字符串
     *
     * @param objects Object对象数组
     * @return String
     */
    public static String getString(Object[] objects) {
        StringBuilder value = new StringBuilder();
        if (!isEmpty(objects)) {
            for (Object o : objects) {
                value.append(JsonUtil.objectToJson(o)).append(",");
            }
        }
        return value.toString();
    }
}
