package com.hg.hollowgoods.UI.Base.Click;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Toolbar菜单项目点击事件
 * Created by HG on 2017-12-29.
 */

public class OnToolbarMenuItemClickListener extends BaseOnClickListener implements Toolbar.OnMenuItemClickListener {

    /**
     * 上下文
     */
    private Context context;

    /**
     * @param context
     */
    public OnToolbarMenuItemClickListener(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @param isDeveloping 是否开发中 默认 true
     */
    public OnToolbarMenuItemClickListener(Context context, boolean isDeveloping) {
        this.context = context;
        setDeveloping(isDeveloping);
    }

    /**
     * @param context
     * @param isDeveloping 是否开发中 默认 true
     * @param isNeedCheck  是否需要检查双击间隔 默认 true
     */
    public OnToolbarMenuItemClickListener(Context context, boolean isDeveloping, boolean isNeedCheck) {
        this.context = context;
        setDeveloping(isDeveloping);
        setNeedCheck(isNeedCheck);
    }

    @Deprecated
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (isDeveloping()) {
            showDevelopTipDialog(context);
        } else {
            if (checkDoubleClickTime()) {
                onToolbarMenuItemClick(item);
            }
        }

        return true;
    }

    public void onToolbarMenuItemClick(MenuItem item) {

    }

}
