package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.PopupWinHelper;

/**
 * Created by HG on 2018-01-17.
 */

public class HGDialog {

    public Context context;
    public AlertDialog dialog;
    public OnDialogDismissListener onDialogDismissListener;
    public OnDialogClickListener onDialogClickListener;
    public int code;
    public boolean isCloseAll = false;
    private PopupWinHelper popupWinHelper;

    public void closeDialog() {
        this.dialog.dismiss();
    }

    public void closeAllDialog() {
        isCloseAll = true;
        this.dialog.dismiss();
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public String getValue(Object source, String defaultValue) {

        if (source == null) {
            return defaultValue;
        } else if (source instanceof String) {
            return (String) source;
        } else if (source instanceof Integer) {
            return context.getString((Integer) source);
        }

        return defaultValue;
    }

    void showDescribe(View view, String title, String describe) {

        if (popupWinHelper == null) {
            popupWinHelper = new PopupWinHelper(context, v -> popupWinHelper.closePopupWin());
            popupWinHelper.init(
                    R.layout.popupwin_choice_dialog_describe,
                    Gravity.CENTER,
                    android.R.style.Animation_Toast,
                    "#00000000",
                    new int[]{R.id.tv_title, R.id.tv_describe},
                    new int[]{PopupWinHelper.TYPE_NO_LISTENER, PopupWinHelper.TYPE_ON_CLICK},
                    false
            );
        }

//        TextView titleView = popupWinHelper.getView(R.id.tv_title);
//        TextView describeView = popupWinHelper.getView(R.id.tv_describe);
//
//        titleView.setText(title);
//        describeView.setText(describe);

        popupWinHelper.showPopupWin(view);

        popupWinHelper.setText(R.id.tv_title, title);
        popupWinHelper.setText(R.id.tv_describe, describe);
    }

}
