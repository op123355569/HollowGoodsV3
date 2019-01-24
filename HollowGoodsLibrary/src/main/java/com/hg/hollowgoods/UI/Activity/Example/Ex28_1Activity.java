package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Adapter.FastAdapter.FastAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.Example.Ex28;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;

import java.util.ArrayList;

/**
 * 快速适配器示例
 * Created by HG on 2018-03-21.
 */

public class Ex28_1Activity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView result;

    private FastAdapter adapter;
    private ArrayList<CommonBean> data = new ArrayList<>();
    private int clickPosition;
    private int clickSortNumber;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_28_1;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        result = findViewById(R.id.rv_result);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_ex28);

        swipeRefreshLayout.setColorSchemeColors(HGSystemConfig.REFRESH_COLORS);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this));

        Ex28 item;
        for (int i = 0; i < 100; i++) {
            item = new Ex28(FastAdapter.ITEM_TYPE_LIST);
            item.setTitle("我是标题" + (i + 1));
            item.setName("张三" + (i + 1));
            item.setAge(i + 20);
            item.setSex(i % 2);
            item.setHobby("打灰机");
            item.setNeedButton(i % 2 == 0);
            item.setUrl("http://img1.imgtn.bdimg.com/it/u=3180728821,3067358428&fm=27&gp=0.jpg");
            data.add(item);
        }

        adapter = new FastAdapter(this, data, true, false);
        result.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {

        adapter.setOnFastClick(new OnFastClick() {

            @Override
            public void onTakePhotoClick(View view, int position, int sortNumber) {

            }

            @Override
            public void onOpenAlbumClick(View view, int position, int sortNumber) {

            }

            @Override
            public void onSubmitClick(View view, int id) {

            }

            @Override
            public void onOperateClick(View view, int position, int sortNumber) {

            }

            @Override
            public void onFilePreClick(View view, int position, int sortNumber) {

            }

            @Override
            public void onNumberPickerClick(View view, int position, int sortNumber, Object num) {

            }

            @Override
            public void onEditClick(View view, int position) {

            }

            @Override
            public void onDeleteClick(View view, int position) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                baseUI.startMyActivity(Ex28_2Activity.class);
            }
        });
    }

}
