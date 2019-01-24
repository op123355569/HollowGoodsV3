package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Bean.ModelSVG;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Widget.Ticker.TickerView;
import com.jaredrummler.android.widget.AnimatedSvgView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * SVG动画控件示例
 * Created by HG on 2018-03-21.
 */

public class Ex24Activity extends BaseActivity {

    private AnimatedSvgView animatedSvgView;
    private TickerView count;
    private TickerView time;

    private int position = 0;
    private int length = ModelSVG.values().length;
    private Timer timer = new Timer();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_24;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        animatedSvgView = findViewById(R.id.animatedSvgView);
        count = findViewById(R.id.tv_count);
        time = findViewById(R.id.tv_time);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_ex24);

        char[] countChars = {
                0,
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                '/',
        };
        count.setCharacterList(countChars);

        char[] timeChars = {
                0,
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                ':',
        };
        time.setCharacterList(timeChars);

        animatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {

                switch (state) {
                    case AnimatedSvgView.STATE_NOT_STARTED:
                        // 动画重新开始或者未开始
                        LogUtils.Log("动画重新开始或者未开始");
                        break;
                    case AnimatedSvgView.STATE_TRACE_STARTED:
                        // 描边开始
                        LogUtils.Log("描边开始");
                        break;
                    case AnimatedSvgView.STATE_FILL_STARTED:
                        // 填充开始
                        LogUtils.Log("填充开始");
                        break;
                    case AnimatedSvgView.STATE_FINISHED:
                        // 动画结束
                        LogUtils.Log("动画结束");
                        break;
                }
            }
        });
        setSvg(ModelSVG.values()[position % length]);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> time.setText(StringUtils.getDateTimeString(System.currentTimeMillis(), StringUtils.DateFormatMode.Time_HMS)));
            }
        }, 0l, 1000l);

        return null;
    }

    @Override
    public void setListener() {
        animatedSvgView.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                position++;
                setSvg(ModelSVG.values()[position % length]);
            }
        });
    }

    private void setSvg(ModelSVG modelSVG) {

        // SVG组成路径
        animatedSvgView.setGlyphStrings(modelSVG.glyphs);
        // SVG每条路径对应的填充颜色
        animatedSvgView.setFillColors(modelSVG.colors);
        // SVG的宽高
        animatedSvgView.setViewportSize(modelSVG.width, modelSVG.height);
        // SVG动画描边的颜色
        animatedSvgView.setTraceResidueColor(0x32000000);
        // SVG动画填充颜色
        animatedSvgView.setTraceColors(modelSVG.colors);
        // 重新构建SVG
        animatedSvgView.rebuildGlyphData();
        // 开始动画
        animatedSvgView.start();

        count.setText((position % length + 1) + "/" + length, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
