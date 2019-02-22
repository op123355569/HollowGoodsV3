package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Widget.SlideView;

/**
 * 字母索引界面
 * Created by HG
 */

public class Ex11Activity extends BaseActivity {

    private SlideView slideView;
    private TextView flag;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_11;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        slideView = findViewById(R.id.slideView);
        flag = findViewById(R.id.tv_flag);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex11);

        return null;
    }

    @Override
    public void setListener() {
        slideView.setSlideViewListener(character -> {

            flag.setText(character);
            if (TextUtils.isEmpty(character)) {
                flag.setVisibility(View.GONE);
            } else {
                flag.setVisibility(View.VISIBLE);
            }
        });
    }

}
