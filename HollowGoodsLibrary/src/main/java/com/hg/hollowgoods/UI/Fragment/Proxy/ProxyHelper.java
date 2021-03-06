package com.hg.hollowgoods.UI.Fragment.Proxy;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * 代理助手
 * Created by Hollow Goods on 2019-06-03.
 */
public class ProxyHelper {

    private static ProxyHelper proxyHelper;

    public static ProxyHelper create(BaseActivity baseActivity) {

        if (proxyHelper == null) {
            proxyHelper = new ProxyHelper();
        }

        proxyHelper.baseActivity = baseActivity;

        return proxyHelper;
    }

    private ProxyHelper() {

    }

    private BaseActivity baseActivity;

    /**
     * 请求代理
     * 1. startActivityForResult
     * 2. requestPermissions
     *
     * @param proxyConfig 代理配置
     */
    public void requestProxy(@NotNull ProxyConfig proxyConfig) {

        FragmentManager fm = baseActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ProxyFragment proxyFragment = new ProxyFragment();
        proxyFragment.setProxyConfig(proxyConfig);

        int contentViewId = R.id.view_content;
        View contentView = baseActivity.findViewById(contentViewId);
        if (contentView == null) {
            if (baseActivity.baseUI.rootView.getId() == View.NO_ID) {
                baseActivity.baseUI.rootView.setId(contentViewId);
            } else {
                contentViewId = baseActivity.baseUI.rootView.getId();
            }
        }
        ft.replace(contentViewId, proxyFragment);
        ft.commitAllowingStateLoss();
    }

}
