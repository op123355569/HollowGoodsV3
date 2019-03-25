package com.hg.hollowgoods.Util;

import android.text.TextUtils;
import android.util.Log;

import com.hg.hollowgoods.Application.BaseApplication;
import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.util.ArrayList;

/**
 * 日志工具
 * Created by Hollow Goods on 2019-03-25.
 */
public class LogUtils {

    public static void LogRequest(String url, String msg) {

        ArrayList<String> content = null;
        if (!TextUtils.isEmpty(msg)) {
            content = StringUtils.getStringArray(msg, "\n");
        }

        Log(getDefaultTag(), getTitle(), content, url);
    }

    public static void Log(Object other) {
        Log(getTitle(), other);
    }

    public static void Log(Object title, Object other) {
        Log(title, null, other);
    }

    public static void Log(Object title, ArrayList<String> content, Object other) {

        String strTitle;

        if (title == null) {
            strTitle = "null";
        } else {
            strTitle = title.toString();
        }

        String strOther;

        if (title == null) {
            strOther = "null";
        } else {
            strOther = other.toString();
        }

        Log(getDefaultTag(), strTitle, content, strOther);
    }

    private static void Log(String tag, String title, ArrayList<String> content, String other) {

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

    private static String getTitle() {

        String title;

        if (APPUtils.isMainThread()) {
            title = "Thread:Main";
        } else {
            title = "Thread:Child";
        }

        return title;
    }

    /**
     * 获取默认TAG
     */
    private static String getDefaultTag() {

        // 运行栈类名
        String stackClassName;
        // 运行栈
        StackTraceElement[] stackTraceElements;

        try {
            // 获取当前运行任务栈信息
            stackTraceElements = Thread.currentThread().getStackTrace();
            // 遍历任务栈信息，获取调用者信息并返回
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                stackClassName = stackTraceElement.getClassName() + "";
                // 仅获取本项目下的非此类调用信息
                if (!stackClassName.contains(LogUtils.class.getSimpleName()) && stackClassName.contains(BaseApplication.create().getPackageName())) {
                    return "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
                }
            }
        } catch (Exception ignored) {

        }

        return LogUtils.class.getSimpleName();
    }

}
