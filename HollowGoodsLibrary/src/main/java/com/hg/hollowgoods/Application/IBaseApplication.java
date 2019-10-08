package com.hg.hollowgoods.Application;

import android.app.Activity;
import android.app.Application;

import com.hg.hollowgoods.Service.Time.TimeThread;
import com.hg.hollowgoods.UI.Base.BaseActivity;

import java.util.ArrayList;

/**
 * 基Application接口
 * Created by Hollow Goods on 2018-03-22.
 */
public interface IBaseApplication {

    /**
     * 初始化App的上下文
     *
     * @return Application
     */
    Application initAppContext();

    /**
     * 初始化数据在DB加载前
     */
    void initAppDataBeforeDB();

    /**
     * 初始化数据在DB加载后
     */
    void initAppDataAfterDB();

    /**
     * 是否与服务器对时
     *
     * @return boolean 默认false
     */
    default boolean checkServerTime() {
        return false;
    }

    /**
     * 每个Activity都必须加入的list
     *
     * @param activity Activity
     */
    void addActivity(Activity activity);

    /**
     * Activity移出list Activity不会finish
     *
     * @param activity Activity
     */
    void exit(Activity activity);

    /**
     * Activity移出list Activity会finish
     *
     * @param activity Activity
     */
    void exitWithFinish(Activity activity);

    /**
     * 完整地遍历所有Activity并finish
     */
    void exitAll();

    /**
     * 获取所有的Activity
     *
     * @return ArrayList<Activity>
     */
    ArrayList<Activity> getAllActivity();

    /**
     * 获取当前的Activity
     *
     * @return BaseActivity
     */
    BaseActivity getTopActivity();

    /**
     * 设置上报Bug的用户名
     *
     * @param username 用户名
     */
    void setCrashHandlerUsername(String username);

    /**
     * 获取当前时间
     *
     * @return long
     */
    long getNowTime();

    /**
     * 设置当前时间
     *
     * @param nowTime long
     */
    void setNowTime(long nowTime);

    /**
     * 获取自动检查版本更新日期
     *
     * @return String
     */
    String getAutoCheckUpdateAppDate();

    /**
     * 设置自动检查版本更新日期
     *
     * @param autoCheckUpdateAppDate String
     */
    void setAutoCheckUpdateAppDate(String autoCheckUpdateAppDate);

    /**
     * 获取对时线程
     *
     * @return TimeThread
     */
    TimeThread getTimeThread();

    /**
     * 设置对时线程
     *
     * @param timeThread TimeThread
     */
    void setTimeThread(TimeThread timeThread);

    /**
     * 获取计时器标识
     *
     * @return int
     */
    int getCountFlag();

    /**
     * 设置计时器标识
     *
     * @param countFlag int
     */
    void setCountFlag(int countFlag);

    /**
     * 获取服务器校验时间间隔（单位：秒）
     *
     * @return int
     */
    int getTestSystemTime();

    /**
     * 设置服务器校验时间间隔（单位：秒）
     *
     * @param testSystemTime int
     */
    void setTestSystemTime(int testSystemTime);

}
