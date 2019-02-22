package com.hg.hollowgoods.UI.Base.Message.Dialog;

import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;

/**
 * @ClassName:输入配置
 * @Description:
 * @author: 马禛
 * @date: 2018年08月10日
 */
public class ConfigInput {

    private Object title = "";
    private Object hint = "";
    private Object text = "";
    private int maxLines = 1;
    private int inputType = HGConstants.INPUT_TYPE_DEFAULT;
    private Validator[] validator;

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

    public ConfigInput setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    public ConfigInput setValidator(Validator... validator) {
        this.validator = validator;
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

    public int getInputType() {
        return inputType;
    }

    public Validator[] getValidator() {
        return validator;
    }
}
