package com.hg.hollowgoods.Adapter.FastAdapter.CallBack;

import android.view.View;

/**
 * Created by Hollow Goods 2018-06-13.
 */
public interface OnFastListClick extends OnFastBaseClick {

    /**
     * 编辑按钮点击事件
     *
     * @param view
     * @param position
     */
    void onEditClick(View view, int position);

    /**
     * 删除按钮点击事件
     *
     * @param view
     * @param position
     */
    void onDeleteClick(View view, int position);

}
