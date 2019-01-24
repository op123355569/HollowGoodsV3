package com.hg.hollowgoods.Adapter.FastAdapter.CallBack;

import android.view.View;

/**
 * Created by HG on 2018-06-13.
 */
public interface OnFastClick extends OnFastListClick, OnFastItemClick {

    /**
     * 拍照按钮点击事件
     *
     * @param view
     * @param position
     * @param sortNumber
     */
    void onTakePhotoClick(View view, int position, int sortNumber);

    /**
     * 相册按钮点击事件
     *
     * @param view
     * @param position
     * @param sortNumber
     */
    void onOpenAlbumClick(View view, int position, int sortNumber);

    /**
     * 提交按钮点击事件
     *
     * @param view
     */
    void onSubmitClick(View view, int id);

}
