package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * @ClassName:自动动画示例
 * @Description:
 * @author: 马禛
 * @date: 2018年12月10日
 */
public class Ex37Activity extends BaseActivity {

    private View text;
    private View click;

    private boolean flag = true;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_37;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        text = findViewById(R.id.tv_text);
        click = findViewById(R.id.tv_click);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_ex37);

        return null;
    }

    @Override
    public void setListener() {

        click.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {

                flag = !flag;
                text.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

}
