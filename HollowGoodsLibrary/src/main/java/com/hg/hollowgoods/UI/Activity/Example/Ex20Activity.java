package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Widget.RotateBar.RotateBar;
import com.hg.hollowgoods.Widget.RotateBar.RotateBarBasic;

import java.util.ArrayList;

/**
 * 数据缓存清理示例界面
 * Created by HG
 */

public class Ex20Activity extends BaseActivity {

    private Button clear;
    private RotateBar rotateBar;

    private ArrayList<String> appCachePath = new ArrayList<String>() {
        {
            add(HGSystemConfig.APP_BASE_PATH);
        }
    };

    private RotateBarBasic bar1;
    private RotateBarBasic bar2;
    private RotateBarBasic bar3;
    private RotateBarBasic bar4;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_20;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        clear = findViewById(R.id.btn_clear);
        rotateBar = findViewById(R.id.rotateBar);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex20);

        bar1 = new RotateBarBasic(0, "缓存");
        bar2 = new RotateBarBasic(0, "缓存");
        bar3 = new RotateBarBasic(0, "缓存");
        bar4 = new RotateBarBasic(0, "缓存");
        rotateBar.addRatingBar(bar1);
        rotateBar.addRatingBar(bar2);
        rotateBar.addRatingBar(bar3);
        rotateBar.addRatingBar(bar4);

        initRotateBar();

        return null;
    }

    @Override
    public void setListener() {

        clear.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                FileUtils.deleteFile(appCachePath);
                initRotateBar();
            }
        });

        rotateBar.setAnimatorListener(new RotateBar.AnimatorListener() {
            @Override
            public void onRotateStart() {

            }

            @Override
            public void onRotateEnd() {
                clear.setVisibility(View.VISIBLE);
                refreshCacheSize();
            }

            @Override
            public void onRatingStart() {

            }

            @Override
            public void onRatingEnd() {

            }
        });
    }

    public void initRotateBar() {

        clear.setVisibility(View.INVISIBLE);

        rotateBar.setCenterTextColor(ContextCompat.getColor(this, R.color.txt_color_dark));
        rotateBar.clear();

        bar1.setRatedColor(getResources().getColor(R.color.google_red));
        bar1.setOutlineColor(getResources().getColor(R.color.google_red));
        bar1.setRatingBarColor(FormatUtils.changeColorAlpha(getResources().getColor(R.color.google_red), 130));
        bar1.setRate(10);

        bar2.setRatedColor(getResources().getColor(R.color.google_yellow));
        bar2.setOutlineColor(getResources().getColor(R.color.google_yellow));
        bar2.setRatingBarColor(FormatUtils.changeColorAlpha(getResources().getColor(R.color.google_yellow), 130));
        bar2.setRate(10);

        bar3.setRatedColor(getResources().getColor(R.color.google_blue));
        bar3.setOutlineColor(getResources().getColor(R.color.google_blue));
        bar3.setRatingBarColor(FormatUtils.changeColorAlpha(getResources().getColor(R.color.google_blue), 130));
        bar3.setRate(10);

        bar4.setRatedColor(getResources().getColor(R.color.google_green));
        bar4.setOutlineColor(getResources().getColor(R.color.google_green));
        bar4.setRatingBarColor(FormatUtils.changeColorAlpha(getResources().getColor(R.color.google_green), 130));
        bar4.setRate(10);

        rotateBar.show();
    }

    private void refreshCacheSize() {
        FileUtils.allSize = 0l;
        FileUtils.getFileAllSize(appCachePath);
        rotateBar.setCenterText(FileUtils.renderSize(FileUtils.allSize));
    }

}
