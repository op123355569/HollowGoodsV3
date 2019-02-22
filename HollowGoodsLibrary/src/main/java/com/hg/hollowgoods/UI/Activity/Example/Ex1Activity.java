package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.PopupWinHelper;
import com.hg.hollowgoods.Widget.WaveDrawable;

/**
 * Snackbar和PopupWindow示例界面
 * Created by HG
 */
public class Ex1Activity extends BaseActivity implements PopupWinHelper.PopupWinOnClickListener {

    private FloatingActionButton fab;
    private ImageView img;
    private View wave;

    private PopupWinHelper helper;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_1;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        fab = findViewById(R.id.fab);
        img = findViewById(R.id.iv_img);
        wave = findViewById(R.id.view_wave);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex1);

        helper = new PopupWinHelper(this, this);
        helper.init(
                R.layout.popupwin_sex,
                Gravity.BOTTOM,
                android.R.style.Animation_Toast,
                "#AA000000",
                new int[]{R.id.tv_male, R.id.tv_female, R.id.tv_cancel},
                new int[]{PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_ON_CLICK},
                false
        );

        WaveDrawable waveDrawable = new WaveDrawable(this, R.mipmap.ic_launcher);
        img.setImageDrawable(waveDrawable);

        // 是否为不确定进度
        waveDrawable.setIndeterminate(false);
        // 进度 0-10000
        waveDrawable.setLevel(6000);
        // 每个波的峰度0-100
        waveDrawable.setWaveAmplitude(5);
        // 每个波的长度0-600
        waveDrawable.setWaveLength(300);
        // 速度0-50
        waveDrawable.setWaveSpeed(1);

        // 背景
        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(Color.YELLOW));
        wave.setBackground(colorWave);

        colorWave.setIndeterminate(true);

        return null;
    }

    @Override
    public void setListener() {
        fab.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                baseUI.showShortSnackbar(R.string.tips, R.string.choose_sex, v -> helper.showPopupwin(v));
            }
        });
    }

    @Override
    public void OnPopupWinClick(View v) {

        helper.closePopupwin();

        if (v.getId() == R.id.tv_male) {
            t.showShortToast(R.string.male);
        } else if (v.getId() == R.id.tv_female) {
            t.showShortToast(R.string.female);
        } else if (v.getId() == R.id.tv_cancel) {

        }
    }

}
