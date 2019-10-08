package com.hg.hollowgoods.Adapter.FastAdapter.CallBack;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Hollow Goods 2018-06-14.
 */
public interface OnFastBaseClick {

    /**
     * 项目点击事件
     *
     * @param view
     * @param holder
     * @param position
     */
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

}
