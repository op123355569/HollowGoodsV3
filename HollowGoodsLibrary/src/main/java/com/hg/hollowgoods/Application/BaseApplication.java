package com.hg.hollowgoods.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Exception.CrashHandler;
import com.hg.hollowgoods.Service.Time.TimeThread;
import com.hg.hollowgoods.Task.TaskCheckServiceTime;
import com.hg.hollowgoods.Task.TaskInitAppAutoCheckDate;
import com.hg.hollowgoods.Task.TaskInitCrashHandler;
import com.hg.hollowgoods.Task.TaskInitNetworkWatcher;
import com.hg.hollowgoods.Task.TaskInitVoiceUtils;
import com.hg.hollowgoods.Task.TaskInitXUtils;
import com.hg.hollowgoods.Task.TaskReadOfficeFile;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.CacheUtils;
import com.hg.hollowgoods.Util.LaunchStarter.TaskDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 基Application
 * <p>
 * Created by Hollow Goods on 2018-03-22.
 * <p>
 * Update by Hollow Goods on 2019-11-29.
 * <p>
 * For:
 * <p>
 * 1. 优化启动加载项
 */
public abstract class BaseApplication extends Application implements IBaseApplication {

    private static Application instance = null;

    @SuppressWarnings({"unchecked"})
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

    @Override
    public void addActivity(Activity activity) {
        if (!activityAllList.contains(activity)) {
            activityAllList.add(activity);
        }
    }

    @Override
    public void exit(Activity activity) {
        activityAllList.remove(activity);
    }

    @Override
    public void exitWithFinish(Activity activity) {
        activityAllList.remove(activity);
        activity.finish();
    }

    @Override
    public void exitAll() {
        for (Activity activity : activityAllList) {
            activity.finish();
        }
        activityAllList.clear();
    }

    @Override
    public ArrayList<Activity> getAllActivity() {
        return activityAllList;
    }

    @Override
    public void onCreate() {

        instance = initAppContext();
        ApplicationBuilder.setApplication(instance);

        initAppDataBeforeDB();

        TaskDispatcher.init(create());
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();

        dispatcher.addTask(new TaskInitCrashHandler())
                .addTask(new TaskInitXUtils())
                .addTask(new TaskInitNetworkWatcher())
                .addTask(new TaskCheckServiceTime())
                .addTask(new TaskReadOfficeFile())
                .addTask(new TaskInitAppAutoCheckDate())
                .addTask(new TaskInitVoiceUtils())
                .start();

        dispatcher.await();

        super.onCreate();
    }

    @Override
    public BaseActivity getTopActivity() {

        BaseApplication application = BaseApplication.create();
        List<Activity> activities = application.getAllActivity();

        if (activities != null && activities.size() > 0) {
            Activity activity = activities.get(activities.size() - 1);

            if (activity instanceof BaseActivity) {
                return (BaseActivity) activity;
            }
        }

        return null;
    }

    @Override
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

    @Override
    public String getAutoCheckUpdateAppDate() {
        return autoCheckUpdateAppDate;
    }

    @Override
    public void setAutoCheckUpdateAppDate(String autoCheckUpdateAppDate) {
        this.autoCheckUpdateAppDate = autoCheckUpdateAppDate;
        CacheUtils.create().save("AppAutoCheckDate", this.autoCheckUpdateAppDate);
    }

    @Override
    public long getNowTime() {

        if (nowTime == 0L) {
            return System.currentTimeMillis();
        }

        return nowTime;
    }

    @Override
    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    @Override
    public TimeThread getTimeThread() {
        return timeThread;
    }

    @Override
    public void setTimeThread(TimeThread timeThread) {
        this.timeThread = timeThread;
    }

    @Override
    public int getCountFlag() {
        return countFlag;
    }

    @Override
    public void setCountFlag(int countFlag) {
        this.countFlag = countFlag;
    }

    @Override
    public int getTestSystemTime() {
        return testSystemTime;
    }

    @Override
    public void setTestSystemTime(int testSystemTime) {
        this.testSystemTime = testSystemTime;
    }

}
