package com.hg.hollowgoods.Service.Time;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Util.XUtils.GetHttpDataListener;
import com.hg.hollowgoods.Util.XUtils.XUtils2;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

/**
 * 时间服务
 * <p>
 * Created by Hollow Goods on 2018-11-07.
 */
public class TimeService extends Service {

    private IBaseApplication baseApplication;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        baseApplication = ApplicationBuilder.create();
        if (baseApplication.getTimeThread() == null) {
            baseApplication.setTimeThread(new TimeThread(getApplicationContext()));
            baseApplication.getTimeThread().start();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkServerTime();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 校验服务器时间
     */
    public void checkServerTime() {
        if (!baseApplication.checkServerTime()) {
            RequestParams params = new RequestParams("http://api.m.taobao.com/rest/api3.do");
            params.addParameter("api", "mtop.common.getTimestamp");

            new XUtils2.BuilderGetHttpData().setGetHttpDataListener(new GetHttpDataListener() {
                @Override
                public void onGetSuccess(String result) {

                    Time time = new Gson().fromJson(result, Time.class);

                    if (time != null
                            && time.data != null
                            && !TextUtils.isEmpty(time.data.t)
                    ) {
                        baseApplication.setNowTime(Long.valueOf(time.data.t));
                    } else {
                        if (baseApplication.getNowTime() == 0l) {
                            baseApplication.setNowTime(System.currentTimeMillis());
                        }
                    }
                }

                @Override
                public void onGetError(Throwable ex) {
                    if (baseApplication.getNowTime() == 0l) {
                        baseApplication.setNowTime(System.currentTimeMillis());
                    }
                }

                @Override
                public void onGetLoading(long total, long current) {

                }

                @Override
                public void onGetFinish() {

                }

                @Override
                public void onGetCancel(Callback.CancelledException cex) {

                }
            }).getHttpData(HttpMethod.GET, params);
        }
    }

    private class Time {

        private String api;
        private String v;
        private ArrayList<String> ret;
        private Result data;

        private class Result {
            private String t;
        }
    }

    public static void start(Context context) {
        try {
            Intent intent = new Intent(context, TimeService.class);
            context.startService(intent);
        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> start(context), 3000);
        }
    }

}
