package com.hg.hollowgoods.Util.XUtils;

import org.xutils.common.Callback;

import java.io.File;

/**
 * 下载文件监听
 * Created by Hollow Goods 2018-01-17.
 */

public interface DownloadListener {

    void onDownloadSuccess(File file);

    void onDownloadError(Throwable ex);

    void onDownloadLoading(long total, long current);

    void onDownloadFinish();

    default void onDownloadCancel(Callback.CancelledException cex) {

    }

}
