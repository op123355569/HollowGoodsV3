package com.hg.hollowgoods.UI.Base.Message.Dialog;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;

/**
 * 输入配置
 * Created by HG on 2018-08-10.
 */
public class ConfigInput {

    private Object title = "";
    private Object hint = "";
    private Object text = "";
    private int maxLines = 1;
    private int maxLength = -1;
    private int inputType = HGConstants.INPUT_TYPE_DEFAULT;
    private Validator[] validator;
    private boolean autoShowKeyboard = true;

    /**** Set ****/

    public ConfigInput setTitle(Object title) {
        this.title = title;
        return this;
    }

    public ConfigInput setHint(Object hint) {
        this.hint = hint;
        return this;
    }

    public ConfigInput setText(Object text) {
        this.text = text;
        return this;
    }

    public ConfigInput setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        return this;
    }

    public ConfigInput setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public ConfigInput setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    public ConfigInput setValidator(Validator... validator) {
        this.validator = validator;
        return this;
    }

    public ConfigInput setAutoShowKeyboard(boolean autoShowKeyboard) {
        this.autoShowKeyboard = autoShowKeyboard;
        return this;
    }

    /**** Get ****/

    public Object getTitle() {
        return title;
    }

    public Object getHint() {
        return hint;
    }

    public Object getText() {
        return text;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getInputType() {
        return inputType;
    }

    public Validator[] getValidator() {
        return validator;
    }

    public boolean isAutoShowKeyboard() {
        return autoShowKeyboard;
    }
}
