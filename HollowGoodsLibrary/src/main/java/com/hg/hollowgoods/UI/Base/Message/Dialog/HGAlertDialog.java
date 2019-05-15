package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.hg.hollowgoods.R;

/**
 * 询问对话框
 * Created by HG on 2018-01-17.
 */

public class HGAlertDialog extends HGDialog {

    private String title = "";
    private String tip = "";
    private String noButtonTxt = "";
    private String yesButtonTxt = "";

    HGAlertDialog(Context context, Object title, Object tip, Object noButtonTxt, Object yesButtonTxt, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        this.title = getValue(title, "");
        this.tip = getValue(tip, "");
        this.noButtonTxt = getValue(noButtonTxt, context.getString(R.string.cancel));
        this.yesButtonTxt = getValue(yesButtonTxt, context.getString(R.string.sure));

        this.dialog = new AlertDialog.Builder(context)
                .setPositiveButton(this.yesButtonTxt, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGAlertDialog.this.code, true, null);
                    }
                })
                .setNegativeButton(this.noButtonTxt, (dialog, which) -> {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(HGAlertDialog.this.code, false, null);
                    }
                })
                .create();
        this.dialog.setOnDismissListener(dialog -> HGAlertDialog.this.onDialogDismissListener.onDialogDismiss(HGAlertDialog.this));

        if (!TextUtils.isEmpty(this.title)) {
            this.dialog.setTitle(this.title);
        }
        this.dialog.setMessage(this.tip);
        this.dialog.setCancelable(cancelable);
        this.dialog.show();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNoButtonTxt() {
        return noButtonTxt;
    }

    public void setNoButtonTxt(String noButtonTxt) {
        this.noButtonTxt = noButtonTxt;
    }

    public String getYesButtonTxt() {
        return yesButtonTxt;
    }

    public void setYesButtonTxt(String yesButtonTxt) {
        this.yesButtonTxt = yesButtonTxt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
