package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hg.hollowgoods.Adapter.Example.Ex10Adapter;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnAdapterViewItemClickListener;

import java.util.ArrayList;

/**
 * 加载数据ListView示例界面
 * Created by HG
 */

public class Ex10Activity extends BaseActivity implements AbsListView.OnScrollListener {

    private ListView result;

    private Ex10Adapter adapter;
    private ArrayList<String> data = new ArrayList<String>() {
        {
            add("Item 1");
            add("Item 2");
            add("Item 3");
            add("Item 4");
            add("Item 5");
            add("Item 6");
            add("Item 7");
            add("Item 8");
            add("Item 9");
            add("Item 10");
        }
    };
    /**
     * listview 总的高度，
     **/
    private int listHeight;
    /**
     * 当前第一行高度+显示在屏幕上的高度
     **/
    private int samHeight;
    private boolean isLoadMore = false;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_10;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        result = findViewById(R.id.lv_result);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex10);

        baseUI.setDataMode(baseUI.DATA_MODE_LOAD_DATA_CENTER);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                baseUI.setDataMode(baseUI.DATA_MODE_HAS_DATA);

                adapter = new Ex10Adapter(Ex10Activity.this, R.layout.item_ex_10, data);
                result.setAdapter(adapter);
            }
        }, 5 * 1000);

        return null;
    }

    @Override
    public void setListener() {
        result.setOnScrollListener(this);
        result.setOnItemClickListener(new OnAdapterViewItemClickListener(false) {
            @Override
            public void onAdapterViewItemClick(AdapterView<?> parentView, View view, int position, long id) {
                baseUI.showShortSnackbar(data.get(position));
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (listHeight == samHeight && !isLoadMore) {
            isLoadMore = true;
            baseUI.setLoadDataBottomMode(true);
            new Handler().postDelayed(() -> {

                for (int i = 0; i < 10; i++) {
                    data.add("Item " + (data.size() + 1));
                }

                baseUI.setLoadDataBottomMode(false);
                adapter.refreshData(data);
                isLoadMore = false;
            }, 5 * 1000);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // 开始高度，显示在屏幕上的高度，总高度
        listHeight = totalItemCount;
        samHeight = firstVisibleItem + visibleItemCount;
    }
}
