package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.hg.hollowgoods.R;

/**
 * 询问对话框
 * Created by Hollow Goods 2018-01-17.
 */

class HGAlertDialog extends HGDialog {

    HGAlertDialog(Context context, Object title, Object tip, Object noButtonTxt, Object yesButtonTxt, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        CharSequence title1 = getValue(title, "");
        CharSequence tip1 = getValue(tip, "");
        CharSequence noButtonTxt1 = getValue(noButtonTxt, context.getString(R.string.cancel));
        CharSequence yesButtonTxt1 = getValue(yesButtonTxt, context.getString(R.string.sure));

        this.dialog = new AlertDialog.Builder(context)
                .setPositiveButton(yesButtonTxt1, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGAlertDialog.this.code, true, null);
                    }
                })
                .setNegativeButton(noButtonTxt1, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGAlertDialog.this.code, false, null);
                    }
                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGAlertDialog.this.onDialogDismissListener.onDialogDismiss(HGAlertDialog.this));

        if (!TextUtils.isEmpty(title1)) {
            this.dialog.setTitle(title1);
        }
        this.dialog.setMessage(tip1);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }

}
