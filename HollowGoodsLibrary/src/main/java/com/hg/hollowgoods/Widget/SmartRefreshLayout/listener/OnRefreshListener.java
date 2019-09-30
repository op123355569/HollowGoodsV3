package com.hg.hollowgoods.Widget.SmartRefreshLayout.listener;

import android.support.annotation.NonNull;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshLayout;

/**
 * 刷新监听器
 * Created by Hollow Goods on unknown.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
