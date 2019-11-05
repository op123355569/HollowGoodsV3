package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.R;

/**
 * 提示对话框
 * <p>
 * Created by Hollow Goods 2018-01-17.
 * <p>
 * <p>
 * 修改UI
 * <p>
 * Updated by Hollow Goods 2019-11-05.
 */

class HGTipDialog extends HGDialog {

    HGTipDialog(Context context, Object title, Object tip, boolean cancelable, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.onDialogDismissListener = onDialogDismissListener;
        this.code = code;

        CharSequence title1 = getValue(title, "");
        CharSequence tip1 = getValue(tip, "");

        this.dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_tip)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGTipDialog.this.onDialogDismissListener.onDialogDismiss(HGTipDialog.this));
        this.dialog.setCancelable(cancelable);
        this.dialog.show();

        TextView mTitle = this.dialog.findViewById(R.id.tv_title);
        TextView mContent = this.dialog.findViewById(R.id.tv_content);

        if (!TextUtils.isEmpty(title1) && mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title1);
        }
        if (mContent != null) {
            mContent.setText(tip1);
        }
    }
}
