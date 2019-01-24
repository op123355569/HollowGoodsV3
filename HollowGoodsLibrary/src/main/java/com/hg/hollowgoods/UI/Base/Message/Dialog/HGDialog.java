package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

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

}
