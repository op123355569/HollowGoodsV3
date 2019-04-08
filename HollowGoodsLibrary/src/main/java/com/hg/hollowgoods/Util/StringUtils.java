package com.hg.hollowgoods.Util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 字符串判断工具类
 * Created by HG
 */
@SuppressLint("SimpleDateFormat")
public class StringUtils {

    /**
     * 拆分字符串为数组
     *
     * @param str       需要拆分的字符串
     * @param separator 拆分间隔符
     * @return
     */
    public static ArrayList<String> getStringArray(String str, String separator) {

        ArrayList<String> result = new ArrayList<>();
        String temp;
        int index;

        if (str != null) {
            while (str.length() != 0) {
                index = str.indexOf(separator);
                temp = str.substring(0, index == -1 ? str.length() : index);
                result.add(temp);
                str = index == -1 ? "" : str.substring(str.indexOf(separator) + separator.length());
            }
        }

        return result;
    }

    public enum DateFormatMode {

        /**
         * yyyyMMddHHmmssSSS
         */
        Default("yyyyMMddHHmmssSSS"),

        /**
         * yyyy.MM.dd HH:mm:ss:SSS
         */
        POINT_YMDHMSS("yyyy.MM.dd HH:mm:ss:SSS"),
        /**
         * yyyy.MM.dd HH:mm:ss
         */
        POINT_YMDHMS("yyyy.MM.dd HH:mm:ss"),
        /**
         * yyyy.MM.dd HH:mm
         */
        POINT_YMDHM("yyyy.MM.dd HH:mm"),
        /**
         * yyyy.MM.dd HH
         */
        POINT_YMDH("yyyy.MM.dd HH"),
        /**
         * yyyy.MM.dd
         */
        POINT_YMD("yyyy.MM.dd"),
        /**
         * yyyy.MM
         */
        POINT_YM("yyyy.MM"),

        /**
         * yyyy-MM-dd HH:mm:ss:SSS
         */
        LINE_YMDHMSS("yyyy-MM-dd HH:mm:ss:SSS"),
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        LINE_YMDHMS("yyyy-MM-dd HH:mm:ss"),
        /**
         * yyyy-MM-dd HH:mm
         */
        LINE_YMDHM("yyyy-MM-dd HH:mm"),
        /**
         * yyyy-MM-dd HH
         */
        LINE_YMDH("yyyy-MM-dd HH"),
        /**
         * yyyy-MM-dd
         */
        LINE_YMD("yyyy-MM-dd"),
        /**
         * yyyy-MM
         */
        LINE_YM("yyyy-MM"),

        /**
         * yyyy年MM月dd日 HH:mm:ss:SSS
         */
        Chinese_YMDHMSS("yyyy年MM月dd日 HH:mm:ss:SSS"),
        /**
         * yyyy年MM月dd日 HH:mm:ss
         */
        Chinese_YMDHMS("yyyy年MM月dd日 HH:mm:ss"),
        /**
         * yyyy年MM月dd日 HH:mm
         */
        Chinese_YMDHM("yyyy年MM月dd日 HH:mm"),
        /**
         * yyyy年MM月dd日 HH
         */
        Chinese_YMDH("yyyy年MM月dd日 HH"),
        /**
         * yyyy年MM月dd日
         */
        Chinese_YMD("yyyy年MM月dd日"),
        /**
         * yyyy年MM月
         */
        Chinese_YM("yyyy年MM月"),

        /**
         * yyyy
         */
        Time_Y("yyyy"),
        /**
         * MM
         */
        Time_M("MM"),
        /**
         * DD
         */
        Time_D("dd"),
        /**
         * HH:mm:ss:SSS
         */
        Time_HMSS("HH:mm:ss:SSS"),
        /**
         * HH:mm:ss
         */
        Time_HMS("HH:mm:ss"),
        /**
         * HH:mm
         */
        Time_HM("HH:mm"),
        /**
         * HH
         */
        Time_H("HH"),
        /**
         * ss
         */
        Time_S("ss"),
        /**
         * SSS
         */
        Time__S("SSS"),
        ;

        private String format;

        DateFormatMode(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }
    }

    /**
     * 格式化时间<p>
     *
     * @param time           时间戳
     * @param dateFormatMode 分隔符
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateTimeString(Object time, DateFormatMode dateFormatMode) {

        if (time == null || TextUtils.isEmpty(time + "")) {
            return "";
        }

        long longTime;
        try {
            longTime = Long.valueOf(time + "");
        } catch (Exception e) {
            longTime = 0l;
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormatMode.format);

        return sDateFormat.format(longTime);
    }

    public static String getDateTimeString(int year, int month, int date, DateFormatMode dateFormatMode) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, date, 0, 0, 0);

        return getDateTimeString(c.getTimeInMillis(), dateFormatMode);
    }

    public static long getDateLong(String dateStr, DateFormatMode dateFormatMode) {

        long result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatMode.format);

        try {
            result = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            LogUtils.Log(e.getMessage());
        }

        return result;
    }

    public static String getTen(int num) {

        if (num > 9) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    private static final String TIME_FOR_FILENAME = "yyyy MM dd HH mm ss SSS";

    /**
     * 以当前时间作为文件名
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeForFilename() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(TIME_FOR_FILENAME);
        return sDateFormat.format(new Date());
    }


    /**
     * 将文件名转成时间戳
     *
     * @param str
     * @return
     */
    public static long getFilenameFormTime(String str) {

        long result = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FOR_FILENAME);
        Date d;

        try {
            d = sdf.parse(str);
            result = d.getTime();
        } catch (ParseException e) {

        }

        return result;
    }

}
