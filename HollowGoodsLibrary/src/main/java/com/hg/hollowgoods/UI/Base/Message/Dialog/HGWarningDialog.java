package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.hg.hollowgoods.R;

/**
 * 警告对话框
 * Created by HG on 2018-01-17.
 */

public class HGWarningDialog extends HGDialog {

    private String tip = "";
    private String yesButtonTxt = "";

    public HGWarningDialog(Context context, Object tip, Object yesButtonTxt, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        this.tip = getValue(tip, "");
        this.yesButtonTxt = getValue(yesButtonTxt, context.getString(R.string.sure));

        this.dialog = new AlertDialog.Builder(context)
                .setPositiveButton(this.yesButtonTxt, (dialog, which) -> {

                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGWarningDialog.this.onDialogDismissListener.onDialogDismiss(HGWarningDialog.this));

        this.dialog.setMessage(this.tip);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }
}
