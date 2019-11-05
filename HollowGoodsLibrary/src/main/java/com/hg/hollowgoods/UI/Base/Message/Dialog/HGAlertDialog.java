package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.R;

/**
 * 询问对话框
 * <p>
 * Created by Hollow Goods 2018-01-17.
 * <p>
 * <p>
 * 修改UI
 * <p>
 * Updated by Hollow Goods 2019-11-05.
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
                .setView(R.layout.dialog_alert)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGAlertDialog.this.onDialogDismissListener.onDialogDismiss(HGAlertDialog.this));
        this.dialog.setCancelable(cancelable);
        this.dialog.show();

        TextView mTitle = this.dialog.findViewById(R.id.tv_title);
        TextView mContent = this.dialog.findViewById(R.id.tv_content);
        TextView cancelView = this.dialog.findViewById(R.id.tv_cancel);
        TextView sureView = this.dialog.findViewById(R.id.tv_sure);

        if (!TextUtils.isEmpty(title1) && mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title1);
        }
        if (mContent != null) {
            mContent.setText(tip1);
        }

        if (cancelView != null) {
            cancelView.setText(noButtonTxt1);
            cancelView.setOnClickListener(v -> {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onDialogClick(HGAlertDialog.this.code, false, null);
                }
                dialog.dismiss();
            });
        }
        if (sureView != null) {
            sureView.setText(yesButtonTxt1);
            sureView.setOnClickListener(v -> {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onDialogClick(HGAlertDialog.this.code, true, null);
                }
                dialog.dismiss();
            });
        }
    }

}
