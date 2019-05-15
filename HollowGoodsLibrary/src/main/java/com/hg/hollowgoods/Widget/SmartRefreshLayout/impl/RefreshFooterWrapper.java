package com.hg.hollowgoods.Widget.SmartRefreshLayout.impl;

import android.annotation.SuppressLint;
import android.view.View;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshFooter;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.internal.InternalAbstract;

/**
 * 刷新底部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends InternalAbstract implements RefreshFooter/*, InvocationHandler */ {

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return mWrappedInternal instanceof RefreshFooter && ((RefreshFooter) mWrappedInternal).setNoMoreData(noMoreData);
    }

}
