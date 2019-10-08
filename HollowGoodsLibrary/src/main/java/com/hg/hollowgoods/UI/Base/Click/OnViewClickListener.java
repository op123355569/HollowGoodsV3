package com.hg.hollowgoods.UI.Base.Click;

import android.view.View;

/**
 * 普通控件点击事件
 * Created by Hollow Goods 2017-12-29.
 */

public class OnViewClickListener extends BaseOnClickListener implements View.OnClickListener, View.OnLongClickListener {

    public OnViewClickListener() {

    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     */
    public OnViewClickListener(boolean isDeveloping) {
        setDeveloping(isDeveloping);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     */
    public OnViewClickListener(boolean isDeveloping, boolean isIntercept) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
    }

    /**
     * @param isDeveloping 是否开发中 默认 true
     * @param isIntercept  是否拦截长按事件传递给单击事件 默认 true
     * @param isNeedCheck  是否需要检查双击间隔 默认 true
     */
    public OnViewClickListener(boolean isDeveloping, boolean isIntercept, boolean isNeedCheck) {
        setDeveloping(isDeveloping);
        setIntercept(isIntercept);
        setNeedCheck(isNeedCheck);
    }

    @Deprecated
    @Override
    public void onClick(View view) {
        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            if (checkDoubleClickTime()) {
                onViewClick(view, view.getId());
            }
        }
    }

    @Deprecated
    @Override
    public boolean onLongClick(View view) {

        if (isDeveloping()) {
            showDevelopTipDialog(view);
        } else {
            onViewLongClick(view, view.getId());
        }

        return isIntercept();
    }

    public void onViewClick(View view, int id) {

    }

    public void onViewLongClick(View view, int id) {

    }

}
