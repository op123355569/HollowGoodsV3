package com.hg.hollowgoods.Util;

import android.text.TextUtils;
import android.util.Log;

import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.util.ArrayList;

/**
 * 日志工具
 * Created by Hollow Goods on 2019-03-25.
 */
public class LogUtils {

    public static void LogRequest(String url, String msg) {

        String title;
        if (APPUtils.isMainThread()) {
            title = "Thread:Main";
        } else {
            title = "Thread:Child";
        }

        ArrayList<String> content = null;
        if (!TextUtils.isEmpty(msg)) {
            content = StringUtils.getStringArray(msg, "\n");
        }

        Log("Network request", title, content, url);
    }

    public static void Log(Object msg) {
        Log(LogUtils.class.getSimpleName(), msg);
    }

    public static void Log(Class<?> tag, Object msg) {

        String str;

        if (tag != null) {
            str = tag.getSimpleName();
        } else {
            str = LogUtils.class.getSimpleName();
        }

        Log(str, msg);
    }

    public static void Log(String tag, Object msg) {

        String title;
        if (APPUtils.isMainThread()) {
            title = "Thread:Main";
        } else {
            title = "Thread:Child";
        }

        String str;

        if (msg == null) {
            str = "null";
        } else {
            str = msg.toString();
        }

        Log(tag, title, null, str);
    }

    public static void Log(String tag, String title, ArrayList<String> content, String other) {

        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            return;
        }

        if (TextUtils.isEmpty(tag)) {
            tag = LogUtils.class.getSimpleName();
        }

        Log.e(tag, "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        Log.e(tag, "\n");
        if (!TextUtils.isEmpty(title)) {
            Log.e(tag, "│ " + title);
        } else {
            Log.e(tag, "│ ");
        }
        Log.e(tag, "\n");

        Log.e(tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
        Log.e(tag, "\n");
        if (content != null) {
            for (String t : content) {
                if (!TextUtils.isEmpty(t)) {
                    Log.e(tag, "│ " + t);
                    Log.e(tag, "\n");
                }
            }

            Log.e(tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            Log.e(tag, "\n");
        }

        if (!TextUtils.isEmpty(other)) {
            Log.e(tag, "│ " + other);
            Log.e(tag, "\n");
        } else {
            Log.e(tag, "│ ");
        }
        Log.e(tag, "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
    }

}
