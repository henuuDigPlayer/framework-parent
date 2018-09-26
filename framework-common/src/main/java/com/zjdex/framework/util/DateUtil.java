package com.zjdex.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: lindj
 * @date: 2018/5/4 16:30
 * @description: 日期操作类
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD_ZERO = "yyyy-MM-dd 00:00:00";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 日期转字符串
     *
     * @param value  Date
     * @param format 格式
     * @return String
     */
    public static String getDateString(Date value, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转字符串
     *
     * @param value  Date
     * @param format 格式
     * @return String
     */
    public static String getDateString(String value, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = getDate(value, format);
        return getDateString(date, format);
    }

    /**
     * 日期转字符串
     *
     * @param value  Date
     * @param format 格式
     * @return String
     */
    public static Date getDate(Date value, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(getDateString(value, format));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转日期
     *
     * @param value  String
     * @param format 格式
     * @return String
     */
    public static Date getDate(String value, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取间隔日期
     *
     * @param time   Date 基点日期
     * @param offset Integer 间隔时间
     * @return Date
     */
    public static Date getDateOffset(Date time, Integer offset) {
        if(offset == null){
            return time;
        }
        Long value = time.getTime() + 24 * 60 * 60 * 1000L * offset;
        return new Date(value);
    }
}
