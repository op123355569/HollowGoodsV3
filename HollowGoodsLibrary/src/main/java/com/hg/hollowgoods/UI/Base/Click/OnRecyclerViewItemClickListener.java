package com.hg.hollowgoods.UI.Base.Click;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;

/**
 * Recycler列表项目点击事件
 * Created by HG on 2017-12-29.
 */

public class OnRecyclerViewItemClickListener extends BaseOnClickListener implements MultiItemTypeAdapter.OnItemClickListener {

    public OnRecyclerViewItemClickListener() {

    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     */
    public OnRecyclerViewItemClickListener(boolean isDeveloping) {
        setDeveloping(isDeveloping);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     */
    public OnRecyclerViewItemClickListener(boolean isDeveloping, boolean isIntercept) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     * @param isNeedCheck  是否需要检查双击间隔 默认 true
     */
    public OnRecyclerViewItemClickListener(boolean isDeveloping, boolean isIntercept, boolean isNeedCheck) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
        setNeedCheck(isNeedCheck);
    }

    @Deprecated
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            if (checkDoubleClickTime()) {
                onRecyclerViewItemClick(view, viewHolder, position);
            }
        }
    }

    @Deprecated
    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            onRecyclerViewItemLongClick(view, viewHolder, position);
        }

        return isIntercept();
    }

    public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

    }

    public void onRecyclerViewItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

    }

}
