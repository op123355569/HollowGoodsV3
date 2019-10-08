package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * 提示对话框
 * Created by Hollow Goods 2018-01-17.
 */

class HGTipDialog extends HGDialog {

    HGTipDialog(Context context, Object title, Object tip, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        CharSequence title1 = getValue(title, "");
        CharSequence tip1 = getValue(tip, "");

        this.dialog = new AlertDialog.Builder(context).create();
        this.dialog.setOnDismissListener(dialog -> HGTipDialog.this.onDialogDismissListener.onDialogDismiss(HGTipDialog.this));

        if (!TextUtils.isEmpty(title1)) {
            this.dialog.setTitle(title1);
        }
        this.dialog.setMessage(tip1);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }
}
