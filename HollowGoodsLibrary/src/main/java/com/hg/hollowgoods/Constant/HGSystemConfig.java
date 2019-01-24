package com.hg.hollowgoods.Constant;

import android.content.Context;
import android.graphics.Color;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.APPUtils;
import com.hg.hollowgoods.Util.FileUtils;

import java.util.Locale;

import kotlin.jvm.JvmStatic;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2019年01月22日
 */
public class HGSystemConfig {

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context, String... rootDirectory) {
        APP_NAME = context.getString(R.string.app_name);
        APP_NAME_EN = context.getString(R.string.app_name_en);
        APP_BASE_PATH = FileUtils.getSDCardPath()
                + ((rootDirectory != null && rootDirectory.length > 0) ? rootDirectory[0] : "/HollowGoods/")
                + APP_NAME_EN
                + "/";
        DATABASE_NAME = APP_NAME_EN.toLowerCase(Locale.getDefault());

        IS_DEBUG_MODEL = APPUtils.isDebug(context);
    }

    /**** APP模式切换 ****/
    /**
     * 是否是开发模式 true:debug模式 ;false:release模式
     */
    public static boolean IS_DEBUG_MODEL = true;
    /**
     * 是否是沉浸模式 true:沉浸模式;false:非沉浸模式
     */
    public static boolean IMMERSE_MODE = true;

    /**** 应用名称 ****/
    /**
     * 应用名称
     */
    public static String APP_NAME = "MyApplication";
    /**
     * 应用英文名称
     */
    public static String APP_NAME_EN = "MyApplication";

    /**** APP缓存目录 ****/
    /**
     * APP根目录
     */
    public static String APP_BASE_PATH = FileUtils.getSDCardPath() + "/" + APP_NAME_EN + "/";

    /**
     * 图片缓存目录
     */
    @JvmStatic
    public static String getPhotoCachePath() {
        return APP_BASE_PATH + "/ImageCache/";
    }

    /**
     * 视频缓存目录
     */
    public static String getVideoCachePath() {
        return APP_BASE_PATH + "/VideoCache/";
    }

    /**
     * 数据缓存目录
     */
    public static String getDataCachePath() {
        return APP_BASE_PATH + "/DataCache/";
    }

    /**
     * 文件下载目录
     */
    public static String getDownloadFilePath() {
        return APP_BASE_PATH + "/Download/";
    }

    /**
     * BUG缓存目录
     */
    public static String getBugPath() {
        return APP_BASE_PATH + "/BUG/";
    }

    /**
     * 搜索历史记录目录
     */
    public static String getSearchHistoryPath() {
        return APP_BASE_PATH + "/SearchHistory/";
    }

    /**** 网络请求 ****/
    /**
     * xUtils超时重连时间数 单位：ms
     */
    public static int TIME_OUT = 60 * 1000;
    /**
     * xUtils Get请求缓存时间
     */
    public static int CACHE_TIME = 0 * 1000;

    /**** 本地数据库 ****/
    /**
     * 当前数据库DB的版本 初始是2
     */
    public static int DB_VERSION = 2;
    /**
     * 数据库名称
     */
    public static String DATABASE_NAME = "data_base";

    /**** 其他配置 ****/
    /**
     * Activity切换动画
     * 00 01 淡入淡出效果
     * 10 11 放大淡出效果
     * 20 21 转动淡出效果1
     * 30 31 转动淡出效果2
     * 40 41 左上角展开淡出效果
     * 50 51 压缩变小淡出效果
     * 60 61 右往左推出效果
     * 70 71 下往上推出效果
     * 80 81 左右交错效果
     * 90 91 放大淡出效果
     * 100 101 缩小效果
     * 110 111 上下交错效果
     */
    public static int[][] ACTIVITY_ANIMATION = {//
            {R.anim.fade_in, R.anim.fade_out}, // 0
            {R.anim.scale_in, R.anim.alpha_out}, // 1
            {R.anim.scale_rotate_in, R.anim.alpha_out}, // 2
            {R.anim.scale_translate_rotate, R.anim.alpha_out}, // 3
            {R.anim.scale_translate, R.anim.alpha_out}, // 4
            {R.anim.hyperspace_in, R.anim.hyperspace_out}, // 5
            {R.anim.push_left_in, R.anim.push_left_out}, // 6
            {R.anim.push_up_in, R.anim.push_up_out}, // 7
            {R.anim.slide_left, R.anim.slide_right}, // 8
            {R.anim.wave_scale, R.anim.alpha_out}, // 9
            {R.anim.zoom_enter, R.anim.zoom_exit}, // 10
            {R.anim.slide_up_in, R.anim.slide_down_out} // 11
    };

    /**
     * start_activity基础动画序号
     */
    public static int BASE_ANIM_START = 6;
    /**
     * 当前使用界面切换效果in
     */
    public static int NOW_ACTIVITY_IN = ACTIVITY_ANIMATION[BASE_ANIM_START][0];
    /**
     * 当前使用界面切换效果out
     */
    public static int NOW_ACTIVITY_OUT = ACTIVITY_ANIMATION[BASE_ANIM_START][1];

    /**
     * finish_activity基础动画序号
     */
    public static int BASE_ANIM_FINISH = 1;
    /**
     * 当前使用界面切换效果in
     */
    // public static final int NOW_ACTIVITY_FINISH_IN = ACTIVITY_ANIMATION[BASE_ANIM_FINISH][0];
    public static int NOW_ACTIVITY_FINISH_IN = 0;
    /**
     * 当前使用界面切换效果out
     */
    public static int NOW_ACTIVITY_FINISH_OUT = ACTIVITY_ANIMATION[BASE_ANIM_FINISH][1];

    /**
     * 下拉刷新旋转颜色
     */
    public static int[] REFRESH_COLORS = {
            Color.parseColor("#DD191D"),
            Color.parseColor("#FFCA28"),
            Color.parseColor("#039BE5"),
            Color.parseColor("#0A8F08"),
            Color.parseColor("#F57C00"),
    };

    /**
     * 波纹跳转颜色
     */
    public static int ACTIVITY_CHANGE_RES = R.color.colorAccent;

    /**
     * 启动界面自动跳转时间ms
     */
    public static long ACTIVITY_INDEX_JUMP_DELAY = 9999 * 1000L;

    /**
     * 时间ms内连续按两下返回键退出APP
     */
    public static long PRESS_AGAIN_TO_EXIT_TIME = 2 * 1000L;

    /**
     * 两次点击控件的最小间隔时间
     */
    public static long DOUBLE_CLICK_VIEW_TIME = 500L;

    /**
     * 图片选择器、图片预览器 是否需要加载gif图
     */
    public static boolean IS_PHOTO_PICKER_NEED_GIF = true;

    /**
     * NFC两次扫描的间隔时间
     */
    public static long NFC_TWICE_SCAN_TIME = 2 * 1000L;

}
