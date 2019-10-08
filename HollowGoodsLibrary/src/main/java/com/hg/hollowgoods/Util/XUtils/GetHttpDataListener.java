package com.hg.hollowgoods.Util.XUtils;

import org.xutils.common.Callback;

/**
 * 获取网络数据监听
 * Created by Hollow Goods 2018-01-17.
 */

public interface GetHttpDataListener {

    void onGetSuccess(String result);

    void onGetError(Throwable ex);

    default void onGetLoading(long total, long current) {

    }

    void onGetFinish();

    default void onGetCancel(Callback.CancelledException cex) {

    }

}
