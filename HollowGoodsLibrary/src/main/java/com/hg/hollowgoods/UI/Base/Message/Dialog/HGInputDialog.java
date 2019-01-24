package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.ViewUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 输入对话框
 * Created by HG on 2018-01-17.
 */

public class HGInputDialog extends HGDialog {

    private MaterialEditText inputView;
    private TextView cancelView;
    private TextView sureView;

    private String hint = "";
    private String value = "";
    private int inputType;
    private int minSize;
    private int maxSize;

    public HGInputDialog(final Context context, Object hint, Object value, int inputType, int code, int minSize, int maxSize, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.inputType = inputType;
        this.code = code;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.onDialogDismissListener = onDialogDismissListener;

        this.hint = getValue(hint, "");
        this.value = getValue(value, "");

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_input)
                .create();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                HGInputDialog.this.onDialogDismissListener.onDialogDismiss(HGInputDialog.this);
            }
        });
        this.dialog.show();

        this.inputView = this.dialog.findViewById(R.id.et_input);
        this.cancelView = this.dialog.findViewById(R.id.tv_cancel);
        this.sureView = this.dialog.findViewById(R.id.tv_sure);

        if (minSize != Constants.INPUT_SIZE_DEFAULT) {
            this.inputView.setMinCharacters(minSize);
        }

        if (maxSize != Constants.INPUT_SIZE_DEFAULT) {
            this.inputView.setMaxCharacters(maxSize);
        }

        this.cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        this.sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = inputView.getText().toString().length();
                String inputTip;

                if (HGInputDialog.this.minSize == Constants.INPUT_SIZE_DEFAULT && HGInputDialog.this.maxSize == Constants.INPUT_SIZE_DEFAULT) {
                    backInputData(HGInputDialog.this.code);
                } else if (HGInputDialog.this.minSize == Constants.INPUT_SIZE_DEFAULT && HGInputDialog.this.maxSize > 0) {
                    if (size <= HGInputDialog.this.maxSize) {
                        backInputData(HGInputDialog.this.code);
                    } else {
                        inputTip = String.format(
                                context.getString(R.string.most_input),
                                "" + HGInputDialog.this.maxSize
                        );
                        Toast.makeText(HGInputDialog.this.context, inputTip, Toast.LENGTH_SHORT).show();
                    }
                } else if (HGInputDialog.this.minSize > 0 && HGInputDialog.this.maxSize == Constants.INPUT_SIZE_DEFAULT) {
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
            }
        });

        if (!TextUtils.isEmpty(this.hint)) {
            this.inputView.setHint(this.hint);
            this.inputView.setFloatingLabelText(this.hint);
        }

        if (!TextUtils.isEmpty(this.value)) {
            this.inputView.setText(this.value);
            ViewUtils.setEditTextCursorLocation(this.inputView);
        }

        if (inputType != Constants.INPUT_TYPE_DEFAULT) {
            this.inputView.setInputType(this.inputType);
        }
    }

    private void backInputData(int code) {

        if (onDialogClickListener != null) {
            Bundle data = new Bundle();
            data.putString(Constants.PARAM_KEY_1, this.inputView.getText().toString());
            onDialogClickListener.onDialogClick(code, true, data);
        }

        this.dialog.dismiss();
    }

}
