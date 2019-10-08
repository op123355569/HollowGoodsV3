package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hg.hollowgoods.Adapter.Example.Ex32.Ex32Adapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import java.util.ArrayList;

/**
 * 列表侧滑菜单示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex32Activity extends BaseActivity {

    private RecyclerView result;

    private Ex32Adapter adapter;
    private ArrayList<CommonBean> data = new ArrayList<>();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_32;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        result = findViewById(R.id.rv_result);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex32);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this));

        initMenuData();

        adapter = new Ex32Adapter(this, data);
        result.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                t.info("Item on click");
            }
        });
    }

    private void initMenuData() {

        for (int i = 0; i < 120; i++) {
            switch (i % 3) {
                case 0:
                    data.add(new CommonBean(HGConstants.LIST_ITEM_TYPE_LEFT_MENU));
                    break;
                case 1:
                    data.add(new CommonBean(HGConstants.LIST_ITEM_TYPE_RIGHT_MENU));
                    break;
                case 2:
                    data.add(new CommonBean(HGConstants.LIST_ITEM_TYPE_LEFT_AND_RIGHT_MENU));
                    break;
            }
        }
    }

}
