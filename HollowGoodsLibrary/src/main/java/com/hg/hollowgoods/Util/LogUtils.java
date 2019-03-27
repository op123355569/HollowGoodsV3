package com.hg.hollowgoods.Util;

import android.text.TextUtils;
import android.util.Log;

import com.hg.hollowgoods.Application.BaseApplication;
import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 日志工具
 * Created by Hollow Goods on 2019-03-25.
 */
public class LogUtils {

    private static ArrayList<String> MODULE_PACKAGE = new ArrayList<>();

    public static void addModulePackage(String... modulePackages) {
        if (modulePackages != null && modulePackages.length > 0) {
            MODULE_PACKAGE.addAll(Arrays.asList(modulePackages));
        }
    }

    public static void LogRequest(String url, String msg) {
        BaseLog(getDefaultTag(), getTitle(), url, msg);
    }

    public static void Log(Object... content) {

        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            return;
        }

        if (content == null || content.length == 0) {
            return;
        }

        String[] str = new String[content.length + 1];
        int i = 0;

        str[i++] = getTitle();

        for (Object t : content) {
            if (t == null) {
                str[i] = "<null>";
            } else {
                str[i] = t.toString();
            }

            i++;
        }

        BaseLog(getDefaultTag(), str);
    }

    private static void BaseLog(String tag, String... content) {

        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            return;
        }

        if (TextUtils.isEmpty(tag)) {
            tag = LogUtils.class.getSimpleName();
        }

        // 头部
        Log.e(tag, "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        Log.e(tag, "\n");

        // 正文
        ArrayList<String> contentList;
        int i = 0;

        for (String t : content) {
            contentList = getContentList(t);

            if (contentList != null && contentList.size() > 0) {
                for (String p : contentList) {
                    if (!TextUtils.isEmpty(p)) {
                        Log.e(tag, "│ " + p);
                        Log.e(tag, "\n");
                    }
                }
            } else {
                Log.e(tag, "│ <null>");
                Log.e(tag, "\n");
            }

            if (i < content.length - 1) {
                // 中部分割线
                Log.e(tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
                Log.e(tag, "\n");
            } else {
                // 底部
                Log.e(tag, "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            }

            i++;
        }
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

    private static ArrayList<String> getContentList(String str) {

        ArrayList<String> content = null;

        if (!TextUtils.isEmpty(str)) {
            content = StringUtils.getStringArray(str, "\n");
        }

        return content;
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
                if (!stackClassName.contains(LogUtils.class.getSimpleName())
                        && (stackClassName.contains(BaseApplication.create().getPackageName())
                        || isIncludeModule(stackClassName))
                ) {
                    return "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
                }
            }
        } catch (Exception ignored) {

        }

        return LogUtils.class.getSimpleName();
    }

    private static boolean isIncludeModule(String stackClassName) {

        for (String t : MODULE_PACKAGE) {
            if (stackClassName.contains(t)) {
                return true;
            }
        }

        return false;
    }

}
