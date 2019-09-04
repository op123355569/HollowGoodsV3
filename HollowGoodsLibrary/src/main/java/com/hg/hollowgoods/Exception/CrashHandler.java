package com.hg.hollowgoods.Exception;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.APPUtils;
import com.hg.hollowgoods.Util.CacheUtils;
import com.hg.hollowgoods.Util.DensityUtils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 全局异常处理类 <br>
 * 线程未捕获异常控制器 UncaughtException处理类, <br>
 * 当程序发生Uncaught异常的时候,由该类来接管程序, <br>
 * 并记录发送错误报告.
 */
@SuppressLint("RtlHardcoded")
public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * CrashHandler实例
     */
    private static CrashHandler instance;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {

        if (instance == null)
            instance = new CrashHandler();

        return instance;
    }

    /**
     * 初始化,<br>
     * 注册Context对象, 获取系统默认的UncaughtException处理器, <br>
     * 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx 上下文
     */
    public void init(Context ctx) {

        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {

            }

            // 全局推出
            IBaseApplication baseApplication = ApplicationBuilder.create();
            baseApplication.exitAll();
            // 调用此方法整个Activity生命周期停止运行
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等<br>
     * 操作均在此完成. 可以根据自己的情况来自<br>
     * 定义异常处理逻辑
     *
     * @param ex Exception异常
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {

        if (ex == null) {
            return true;
        }

        ExceptionLog exceptionLog = new ExceptionLog();

        if (ex.getStackTrace() != null) {
            for (StackTraceElement t : ex.getStackTrace()) {
                exceptionLog.getErrorList().add(t.toString());
            }
        }

        if (ex.getLocalizedMessage() != null) {
            exceptionLog.setErrorMessage(ex.getLocalizedMessage());
        }

        if (ex.getCause() != null) {
            exceptionLog.setErrorReason(ex.getCause().toString());
        }

        exceptionLog.setErrorAll(ex.toString());

        // 收集设备信息
        StringBuilder tagString = new StringBuilder();
        try {
            exceptionLog.setAppVersionName(APPUtils.getVersionName(mContext));
            exceptionLog.setAppVersionCode(APPUtils.getVersionCode(mContext));
        } catch (Exception e) {
            exceptionLog.setAppVersionName("");
            exceptionLog.setAppVersionCode(0);
        }

        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                exceptionLog.getDeviceList().add(field.getName() + ":" + field.get(null));
            } catch (Exception e) {

            }
        }

        // 将错误信息保存到本地
        saveCrashInfoToSDCard(exceptionLog);
        // 打印log信息
        Log.e(CrashHandler.class.getSimpleName(), new Gson().toJson(exceptionLog));

        // 显示提示对话框
        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                showTips();
                Looper.loop();
            }
        }.start();

        return true;
    }

    /**
     * 提示用户程序出错
     */
    public void showTips() {

        Toast toast = new Toast(mContext);
        TextView textView = new TextView(mContext);
        textView.setText(R.string.sorry_app_will_exit);
        textView.setTextSize(16);
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.toast_bg));
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        int padding = DensityUtils.dp2px(mContext, 16f);
        textView.setPadding(padding, padding, padding, padding);
        toast.setView(textView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private final String name = "Exception.txt";

    /**
     * 保存错误信息到本地
     *
     * @param exceptionLog ExceptionLog
     */
    private void saveCrashInfoToSDCard(ExceptionLog exceptionLog) {

        exceptionLog.setUsername(getUsername());
        exceptionLog.setAppName(HGSystemConfig.APP_NAME);
        Activity activity = null;
        IBaseApplication baseApplication = ApplicationBuilder.create();
        if (baseApplication.getAllActivity() != null && baseApplication.getAllActivity().size() > 0) {
            activity = baseApplication.getAllActivity().get(baseApplication.getAllActivity().size() - 1);
        }
        exceptionLog.setActivityName(activity == null ? "未知" : activity.getClass().getSimpleName());
        exceptionLog.setTime(System.currentTimeMillis());
        exceptionLog.setUploadStatus(false);

        ArrayList<ExceptionLog> logs = CacheUtils.create().load(name, new TypeToken<ArrayList<ExceptionLog>>() {
        }.getType());

        if (logs == null) {
            logs = new ArrayList<>();
        }

        logs.add(0, exceptionLog);
        CacheUtils.create().save(name, logs);
    }

    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
