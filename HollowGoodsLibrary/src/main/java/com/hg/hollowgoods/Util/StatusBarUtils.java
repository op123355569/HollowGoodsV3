package com.hg.hollowgoods.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 状态栏工具类
 * Created by Hollow Goods on 2019-11-20.
 */
public class StatusBarUtils {

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity Activity
     * @return boolean true 设置成功 false 设置失败
     */
    public static boolean statusBarLightMode(Activity activity) {
        return statusBarLightMode(activity, true);
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity   Activity
     * @param fullScreen boolean 是否全屏
     * @return boolean true 设置成功 false 设置失败
     */
    public static boolean statusBarLightMode(Activity activity, boolean fullScreen) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String rom = SystemRomUtils.getSystem();

            switch (rom) {
                case SystemRomUtils.SYS_MIUI:
                    return miuiSetStatusBarLightMode(activity, true, fullScreen);
                case SystemRomUtils.SYS_FLYME:
                    return flymeSetStatusBarLightMode(activity.getWindow(), true);
                case SystemRomUtils.SYS_EMUI:
                default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (fullScreen) {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        } else {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                        return true;
                    }
                    break;
            }
        }

        return false;
    }

    /**
     * 状态栏暗色模式，设置状态栏白色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity Activity
     * @return boolean true 设置成功 false 设置失败
     */
    public static boolean statusBarDarkMode(Activity activity) {
        return statusBarDarkMode(activity, true);
    }

    /**
     * 状态栏暗色模式，设置状态栏白色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity   Activity
     * @param fullScreen boolean 是否全屏
     * @return boolean true 设置成功 false 设置失败
     */
    public static boolean statusBarDarkMode(Activity activity, boolean fullScreen) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String rom = SystemRomUtils.getSystem();

            switch (rom) {
                case SystemRomUtils.SYS_MIUI:
                    return miuiSetStatusBarLightMode(activity, false, fullScreen);
                case SystemRomUtils.SYS_FLYME:
                    return flymeSetStatusBarLightMode(activity.getWindow(), false);
                case SystemRomUtils.SYS_EMUI:
                default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        return true;
                    }
                    break;
            }
        }

        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {

        boolean result = false;

        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);

                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }

                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignored) {

            }
        }

        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @SuppressLint("PrivateApi")
    private static boolean miuiSetStatusBarLightMode(Activity activity, boolean dark, boolean fullScreen) {

        boolean result = false;
        Window window = activity.getWindow();

        if (window != null) {
            Class clazz = window.getClass();

            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                //noinspection unchecked
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);

                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        if (fullScreen) {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        } else {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception ignored) {

            }
        }

        return result;
    }

    /**
     * 获取屏幕虚拟键高度
     *
     * @param context Context
     * @return int
     */
    public static int getVirtualBarHeight(Context context) {

        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager == null) {
            return vh;
        }

        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();

        try {
            @SuppressWarnings("rawtypes") Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method =
                    c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception ignored) {

        }

        return vh;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context Context
     * @return int
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeight(Context context) {

        int height = 0;

        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Field f = c.getField("status_bar_height");
            int id = (int) f.get(null);
            height = context.getResources().getDimensionPixelSize(id);
        } catch (Exception ignored) {

        }

        return height;
    }

}
