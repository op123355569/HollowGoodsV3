package com.hg.hollowgoods.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Exception.CrashHandler;
import com.hg.hollowgoods.Service.Time.TimeService;
import com.hg.hollowgoods.Service.Time.TimeThread;
import com.hg.hollowgoods.Util.XUtils.XUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;

/**
 * 基Application
 * Created by HG on 2018-03-22.
 */
public abstract class BaseApplication extends Application implements IBaseApplication {

    private static Application instance = null;

    public static <T extends Application> T create() {
        if (instance == null) {
            instance = new ExampleApplication();
        }
        return (T) instance;
    }

    /**
     * activity堆
     */
    private ArrayList<Activity> activityAllList = new ArrayList<>();
    /**
     * 今天是否自动检查过APP更新
     */
    private boolean isAutoCheckUpdateApp = false;
    /**
     * 最近一次自动检查APP更新的时间
     */
    private String autoCheckUpdateAppDate = "";
    /**
     * 当前时间戳
     */
    private long nowTime = 0L;
    /**
     * 计时器
     */
    private TimeThread timeThread;
    /**
     * 计时器标识
     */
    private int countFlag = 0;
    /**
     * 服务器校验时间间隔（单位：秒）
     */
    private int testSystemTime = 300;

    /**
     * 每个Activity都必须加入的list
     *
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        if (!activityAllList.contains(activity)) {
            activityAllList.add(activity);
        }
    }

    /**
     * 退出
     *
     * @param activity Activity
     */
    public void exit(Activity activity) {
        activityAllList.remove(activity);
    }

    /**
     * 退出
     *
     * @param activity Activity
     */
    public void exitWithFinish(Activity activity) {
        activityAllList.remove(activity);
        activity.finish();
    }

    /**
     * 完整地遍历所有Activity并finish
     */
    public void exitAll() {
        for (Activity activity : activityAllList) {
            activity.finish();
        }
        activityAllList.clear();
    }

    public ArrayList<Activity> getAllActivity() {
        return activityAllList;
    }

    @Override
    public void onCreate() {

        instance = initAppContext();
        initCrashHandler();
        initAppDataBeforeDB();
        initXUtils();
        initAppDataAfterDB();
        if (HGSystemConfig.IS_NEED_CHECK_SERVER_TIME) {
            TimeService.start(create());
        }
        if (HGSystemConfig.IS_NEED_READ_OFFICE_FILE) {
            initFileView();
        }

        super.onCreate();
    }

    /**
     * 初始化文件查看控件
     */
    private void initFileView() {
        QbSdk.initX5Environment(create(), null);
    }

    /**
     * 初始化XUtils
     */
    private void initXUtils() {
        XUtils.init(create());
    }

    /**
     * 初始化异常捕捉类
     */
    private void initCrashHandler() {
        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            CrashHandler.getInstance().init(create());
        }
    }

    /**
     * 设置上报Bug的用户名
     *
     * @param username 用户名
     */
    public void setCrashHandlerUsername(String username) {
        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            CrashHandler.getInstance().setUsername(username);
        }
    }

    /**
     * 防止6K方法爆炸
     *
     * @param base 上下文
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(create());
    }

    public boolean isAutoCheckUpdateApp() {
        return isAutoCheckUpdateApp;
    }

    public void setAutoCheckUpdateApp(boolean autoCheckUpdateApp) {
        isAutoCheckUpdateApp = autoCheckUpdateApp;
    }

    public String getAutoCheckUpdateAppDate() {
        return autoCheckUpdateAppDate;
    }

    public void setAutoCheckUpdateAppDate(String autoCheckUpdateAppDate) {
        this.autoCheckUpdateAppDate = autoCheckUpdateAppDate;
    }

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public TimeThread getTimeThread() {
        return timeThread;
    }

    public void setTimeThread(TimeThread timeThread) {
        this.timeThread = timeThread;
    }

    public int getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(int countFlag) {
        this.countFlag = countFlag;
    }

    public void setTestSystemTime(int testSystemTime) {
        this.testSystemTime = testSystemTime;
    }

    public int getTestSystemTime() {
        return testSystemTime;
    }

}
