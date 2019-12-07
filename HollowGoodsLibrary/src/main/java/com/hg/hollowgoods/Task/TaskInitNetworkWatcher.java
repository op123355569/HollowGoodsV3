package com.hg.hollowgoods.Task;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Widget.HGStatusLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * 初始化网络监察者任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskInitNetworkWatcher extends Task {

    @Override
    public void run() {

        Application application = ApplicationBuilder.create();
        ConnectivityManager manager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            manager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    // 网络不可用
                    LogUtils.Log("断网");
                    sendMessage(HGEventActionCode.NETWORK_STATUS_BREAK);
                    networkBreak();
                }

                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    // 网络可用
                    LogUtils.Log("联网");
                    sendMessage(HGEventActionCode.NETWORK_STATUS_LINK);
                    networkLink();
                }
            });
        }
    }

    /**
     * 发送网络状态变化的Event消息
     *
     * @param code code
     */
    private void sendMessage(int code) {
        Event event = new Event(code);
        EventBus.getDefault().post(event);
    }

    /**
     * 设置断网界面布局
     */
    private void networkBreak() {

        BaseActivity baseActivity = ApplicationBuilder.create().getTopActivity();

        if (baseActivity != null) {
            baseActivity.runOnUiThread(() -> baseActivity.baseUI.setStatus(HGStatusLayout.Status.NetworkBreak));
        }
    }

    /**
     * 设置联网界面布局
     */
    private void networkLink() {

        BaseActivity baseActivity = ApplicationBuilder.create().getTopActivity();

        if (baseActivity != null) {
            if (baseActivity.baseUI.getStatusLayout() != null) {
                baseActivity.runOnUiThread(() -> baseActivity.baseUI.getStatusLayout().networkLink());
            }
        }
    }

}
