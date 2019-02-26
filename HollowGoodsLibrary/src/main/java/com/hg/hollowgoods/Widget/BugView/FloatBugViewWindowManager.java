package com.hg.hollowgoods.Widget.BugView;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * BugView窗口管理器
 */
public class FloatBugViewWindowManager {

    /**
     * 菜单开关View的实例
     */
    private static FloatBugViewMenuSwitch menuSwitch;

    /**
     * 菜单项View的实例
     */
    private static FloatBugViewMenuItem menuItem;

    /**
     * 菜单开关View的参数
     */
    private static LayoutParams menuSwitchParams;

    /**
     * 菜单项View的参数
     */
    private static LayoutParams menuItemParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;

    /**
     * 创建菜单开关。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createMenuSwitch(Context context) {

        WindowManager windowManager = getWindowManager(context);

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if (menuSwitch == null) {
            menuSwitch = new FloatBugViewMenuSwitch(context);

            if (menuSwitchParams == null) {
                menuSwitchParams = new LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    menuSwitchParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    menuSwitchParams.type = LayoutParams.TYPE_PHONE;
                }
                menuSwitchParams.format = PixelFormat.RGBA_8888;
                menuSwitchParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                menuSwitchParams.gravity = Gravity.LEFT | Gravity.TOP;
                menuSwitchParams.width = FloatBugViewMenuSwitch.mViewWidth;
                menuSwitchParams.height = FloatBugViewMenuSwitch.mViewHeight;
                menuSwitchParams.x = screenWidth;
                menuSwitchParams.y = screenHeight / 2;
            }

            menuSwitch.setParams(menuSwitchParams);
            windowManager.addView(menuSwitch, menuSwitchParams);
        }
    }

    /**
     * 将菜单开关从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeMenuSwitch(Context context) {

        if (menuSwitch != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(menuSwitch);
            menuSwitch = null;
        }
    }

    /**
     * 创建菜单项。位置为屏幕正中间。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createMenuItem(Context context) {

        WindowManager windowManager = getWindowManager(context);

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if (menuItem == null) {
            menuItem = new FloatBugViewMenuItem(context);

            if (menuItemParams == null) {
                menuItemParams = new LayoutParams();
                menuItemParams.x = screenWidth / 2 - FloatBugViewMenuItem.mViewWidth / 2;
                menuItemParams.y = screenHeight / 2 - FloatBugViewMenuItem.mViewHeight / 2;
                menuItemParams.type = LayoutParams.TYPE_PHONE;
                menuItemParams.format = PixelFormat.RGBA_8888;
                menuItemParams.gravity = Gravity.LEFT | Gravity.TOP;
                menuItemParams.width = FloatBugViewMenuItem.mViewWidth;
                menuItemParams.height = FloatBugViewMenuItem.mViewHeight;
            }

            windowManager.addView(menuItem, menuItemParams);
        }
    }

    /**
     * 将菜单项从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeMenuItem(Context context) {

        if (menuItem != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(menuItem);
            menuItem = null;
        }
    }

    /**
     * 是否有悬浮窗显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return menuSwitch != null || menuItem != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {

        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {

        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }

        return mActivityManager;
    }

//    /**
//     * 计算已使用内存的百分比，并返回。
//     *
//     * @param context 可传入应用程序上下文。
//     * @return 已使用内存的百分比，以字符串形式返回。
//     */
//    public static String getUsedPercentValue(Context context) {
//        String dir = "/proc/meminfo";
//        try {
//            FileReader fr = new FileReader(dir);
//            BufferedReader br = new BufferedReader(fr, 2048);
//            String memoryLine = br.readLine();
//            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
//            br.close();
//            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
//            long availableSize = getAvailableMemory(context) / 1024;
//            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
//            return percent + "%";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "悬浮窗";
//    }

//    /**
//     * 获取当前可用内存，返回数据以字节为单位。
//     *
//     * @param context 可传入应用程序上下文。
//     * @return 当前可用内存。
//     */
//    private static long getAvailableMemory(Context context) {
//        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//        getActivityManager(context).getMemoryInfo(mi);
//        return mi.availMem;
//    }

}
