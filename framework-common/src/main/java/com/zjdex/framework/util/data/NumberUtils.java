package com.zjdex.framework.util.data;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author: lindj
 * @date: 2018/4/13 10:43
 * @description: 数字校验
 */
public class NumberUtils {

    public static final Integer SCALE = 2;

    /**
     * 为空或者小于等于0 为false，其他 true
     *
     * @param value Integer
     * @return boolean
     */
    public static boolean isValidate(Integer value) {
        return value == null || value <= 0;
    }

    /**
     * 为空或者小于等于0 为false，其他 true
     *
     * @param value Integer
     * @return boolean
     */
    public static boolean isValidate(Long value) {
        return value == null || value <= 0;
    }

    /**
     * 除法运算
     *
     * @param first  BigDecimal
     * @param second BigDecimal
     * @param scale  精度
     * @return BigDecimal
     */
    public static double divide(BigDecimal first, BigDecimal second, int
            scale) {
        BigDecimal b1 = new BigDecimal(first.toString());
        BigDecimal b2 = new BigDecimal(second.toString());
        double value = (b1.doubleValue() / b2.doubleValue()) * 100;
        BigDecimal result = new BigDecimal(value);
        return result.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取定长随机数字符串
     *
     * @param length Integer长度
     * @return String
     */
    public static String getValifyCode(Integer length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        if (length == null) {
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

}
