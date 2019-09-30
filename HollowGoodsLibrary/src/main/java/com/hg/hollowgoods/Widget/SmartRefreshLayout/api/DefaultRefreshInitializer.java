package com.hg.hollowgoods.Widget.SmartRefreshLayout.api;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 默认全局初始化器
 * <p>
 * Created by Hollow Goods on unknown.
 */
public interface DefaultRefreshInitializer {
    void initialize(@NonNull Context context, @NonNull RefreshLayout layout);
}
