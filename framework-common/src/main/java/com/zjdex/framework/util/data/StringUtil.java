package com.zjdex.framework.util.data;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
     * map转objetc
     *
     * @param map       Map<String, Object>
     * @param beanClass
     * @return Object
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * 对象转map
     *
     * @param obj
     * @return Map
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

}
