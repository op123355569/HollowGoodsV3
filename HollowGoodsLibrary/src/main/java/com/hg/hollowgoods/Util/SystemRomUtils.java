package com.hg.hollowgoods.Util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;

import com.hg.hollowgoods.Application.ApplicationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 系统Rom工具类
 * Created by Hollow Goods on 2019-11-20.
 */
public class SystemRomUtils {

    /**** 华为 ****/
    public static final String SYS_EMUI = "sys_emui";
    /**** 小米 ****/
    public static final String SYS_MIUI = "sys_miui";
    /**** 魅族 ****/
    public static final String SYS_FLYME = "sys_flyme";
    /**** 其他 ****/
    public static final String SYS_OTHER = "sys_other";

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static String getSystem() {

        String sysType = (String) SharedPreferencesUtils.get(ApplicationBuilder.create(), "sysType", "");

        if (TextUtils.isEmpty(sysType)) {
            try {
                sysType = SYS_OTHER;
                Properties prop = new Properties();
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                        || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                        || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                    sysType = SYS_MIUI;//小米
                } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                        || prop.getProperty(KEY_EMUI_VERSION, null) != null
                        || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                    sysType = SYS_EMUI;//华为
                } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                    sysType = SYS_FLYME;//魅族
                }

                SharedPreferencesUtils.put(ApplicationBuilder.create(), "sysType", sysType);
            } catch (IOException e) {
                return "";
            }
        }

        return sysType;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    @SuppressLint("PrivateApi")
    private static String getSystemProperty(String key, String defaultValue) {

        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception ignored) {

        }

        return defaultValue;
    }

}
