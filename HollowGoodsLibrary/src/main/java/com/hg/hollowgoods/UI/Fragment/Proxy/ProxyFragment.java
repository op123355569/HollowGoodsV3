package com.hg.hollowgoods.UI.Fragment.Proxy;

import android.content.Context;
import android.content.Intent;

import com.hg.hollowgoods.UI.Base.ProxyBaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 代理碎片
 * Created by Hollow Goods on 2019-05-31.
 */
public class ProxyFragment extends ProxyBaseFragment {

    private ProxyConfig proxyConfig;

    public void setProxyConfig(@NotNull ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

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
                    detachFragment();
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
        if (isAdded()) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        }
    }

}
