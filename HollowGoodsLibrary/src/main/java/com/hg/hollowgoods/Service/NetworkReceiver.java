package com.hg.hollowgoods.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hg.hollowgoods.Service.Time.TimeService;
import com.hg.hollowgoods.Util.LogUtils;

/**
 * 网络连接状态改变监听
 * Created by HG on 2018-05-23.
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            // 获取联网状态的NetworkInfo对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info != null) {
                // 如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI
                            || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        LogUtils.Log("连上");
                        TimeService.start(context);
                    }
                } else {
                    LogUtils.Log("断开");
                }
            } else {
                LogUtils.Log("断开");
            }
        }
    }

}
