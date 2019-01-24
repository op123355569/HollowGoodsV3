package com.hg.hollowgoods.UI.Activity.Example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.hg.hollowgoods.Adapter.Example.Ex13Adapter;
import com.hg.hollowgoods.Bean.Example.Ex13_1;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Widget.TanTan.CardConfig;
import com.hg.hollowgoods.Widget.TanTan.OverLayCardLayoutManager;
import com.hg.hollowgoods.Widget.TanTan.RenRenCallback;

import java.util.List;

/**
 * 探探示例界面1
 */
public class Ex13_1Activity extends AppCompatActivity {

    RecyclerView mRv;
    Ex13Adapter mAdapter;
    List<Ex13_1> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_13_1);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());
        mAdapter = new Ex13Adapter(this, R.layout.item_ex_13_1, mDatas = Ex13_1.initDatas());
        mRv.setAdapter(mAdapter);

        //初始化配置
        CardConfig.initConfig(this);
        ItemTouchHelper.Callback callback = new RenRenCallback(mRv, mAdapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);
    }

}
