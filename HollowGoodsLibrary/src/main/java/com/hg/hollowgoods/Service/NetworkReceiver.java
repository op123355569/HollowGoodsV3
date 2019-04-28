package com.hg.hollowgoods.Service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hg.hollowgoods.Application.BaseApplication;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Widget.HGStatusLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 网络连接状态改变监听
 * Created by HG on 2018-05-23.
 */
@Deprecated
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            // 获取联网状态的NetworkInfo对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;

            if (connectivityManager != null) {
                info = connectivityManager.getActiveNetworkInfo();
            }

            if (info != null) {
                // 如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI
                            || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        LogUtils.Log("连上");
                        sendMessage(HGEventActionCode.NETWORK_STATUS_LINK);
                        networkLink();
                    }
                } else {
                    LogUtils.Log("断开");
                    sendMessage(HGEventActionCode.NETWORK_STATUS_BREAK);
                    networkBreak();
                }
            } else {
                LogUtils.Log("断开");
                sendMessage(HGEventActionCode.NETWORK_STATUS_BREAK);
                networkBreak();
            }
        }
    }

    private void networkBreak() {

        BaseActivity baseActivity = getActivity();

        if (baseActivity != null) {
            baseActivity.baseUI.setStatus(HGStatusLayout.Status.NetworkBreak);
        }
    }

    private void networkLink() {

        BaseActivity baseActivity = getActivity();

        if (baseActivity != null) {
            if (baseActivity.baseUI.getStatusLayout() != null) {
                baseActivity.baseUI.getStatusLayout().networkLink();
            }
        }
    }

    private BaseActivity getActivity() {

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

    private void sendMessage(int code) {
        Event event = new Event(code);
        EventBus.getDefault().post(event);
    }

}
