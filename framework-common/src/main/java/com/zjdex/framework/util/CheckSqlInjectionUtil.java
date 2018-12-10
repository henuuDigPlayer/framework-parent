package com.zjdex.framework.util;

import java.util.regex.Pattern;

/**
 * @author: lindj
 * @date: 2018/12/3
 * @description:
 */
public class CheckSqlInjectionUtil {
    private static final String SQL_REG = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|"
            + "ascii|declare|exec|count|master|into|drop|execute)\\b)";

    private static Pattern pattern = Pattern.compile(SQL_REG, Pattern.CASE_INSENSITIVE);

    /**
     * 检查SQL注入
     * @param str
     */
    public static boolean validate(String str) {
        if (pattern.matcher(str).find()) {
            return false;
        }
        return true;
    }
    /**
     * 检查SQL注入
     * @param strs
     */
    public static boolean validate(String[] strs) {
        for (String str : strs) {
            if (pattern.matcher(str).find()) {
                return false;
            }
        }
        return true;
    }
}
