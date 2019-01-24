package com.hg.hollowgoods.UI.Base.Click;

import android.view.View;
import android.widget.AdapterView;

/**
 * 普通列表项目点击事件
 * Created by HG on 2017-12-29.
 */

public class OnAdapterViewItemClickListener extends BaseOnClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public OnAdapterViewItemClickListener() {

    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     */
    public OnAdapterViewItemClickListener(boolean isDeveloping) {
        setDeveloping(isDeveloping);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     */
    public OnAdapterViewItemClickListener(boolean isDeveloping, boolean isIntercept) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     * @param isNeedCheck  是否需要检查双击间隔 默认 true
     */
    public OnAdapterViewItemClickListener(boolean isDeveloping, boolean isIntercept, boolean isNeedCheck) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
        setNeedCheck(isNeedCheck);
    }

    @Deprecated
    @Override
    public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            if (checkDoubleClickTime()) {
                onAdapterViewItemClick(parentView, view, position, id);
            }
        }
    }

    @Deprecated
    @Override
    public boolean onItemLongClick(AdapterView<?> parentView, View view, int position, long id) {

        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            onAdapterViewItemLongClick(parentView, view, position, id);
        }

        return isIntercept();
    }

    public void onAdapterViewItemClick(AdapterView<?> parentView, View view, int position, long id) {

    }

    public void onAdapterViewItemLongClick(AdapterView<?> parentView, View view, int position, long id) {

    }

}
