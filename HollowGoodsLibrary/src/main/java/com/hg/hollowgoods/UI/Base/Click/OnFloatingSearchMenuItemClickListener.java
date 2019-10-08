package com.hg.hollowgoods.UI.Base.Click;

import android.content.Context;
import android.view.MenuItem;

import com.hg.hollowgoods.Widget.FloatingSearchView.FloatingSearchView;

/**
 * FloatingSearchView菜单项目点击事件
 * Created by Hollow Goods 2017-12-29.
 */

public class OnFloatingSearchMenuItemClickListener extends BaseOnClickListener implements FloatingSearchView.OnMenuItemClickListener {

    /**
     * 上下文
     */
    private Context context;

    /**
     * @param context
     */
    public OnFloatingSearchMenuItemClickListener(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @param isDeveloping 是否开发中 默认 true
     */
    public OnFloatingSearchMenuItemClickListener(Context context, boolean isDeveloping) {
        this.context = context;
        setDeveloping(isDeveloping);
    }

    /**
     * @param context
     * @param isDeveloping 是否开发中 默认 true
     * @param isNeedCheck  是否需要检查双击间隔 默认 true
     */
    public OnFloatingSearchMenuItemClickListener(Context context, boolean isDeveloping, boolean isNeedCheck) {
        this.context = context;
        setDeveloping(isDeveloping);
        setNeedCheck(isNeedCheck);
    }

    @Deprecated
    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        if (isDeveloping()) {
            showDevelopTipDialog(context);
        } else {
            if (checkDoubleClickTime()) {
                onFloatingSearchMenuItemClick(item);
            }
        }
    }

    public void onFloatingSearchMenuItemClick(MenuItem item) {

    }

}
