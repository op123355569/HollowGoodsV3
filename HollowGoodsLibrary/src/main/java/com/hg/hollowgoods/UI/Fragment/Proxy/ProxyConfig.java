package com.hg.hollowgoods.UI.Fragment.Proxy;

import android.content.Intent;

import com.hg.hollowgoods.UI.Base.OnPermissionsCheckedListener;

/**
 * 代理配置
 * Created by Hollow Goods on 2019-06-03.
 */
public class ProxyConfig {

    private Intent intent;
    private int requestCode;
    private OnProxyActivityResult onProxyActivityResult;
    private OnPermissionsCheckedListener onProxyRequestPermissionsResult;
    private String[] permissions;

    public ProxyConfig setIntent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public ProxyConfig setPermissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public ProxyConfig setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public ProxyConfig setOnProxyActivityResult(OnProxyActivityResult onProxyActivityResult) {
        this.onProxyActivityResult = onProxyActivityResult;
        return this;
    }

    public ProxyConfig setOnProxyRequestPermissionsResult(OnPermissionsCheckedListener onProxyRequestPermissionsResult) {
        this.onProxyRequestPermissionsResult = onProxyRequestPermissionsResult;
        return this;
    }

    public Intent getIntent() {
        return intent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public OnProxyActivityResult getOnProxyActivityResult() {
        return onProxyActivityResult;
    }

    public OnPermissionsCheckedListener getOnProxyRequestPermissionsResult() {
        return onProxyRequestPermissionsResult;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
