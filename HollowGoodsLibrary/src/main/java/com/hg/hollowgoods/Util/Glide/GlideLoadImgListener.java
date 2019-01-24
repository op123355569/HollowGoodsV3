package com.hg.hollowgoods.Util.Glide;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Glide加载图片回调接口
 * Created by HG
 */

public interface GlideLoadImgListener {

    /**
     * 加载图片成功
     *
     * @param v
     * @param bitmap
     */
    public void onImgLoadSuccess(View v, Bitmap bitmap);

    /**
     * 加载图片失败
     *
     * @param v
     */
    public void onImgLoadFail(View v);

}
