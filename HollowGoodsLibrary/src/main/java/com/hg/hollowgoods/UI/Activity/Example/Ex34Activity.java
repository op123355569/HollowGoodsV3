package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Widget.RippleValidatorEditText.RVEValidatorFactory;
import com.hg.hollowgoods.Widget.RippleValidatorEditText.RVEValidatorType;
import com.hg.hollowgoods.Widget.RippleValidatorEditText.RippleValidatorEditText;

/**
 * 波纹验证输入框示例
 * Created by HG on 2018-08-10.
 */

public class Ex34Activity extends BaseActivity {

    private RippleValidatorEditText name;
    private RippleValidatorEditText email;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_34;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex34);

        name.addValidator(
//                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "不能为空", ""),
                RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "至少1个字", 1),
//                RVEValidatorFactory.getValidator(RVEValidatorType.BEGIN, "开头字母为A", "A"),
                RVEValidatorFactory.getValidator(RVEValidatorType.MAX_LENGTH, "最多2个字", 2),
                RVEValidatorFactory.getValidator(RVEValidatorType.MIN_VALUE, "最小值5", 5),
                RVEValidatorFactory.getValidator(RVEValidatorType.MAX_VALUE, "最大值10", 10)
        );
        name.getEditText().setSingleLine(false);
        name.getEditText().setMaxLines(3);

        email.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "邮箱不合法", null),
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "邮箱不能为空", null),
                RVEValidatorFactory.getValidator(RVEValidatorType.END, "邮箱请以com结尾", "com")
        );

        return null;
    }

    @Override
    public void setListener() {

    }

}
