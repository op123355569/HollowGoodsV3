package com.hg.hollowgoods.Widget.SmartRefreshLayout.impl;

import android.annotation.SuppressLint;
import android.view.View;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshHeader;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.internal.InternalAbstract;

/**
 * 刷新头部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader/*, InvocationHandler*/ {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
