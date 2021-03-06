package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Widget.AutoShowDeleteLayout;

/**
 * 示例7界面
 * Created by Hollow Goods 2018-07-26.
 */
public class Ex7Activity extends BaseActivity {

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_7;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex7);

        AutoShowDeleteLayout deleteLayout = findViewById(R.id.deleteLayout);
        deleteLayout.setDeleteButtonColorRes(R.color.colorAccent);

        return null;
    }

    @Override
    public void setListener() {

    }

}
