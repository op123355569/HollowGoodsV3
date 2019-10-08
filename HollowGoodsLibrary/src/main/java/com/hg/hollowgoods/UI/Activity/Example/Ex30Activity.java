package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Widget.ENView.ENDownloadView;
import com.hg.hollowgoods.Widget.ENView.ENLoadingView;
import com.hg.hollowgoods.Widget.ENView.ENPlayView;
import com.hg.hollowgoods.Widget.ENView.ENRefreshView;
import com.hg.hollowgoods.Widget.ENView.ENScrollView;
import com.hg.hollowgoods.Widget.ENView.ENSearchView;
import com.hg.hollowgoods.Widget.ENView.ENVolumeView;

/**
 * 动画按钮示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex30Activity extends BaseActivity {

    // 刷新
    private ENRefreshView refreshView;
    private Button btnRefresh;

    // 播放
    private ENPlayView playView;
    private Button btnPause;
    private Button btnPlay;

    // 下载
    private ENDownloadView downloadView;
    private Button btnStart;
    private Button btnReset;

    // 开关
    private ENScrollView scrollView;
    private Button btnSwitch;

    // 音量
    private ENVolumeView volumeView;
    private SeekBar sbVolume;

    // 搜索
    private ENSearchView searchView;
    private Button btnSearch;

    // 加载
    private ENLoadingView loadingView;
    private Button btnShow;
    private Button btnHide;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_30;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        // 刷新
        refreshView = findViewById(R.id.view_refresh);
        btnRefresh = findViewById(R.id.btn_refresh);

        // 播放
        playView = findViewById(R.id.view_play);
        btnPause = findViewById(R.id.btn_pause);
        btnPlay = findViewById(R.id.btn_play);

        // 下载
        downloadView = findViewById(R.id.view_download);
        btnStart = findViewById(R.id.btn_start);
        btnReset = findViewById(R.id.btn_reset);

        // 开关
        scrollView = findViewById(R.id.view_scroll);
        btnSwitch = findViewById(R.id.btn_switch);

        // 音量
        volumeView = findViewById(R.id.view_volume);
        sbVolume = findViewById(R.id.sb_volume);

        // 搜索
        searchView = findViewById(R.id.view_search);
        btnSearch = findViewById(R.id.btn_search);

        // 加载
        loadingView = findViewById(R.id.view_loading);
        btnShow = findViewById(R.id.btn_show);
        btnHide = findViewById(R.id.btn_hide);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex30);

        return null;
    }

    @Override
    public void setListener() {

        // 刷新
        btnRefresh.setOnClickListener(view -> refreshView.startRefresh());

        // 播放
        btnPause.setOnClickListener(view -> playView.pause());
        btnPlay.setOnClickListener(view -> playView.play());

        // 下载
        downloadView.setDownloadConfig(2000, 20, ENDownloadView.DownloadUnit.MB);
        btnStart.setOnClickListener(view -> downloadView.start());
        btnReset.setOnClickListener(view -> downloadView.reset());

        // 开关
        btnSwitch.setOnClickListener(view -> {
            if (scrollView.isSelected()) {
                scrollView.unSelect();
            } else {
                scrollView.select();
            }
        });

        // 音量
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeView.updateVolumeValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 搜索
        btnSearch.setOnClickListener(view -> searchView.start());

        // 加载
        btnShow.setOnClickListener(view -> loadingView.show());
        btnHide.setOnClickListener(view -> loadingView.hide());
    }

}
