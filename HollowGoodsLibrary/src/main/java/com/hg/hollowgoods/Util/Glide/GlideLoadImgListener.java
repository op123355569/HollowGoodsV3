package com.hg.hollowgoods.Util.Glide;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Glide加载图片回调接口
 * Created by Hollow Goods on unknown.
 */

public interface GlideLoadImgListener {

    /**
     * 加载图片成功
     *
     * @param v        view
     * @param drawable drawable
     */
    void onImgLoadSuccess(View v, Drawable drawable);

    /**
     * 加载图片失败
     *
     * @param v view
     */
    void onImgLoadFail(View v);

}
