package com.hg.hollowgoods.Util.XUtils;

import android.graphics.Bitmap;
import android.view.View;

import org.xutils.common.Callback;

/**
 * 获取图片监听
 * Created by Hollow Goods 2018-01-17.
 */

public interface LoadImageListener {

    void onLoadSuccess(View view, Bitmap bitmap);

    void onLoadError(View view, Throwable ex);

    default void onLoadLoading(View view, long total, long current) {

    }

    void onLoadFinish(View view);

    default void onLoadCancel(View view, Callback.CancelledException cex) {

    }

}
