package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Widget.RunTextView.View.RunTextView;
import com.hg.hollowgoods.Widget.RunTextView.View.RunTextViewVertical;
import com.hg.hollowgoods.Widget.RunTextView.View.RunTextViewVerticalMore;

import java.util.ArrayList;

/**
 * 跑马灯文字控件示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex26Activity extends BaseActivity {

    private RunTextView runTextView1;
    private RunTextViewVertical runTextView2;
    private RunTextViewVerticalMore runTextView3;

    private ArrayList<String> runData2 = new ArrayList<>();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_26;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        runTextView1 = findViewById(R.id.runTextView1);
        runTextView2 = findViewById(R.id.runTextView2);
        runTextView3 = findViewById(R.id.runTextView3);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex26);

        initRunTextView2();
        initRunTextView3();

        return null;
    }

    @Override
    public void setListener() {

    }

    private void initRunTextView2() {

        runData2.add("你是天上最受宠的一架钢琴");
        runData2.add("我是丑人脸上的鼻涕");
        runData2.add("你发出完美的声音");
        runData2.add("我被默默揩去");
        runData2.add("你冷酷外表下藏着诗情画意");
        runData2.add("我已经够胖还吃东西");
        runData2.add("你踏着七彩祥云离去");
        runData2.add("我被留在这里");

        runTextView2.setTextList(runData2);
        runTextView2.setText(26, 48, 0xff766156);//设置属性
        runTextView2.setTextStillTime(3000);//设置停留时长间隔
        runTextView2.setAnimTime(300);//设置进入和退出的时间间隔
        runTextView2.setOnItemClickListener(new RunTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    private void initRunTextView3() {

        ArrayList<View> views = new ArrayList<>();
        TextView textView;

        for (int i = 0; i < 20; i++) {
            textView = new TextView(this);
            textView.setText("" + i + i + i + i + i + i + i + i + i + i + i + i + i + i);
            textView.setHeight(80);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(16);
            views.add(textView);
        }

        runTextView3.setViews(views);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runTextView2.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        runTextView2.stopAutoScroll();
    }


}
