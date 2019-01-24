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
import com.hg.hollowgoods.Widget.TanTan.TanTanCallback;

import java.util.List;

/**
 * 探探示例界面2
 */
public class Ex13_2Activity extends AppCompatActivity {

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

        CardConfig.initConfig(this);

        //探探上下滑是不能删除的，所以只传入左右即可
        final ItemTouchHelper.Callback callback = new TanTanCallback(mRv, mAdapter, mDatas);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }


}

