package com.hg.hollowgoods.UI.Base.Message.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.ViewUtils;
import com.hg.hollowgoods.Widget.ValidatorInput.ValidatorInputView;

/**
 * 输入对话框
 * <p>
 * Created by Hollow Goods 2018-01-17.
 * <p>
 * <p>
 * 修改UI
 * <p>
 * Updated by Hollow Goods 2019-11-05.
 */

class HGInputNewDialog extends HGDialog {

    private ValidatorInputView inputView;

    HGInputNewDialog(BaseActivity context, ConfigInput configInput, int code, OnDialogDismissListener onDialogDismissListener) {

        this.context = context;
        this.code = code;
        this.onDialogDismissListener = onDialogDismissListener;

        this.dialog = new AlertDialog
                .Builder(context)
                .setView(R.layout.dialog_input_new)
                .create();
        this.dialog.setOnDismissListener(dialog -> HGInputNewDialog.this.onDialogDismissListener.onDialogDismiss(HGInputNewDialog.this));
        this.dialog.show();

        this.inputView = this.dialog.findViewById(R.id.et_input);
        if (inputView != null) {
            inputView.setBaseActivity(context);
        }
        TextView cancelView = this.dialog.findViewById(R.id.tv_cancel);
        TextView sureView = this.dialog.findViewById(R.id.tv_sure);
        TextView titleView = this.dialog.findViewById(R.id.tv_title);

        // 标题
        CharSequence titleValue = getValue(configInput.getTitle(), "");
        if (titleView != null && !TextUtils.isEmpty(titleValue)) {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(titleValue);
        }

        // 默认字符
        CharSequence hintValue = getValue(configInput.getHint(), this.context.getString(R.string.input_dialog_hint));
        this.inputView.setHint(hintValue);

        // 填写字符
        CharSequence textValue = getValue(configInput.getText(), "");
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

        // 最大输入字符数
        if (configInput.getMaxLength() > 0) {
            inputView.getInputView().setFilters(
                    new InputFilter[]{new InputFilter.LengthFilter(configInput.getMaxLength())}
            );
        }

        // 输入类型
        if (configInput.getInputType() != HGConstants.INPUT_TYPE_DEFAULT) {
            inputView.getInputView().setInputType(configInput.getInputType());
        }

        // 验证规则
        inputView.setValidator(configInput.getValidator());

        // 光标移至最后
        ViewUtils.setEditTextCursorLocation(inputView.getInputView());

        if (cancelView != null) {
            cancelView.setOnClickListener(v -> dialog.dismiss());
        }

        if (sureView != null) {
            sureView.setOnClickListener(v -> {

                boolean result = inputView.isInputRight();

                if (result) {
                    backInputData();
                } else {
                    t.error(R.string.input_content_error);
                }
            });
        }

        if (configInput.isAutoShowKeyboard()) {
            new Handler().postDelayed(this::showKeyboard, 50);
        }
    }

    private void backInputData() {

        if (onDialogClickListener != null) {
            Bundle data = new Bundle();
            data.putString(HGParamKey.InputValue.getValue(), this.inputView.getText());
            onDialogClickListener.onDialogClick(code, true, data);
        }

        this.dialog.dismiss();
    }

    private void showKeyboard() {
        if (inputView != null) {
            // 设置可获得焦点
            inputView.getInputView().setFocusable(true);
            inputView.getInputView().setFocusableInTouchMode(true);
            // 请求获得焦点
            inputView.getInputView().requestFocus();
            // 调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) inputView.getInputView().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.showSoftInput(inputView.getInputView(), 0);
            }
        }
    }

}
