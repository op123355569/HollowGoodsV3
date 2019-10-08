package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Helper.SimpleItemTouchHelperCallback;
import com.hg.hollowgoods.Adapter.Example.Ex16Adapter;
import com.hg.hollowgoods.Bean.Example.Ex16;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import java.util.ArrayList;

/**
 * RecyclerView拖动示例界面
 * Created by Hollow Goods on unknown.
 */

public class Ex16Activity extends BaseActivity {

    private RecyclerView result;

    private ArrayList<Ex16> data;
    private Ex16Adapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_16;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        result = findViewById(R.id.rv_result);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex16);

        result.setLayoutManager(new LinearLayoutManager(this));
        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        data.add(new Ex16("刘一"));
        data.add(new Ex16("牛二"));
        data.add(new Ex16("张三"));
        data.add(new Ex16("李四"));
        data.add(new Ex16("王五"));
        data.add(new Ex16("赵六"));
        data.add(new Ex16("洪七"));
        data.add(new Ex16("胡八"));

        adapter = new Ex16Adapter(this, R.layout.item_ex_16, data);
        result.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(result);

        return null;
    }

    @Override
    public void setListener() {
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                t.showShortToast(data.get(position).getTxt());
            }
        });
        adapter.setOnStartDragListener(viewHolder -> mItemTouchHelper.startDrag(viewHolder), R.id.cv_drag);
    }

}
