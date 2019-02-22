package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * 共同元素界面跳转示例界面
 * Created by HG
 */

public class Ex8Activity extends BaseActivity {

    private View button;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_8;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        button = findViewById(R.id.ll_btn);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex8);

        return null;
    }

    @Override
    public void setListener() {
        button.setOnClickListener(new OnViewClickListener(false));
    }

}
