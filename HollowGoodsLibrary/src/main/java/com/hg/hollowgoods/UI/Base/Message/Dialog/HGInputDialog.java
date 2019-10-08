package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.ViewUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 输入对话框
 * Created by Hollow Goods 2018-01-17.
 */
@Deprecated
public class HGInputDialog extends HGDialog {

    private MaterialEditText inputView;

    private int minSize;
    private int maxSize;

    HGInputDialog(final Context context, Object hint, Object value, int inputType, int code, int minSize, int maxSize, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.onDialogDismissListener = onDialogDismissListener;

        CharSequence hint1 = getValue(hint, "");
        CharSequence value1 = getValue(value, "");

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_input)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGInputDialog.this.onDialogDismissListener.onDialogDismiss(HGInputDialog.this));
        this.dialog.show();

        this.inputView = this.dialog.findViewById(R.id.et_input);
        TextView cancelView = this.dialog.findViewById(R.id.tv_cancel);
        TextView sureView = this.dialog.findViewById(R.id.tv_sure);

        if (minSize != HGConstants.INPUT_SIZE_DEFAULT) {
            this.inputView.setMinCharacters(minSize);
        }

        if (maxSize != HGConstants.INPUT_SIZE_DEFAULT) {
            this.inputView.setMaxCharacters(maxSize);
        }

        if (cancelView != null) {
            cancelView.setOnClickListener(v -> dialog.dismiss());
        }

        if (sureView != null) {
            sureView.setOnClickListener(v -> {

                int size = inputView.getText().toString().length();
                String inputTip;

                if (HGInputDialog.this.minSize == HGConstants.INPUT_SIZE_DEFAULT && HGInputDialog.this.maxSize == HGConstants.INPUT_SIZE_DEFAULT) {
                    backInputData(HGInputDialog.this.code);
                } else if (HGInputDialog.this.minSize == HGConstants.INPUT_SIZE_DEFAULT && HGInputDialog.this.maxSize > 0) {
                    if (size <= HGInputDialog.this.maxSize) {
                        backInputData(HGInputDialog.this.code);
                    } else {
                        inputTip = String.format(
                                context.getString(R.string.most_input),
                                "" + HGInputDialog.this.maxSize
                        );
                        Toast.makeText(HGInputDialog.this.context, inputTip, Toast.LENGTH_SHORT).show();
                    }
                } else if (HGInputDialog.this.minSize > 0 && HGInputDialog.this.maxSize == HGConstants.INPUT_SIZE_DEFAULT) {
                    if (size >= HGInputDialog.this.minSize) {
                        backInputData(HGInputDialog.this.code);
                    } else {
                        inputTip = String.format(
                                context.getString(R.string.least_input),
                                "" + HGInputDialog.this.minSize
                        );
                        Toast.makeText(HGInputDialog.this.context, inputTip, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (size >= HGInputDialog.this.minSize && size <= HGInputDialog.this.maxSize) {
                        backInputData(HGInputDialog.this.code);
                    } else {
                        if (HGInputDialog.this.minSize == HGInputDialog.this.maxSize) {
                            inputTip = String.format(
                                    context.getString(R.string.limit_input2),
                                    ("" + HGInputDialog.this.minSize)
                            );
                        } else {
                            inputTip = String.format(
                                    context.getString(R.string.limit_input),
                                    ("" + HGInputDialog.this.minSize),
                                    ("" + HGInputDialog.this.maxSize)
                            );
                        }

                        Toast.makeText(HGInputDialog.this.context, inputTip, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(hint1)) {
            this.inputView.setHint(hint1);
            this.inputView.setFloatingLabelText(hint1);
        }

        if (!TextUtils.isEmpty(value1)) {
            this.inputView.setText(value1);
            ViewUtils.setEditTextCursorLocation(this.inputView);
        }

        if (inputType != HGConstants.INPUT_TYPE_DEFAULT) {
            this.inputView.setInputType(inputType);
        }
    }

    private void backInputData(int code) {

        if (onDialogClickListener != null) {
            Bundle data = new Bundle();
            data.putString(HGParamKey.InputValue.getValue(), this.inputView.getText().toString());
            onDialogClickListener.onDialogClick(code, true, data);
        }

        this.dialog.dismiss();
    }

}
