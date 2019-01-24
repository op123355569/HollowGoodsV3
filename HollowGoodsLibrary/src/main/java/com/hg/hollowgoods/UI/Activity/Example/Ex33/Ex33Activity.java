package com.hg.hollowgoods.UI.Activity.Example.Ex33;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Example.Ex33.gallery.GalleryActivity;
import com.hg.hollowgoods.UI.Activity.Example.Ex33.shop.ShopActivity;
import com.hg.hollowgoods.UI.Activity.Example.Ex33.weather.WeatherActivity;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * 照片墙示例
 * Created by HG on 2018-03-21.
 */

public class Ex33Activity extends BaseActivity {

    private Button ex1;
    private Button ex2;
    private Button ex3;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_33;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        ex1 = findViewById(R.id.btn_1);
        ex2 = findViewById(R.id.btn_2);
        ex3 = findViewById(R.id.btn_3);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_ex33);

        return null;
    }

    @Override
    public void setListener() {

        ex1.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                baseUI.startMyActivity(ShopActivity.class);
            }
        });

        ex2.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                baseUI.startMyActivity(WeatherActivity.class);
            }
        });

        ex3.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                baseUI.startMyActivity(GalleryActivity.class);
            }
        });
    }

}
