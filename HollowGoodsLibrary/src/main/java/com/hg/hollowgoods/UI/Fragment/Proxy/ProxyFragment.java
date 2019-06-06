package com.hg.hollowgoods.UI.Fragment.Proxy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.hg.hollowgoods.UI.Base.ProxyBaseFragment;
import com.hg.hollowgoods.Util.LogUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 代理碎片
 * Created by Hollow Goods on 2019-05-31.
 */
public class ProxyFragment extends ProxyBaseFragment {

    private ProxyConfig proxyConfig;

    public void setProxyConfig(@NotNull ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onStartFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onStartFragment();
        }
    }

    private void onStartFragment() {

        if (proxyConfig.getPermissions() != null) {
            if (proxyConfig.getOnProxyRequestPermissionsResult() != null) {
                baseUI.setOnPermissionsListener((isAgreeAll, requestCode, permissions, isAgree) -> {
                    proxyConfig.getOnProxyRequestPermissionsResult().onPermissionsResult(isAgreeAll, requestCode, permissions, isAgree);
                    detachFragment();
                });

                boolean result = baseUI.requestPermission(proxyConfig.getRequestCode(), proxyConfig.getPermissions());
                if (result) {
                    boolean[] agree = new boolean[proxyConfig.getPermissions().length];
                    for (int i = 0; i < agree.length; i++) {
                        agree[i] = true;
                    }

                    proxyConfig.getOnProxyRequestPermissionsResult().onPermissionsResult(true, proxyConfig.getRequestCode(), proxyConfig.getPermissions(), agree);
                    new Handler().postDelayed(this::detachFragment, 100);
                }
            }
        } else if (proxyConfig.getIntent() != null) {
            startActivityForResult(proxyConfig.getIntent(), proxyConfig.getRequestCode());
        } else {
            detachFragment();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (proxyConfig.getOnProxyActivityResult() != null) {
            proxyConfig.getOnProxyActivityResult().onActivityResult(requestCode, resultCode, data);
        }

        detachFragment();
    }

    private void detachFragment() {
        LogUtils.Log("是否绑定", isAdded());
        if (isAdded()) {
            baseUI.getBaseContext().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
            LogUtils.Log("解绑");
        }
    }

}
