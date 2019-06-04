package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.hg.hollowgoods.R;

/**
 * 警告对话框
 * Created by HG on 2018-01-17.
 */

class HGWarningDialog extends HGDialog {

    HGWarningDialog(Context context, Object title, Object tip, Object yesButtonTxt, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        CharSequence titleValue = getValue(title, "");
        CharSequence tipValue = getValue(tip, "");
        CharSequence yesButtonTxtValue = getValue(yesButtonTxt, context.getString(R.string.sure));

        this.dialog = new AlertDialog.Builder(context)
                .setPositiveButton(yesButtonTxtValue, (dialog, which) -> {

                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGWarningDialog.this.onDialogDismissListener.onDialogDismiss(HGWarningDialog.this));

        if (!TextUtils.isEmpty(titleValue)) {
            this.dialog.setTitle(titleValue);
        }
        this.dialog.setMessage(tipValue);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }
}
