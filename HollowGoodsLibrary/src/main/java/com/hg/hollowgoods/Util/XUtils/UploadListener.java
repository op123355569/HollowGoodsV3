package com.hg.hollowgoods.Util.XUtils;

import org.xutils.common.Callback;

/**
 * 上传文件监听
 * Created by Hollow Goods 2018-01-17.
 */

public interface UploadListener {

    void onUploadSuccess(String result);

    void onUploadError(Throwable ex);

    void onUploadLoading(long total, long current);

    void onUploadFinish();

    default void onUploadCancel(Callback.CancelledException cex) {

    }

}
