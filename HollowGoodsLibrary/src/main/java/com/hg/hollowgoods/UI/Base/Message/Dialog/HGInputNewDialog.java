package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.ViewUtils;
import com.hg.hollowgoods.Widget.ValidatorInput.ValidatorInputView;

/**
 * 输入对话框
 * Created by HG on 2018-01-17.
 */

public class HGInputNewDialog extends HGDialog {

    private ValidatorInputView inputView;
    private TextView cancelView;
    private TextView sureView;
    private TextView titleView;

    private ConfigInput configInput;

    public HGInputNewDialog(Context context, ConfigInput configInput, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.configInput = configInput;
        this.onDialogDismissListener = onDialogDismissListener;

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_input_new)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGInputNewDialog.this.onDialogDismissListener.onDialogDismiss(HGInputNewDialog.this));
        this.dialog.show();

        this.inputView = this.dialog.findViewById(R.id.et_input);
        this.cancelView = this.dialog.findViewById(R.id.tv_cancel);
        this.sureView = this.dialog.findViewById(R.id.tv_sure);
        this.titleView = this.dialog.findViewById(R.id.tv_title);

        // 标题
        String titleValue = getValue(configInput.getTitle(), "");
        if (!TextUtils.isEmpty(titleValue)) {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(titleValue);
        }

        // 默认字符
        String hintValue = getValue(configInput.getHint(), this.context.getString(R.string.input_dialog_hint));
        this.inputView.setHint(hintValue);

        // 填写字符
        String textValue = getValue(configInput.getText(), "");
        inputView.setText(textValue);

        // 行数
        if (configInput.getMaxLines() > 1) {
            if (configInput.getMaxLines() > 8) {
                configInput.setMaxLines(8);
            }

            inputView.getInputView().setSingleLine(false);
            inputView.getInputView().setMaxLines(configInput.getMaxLines());

//            int oneLineHeight = DensityUtils.sp2px(this.context, 19.5f);
//            int margin = DensityUtils.dp2px(this.context, 16f);
//
//            ViewGroup.LayoutParams vlp = inputView.getLayoutParams();
//            vlp.height = oneLineHeight * configInput.getMaxLines() + margin;
//            inputView.setLayoutParams(vlp);
        }

        // 输入类型
        if (configInput.getInputType() != HGConstants.INPUT_TYPE_DEFAULT) {
            inputView.getInputView().setInputType(configInput.getInputType());
        }

        // 验证规则
        inputView.setValidator(configInput.getValidator());

        // 光标移至最后
        ViewUtils.setEditTextCursorLocation(inputView.getInputView());

        this.cancelView.setOnClickListener(v -> dialog.dismiss());

        this.sureView.setOnClickListener(v -> {

            boolean result = inputView.isInputRight();

            if (result) {
                backInputData();
            } else {
                t.error(R.string.input_content_error);
            }
        });
    }

    private void backInputData() {

        if (onDialogClickListener != null) {
            Bundle data = new Bundle();
            data.putString(HGParamKey.InputValue.getValue(), this.inputView.getText().toString());
            onDialogClickListener.onDialogClick(code, true, data);
        }

        this.dialog.dismiss();
    }

}
