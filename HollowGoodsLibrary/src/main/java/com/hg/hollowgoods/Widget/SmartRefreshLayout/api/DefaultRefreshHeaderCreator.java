package com.hg.hollowgoods.Widget.SmartRefreshLayout.api;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 默认Header创建器
 * <p>
 * Created by Hollow Goods on unknown.
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
