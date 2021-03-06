package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * 万能阴影控件示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex22Activity extends BaseActivity {

    private View shadowView;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_22;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        shadowView = findViewById(R.id.shadowView);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex22);

        return null;
    }

    @Override
    public void setListener() {

        shadowView.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {

            }
        });
    }

}
