package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * 提示对话框
 * Created by HG on 2018-01-17.
 */

public class HGTipDialog extends HGDialog {

    private String title;
    private String tip;

    public HGTipDialog(Context context, Object title, Object tip, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        this.title = getValue(title, "");
        this.tip = getValue(tip, "");

        this.dialog = new AlertDialog.Builder(context).create();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HGTipDialog.this.onDialogDismissListener.onDialogDismiss(HGTipDialog.this);
            }
        });

        if (!TextUtils.isEmpty(this.title)) {
            this.dialog.setTitle(this.title);
        }
        this.dialog.setMessage(this.tip);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }
}
