package com.hg.hollowgoods.Widget.SmartRefreshLayout.listener;

import android.support.annotation.NonNull;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshLayout;

/**
 * 加载更多监听器
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
