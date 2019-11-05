package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.R;

/**
 * 警告对话框
 * <p>
 * Created by Hollow Goods 2018-01-17.
 * <p>
 * <p>
 * 修改UI
 * <p>
 * Updated by Hollow Goods 2019-11-05.
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
                .setView(R.layout.dialog_warning)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGWarningDialog.this.onDialogDismissListener.onDialogDismiss(HGWarningDialog.this));
        this.dialog.setCancelable(cancelable);
        this.dialog.show();

        TextView mTitle = this.dialog.findViewById(R.id.tv_title);
        TextView mContent = this.dialog.findViewById(R.id.tv_content);
        TextView cancelView = this.dialog.findViewById(R.id.tv_cancel);
        TextView sureView = this.dialog.findViewById(R.id.tv_sure);
        View lineView = this.dialog.findViewById(R.id.line);

        if (!TextUtils.isEmpty(titleValue) && mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(titleValue);
        }
        if (mContent != null) {
            mContent.setText(tipValue);
        }

        if (cancelView != null) {
            cancelView.setVisibility(View.GONE);
        }
        if (sureView != null) {
            sureView.setText(yesButtonTxtValue);
            sureView.setOnClickListener(v -> dialog.dismiss());
        }
        if (lineView != null) {
            lineView.setVisibility(View.GONE);
        }
    }
}
