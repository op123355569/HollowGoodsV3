package com.hg.hollowgoods.UI.Base.Click;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;

/**
 * Created by Hollow Goods 2017-12-29.
 */

public class BaseOnClickListener {

    /**
     * 上次点击时间
     */
    private long clickTime = 0L;
    /**
     * 是否需要检查双击间隔
     */
    private boolean isNeedCheck = true;
    /**
     * 是否开发中
     */
    private boolean isDeveloping = true;
    /**
     * 是否拦截长按事件传递给单击事件
     */
    private boolean isIntercept = true;

    public boolean checkDoubleClickTime() {

        if (isNeedCheck) {
            long nowTime = System.currentTimeMillis();

            if (nowTime - clickTime < HGSystemConfig.DOUBLE_CLICK_VIEW_TIME) {
                return false;
            } else {
                clickTime = nowTime;

                return true;
            }
        }

        return true;
    }

    public boolean isNeedCheck() {
        return isNeedCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        isNeedCheck = needCheck;
    }

    public boolean isDeveloping() {
        return isDeveloping;
    }

    public void setDeveloping(boolean developing) {
        isDeveloping = developing;
    }

    public boolean isIntercept() {
        return isIntercept;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    public void showDevelopTipDialog(View view) {
        showDevelopTipDialog(view.getContext());
    }

    public void showDevelopTipDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.tips).setMessage(R.string.is_developing).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

}
