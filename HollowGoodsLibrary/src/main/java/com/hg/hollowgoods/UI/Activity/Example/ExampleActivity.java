package com.hg.hollowgoods.UI.Activity.Example;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hg.hollowgoods.Adapter.Example.ExampleAdapter;
import com.hg.hollowgoods.Bean.Example.Example;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Example.Ex33.Ex33Activity;
import com.hg.hollowgoods.UI.Activity.Example.Ex35.Ex35Activity;
import com.hg.hollowgoods.UI.Activity.Example.Ex36.UI.Ex36Activity;
import com.hg.hollowgoods.UI.Activity.Plugin.PlayVideoActivity;
import com.hg.hollowgoods.UI.Activity.Plugin.QRCodeScannerActivity;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * 示例索引界面
 * Created by HG
 */
public class ExampleActivity extends BaseActivity {

    private RecyclerView result;

    private ExampleAdapter adapter;
    private ArrayList<Example> data = new ArrayList<>();

    private void initExampleData() {
        data.add(new Example(getString(R.string.title_activity_ex1), Ex1Activity.class, R.drawable.ic_ex_1));// ex1
        data.add(new Example(getString(R.string.title_activity_ex2), Ex2Activity.class, R.drawable.ic_ex_2));// ex2
        data.add(new Example(getString(R.string.title_activity_ex3), Ex3Activity.class, R.drawable.ic_ex_3));// ex3
        data.add(new Example(getString(R.string.title_activity_ex4), Ex4Activity.class, R.drawable.ic_ex_3));// ex4
        data.add(new Example(getString(R.string.title_activity_ex5), Ex5Activity.class, R.drawable.ic_ex_5));// ex5
        data.add(new Example(getString(R.string.title_activity_ex6), Ex6Activity.class, R.drawable.ic_ex_6));// ex6
        data.add(new Example(getString(R.string.title_activity_ex7), Ex7Activity.class, R.drawable.ic_ex_7));// ex7
        data.add(new Example(getString(R.string.title_activity_ex8), Ex8Activity.class, null, null, 2, getString(R.string.app_name), R.drawable.ic_ex_8));// ex8
        data.add(new Example(getString(R.string.title_activity_ex9), Ex9Activity.class, R.drawable.ic_ex_9));// ex9
        data.add(new Example(getString(R.string.title_activity_ex10), Ex10Activity.class, R.drawable.ic_ex_10));// ex10
        data.add(new Example(getString(R.string.title_activity_ex11), Ex11Activity.class, R.drawable.ic_ex_11));// ex11
        data.add(new Example(getString(R.string.title_activity_ex12), Ex12Activity.class, R.drawable.ic_ex_10));// ex12
        data.add(new Example(getString(R.string.title_activity_ex13), Ex13Activity.class, R.drawable.ic_ex_13));// ex13
        data.add(new Example(getString(R.string.title_activity_ex14), Ex14Activity.class, R.drawable.ic_ex_14));// ex14
        data.add(new Example(getString(R.string.title_activity_ex15), Ex15Activity.class, R.drawable.ic_ex_15));// ex15
        data.add(new Example(getString(R.string.title_activity_ex16), Ex16Activity.class, R.drawable.ic_ex_10));// ex16
        data.add(new Example(getString(R.string.title_activity_ex17), Ex17Activity.class, R.drawable.ic_ex_17));// ex17
        data.add(new Example(getString(R.string.title_activity_ex18), Ex18Activity.class, R.drawable.ic_ex_18));// ex18
        data.add(new Example(getString(R.string.title_activity_ex19), Ex19Activity.class, R.drawable.ic_ex_19));// ex19
        data.add(new Example(getString(R.string.title_activity_ex20), Ex20Activity.class, R.drawable.ic_ex_20));// ex20
        data.add(new Example(getString(R.string.title_activity_ex21), Ex21_1Activity.class, R.drawable.ic_ex_21));// ex21
        data.add(new Example(getString(R.string.title_activity_ex22), Ex22Activity.class, R.drawable.ic_ex_22));// ex22
        data.add(new Example(getString(R.string.title_activity_ex23), QRCodeScannerActivity.class, R.drawable.ic_ex_23));// ex23
        data.add(new Example(getString(R.string.title_activity_ex24), Ex24Activity.class, R.drawable.ic_ex_24));// ex24
        data.add(new Example(getString(R.string.title_activity_ex25), Ex25Activity.class, R.drawable.ic_ex_25));// ex25
        data.add(new Example(getString(R.string.title_activity_ex26), Ex26Activity.class, R.drawable.ic_ex_26));// ex26
        data.add(new Example(getString(R.string.title_activity_ex27), Ex27Activity.class, R.drawable.ic_ex_26));// ex27
        data.add(new Example(getString(R.string.title_activity_ex28), Ex28_1Activity.class, R.drawable.ic_ex_10));// ex28
        data.add(new Example(getString(R.string.title_activity_ex29), Ex29Activity.class, R.drawable.ic_ex_29));// ex29
        data.add(new Example(getString(R.string.title_activity_ex30), Ex30Activity.class, R.drawable.ic_ex_29));// ex30
        data.add(new Example(getString(R.string.title_activity_ex31), PlayVideoActivity.class, R.drawable.ic_ex_31));// ex31
        data.add(new Example(getString(R.string.title_activity_ex32), Ex32Activity.class, R.drawable.ic_ex_10));// ex32
        data.add(new Example(getString(R.string.title_activity_ex33), Ex33Activity.class, R.drawable.ic_ex_6));// ex33
        data.add(new Example(getString(R.string.title_activity_ex34), Ex34Activity.class, R.drawable.ic_ex_7));// ex34
        data.add(new Example(getString(R.string.title_activity_ex35), Ex35Activity.class, R.drawable.ic_ex_35));// ex35
        data.add(new Example(getString(R.string.title_activity_ex36), Ex36Activity.class, R.drawable.ic_ex_35));// ex36
        data.add(new Example(getString(R.string.title_activity_ex37), Ex37Activity.class, R.drawable.ic_ex_24));// ex37
        data.add(new Example(getString(R.string.title_activity_ex38), Ex38Activity.class, R.drawable.ic_ex_10));// ex38
        data.add(new Example(getString(R.string.title_activity_ex39), Ex39Activity.class, R.drawable.ic_ex_24));// ex39
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_example;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        result = findViewById(R.id.rv_result);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_example);

        initExampleData();

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter = new ExampleAdapter(this, R.layout.item_activity_example, data);
        result.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

                if (position == 22) {
                    baseUI.requestPermission(5555, Manifest.permission.CAMERA);
                }

                Example t = data.get(position);

                if (t.getJumpType() == 1) {
                    if (t.getKeys() == null) {
                        baseUI.startMyActivityRipple(t.getJumpClass(), view, HGSystemConfig.ACTIVITY_CHANGE_RES, null);
                    } else {
                        baseUI.startMyActivityRipple(t.getJumpClass(), view, HGSystemConfig.ACTIVITY_CHANGE_RES, t.getKeys(), t.getValues(), null);
                    }
                } else {
                    View v = view.findViewById(R.id.iv_icon);
                    baseUI.startMyActivity(t.getJumpClass(), v, t.getShareKey());
                }
            }
        });
    }

}
