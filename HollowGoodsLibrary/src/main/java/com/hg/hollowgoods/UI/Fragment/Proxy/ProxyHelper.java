package com.hg.hollowgoods.UI.Fragment.Proxy;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hg.hollowgoods.UI.Base.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Hollow Goods on 2019-06-03.
 */
public class ProxyHelper {

    private static ProxyHelper proxyHelper;

    public static ProxyHelper create(BaseActivity baseActivity) {

        if (proxyHelper == null) {
            proxyHelper = new ProxyHelper();
            proxyHelper.baseActivity = baseActivity;
        }

        return proxyHelper;
    }

    private ProxyHelper() {

    }

    private BaseActivity baseActivity;

    public void requestProxy(@NotNull ProxyConfig proxyConfig) {

        ProxyFragment proxyFragment = new ProxyFragment();
        proxyFragment.setProxyConfig(proxyConfig);

        FragmentManager fm = baseActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, proxyFragment);
        ft.commitAllowingStateLoss();
    }

}
