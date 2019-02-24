package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hg.hollowgoods.Adapter.Example.Ex38Adapter;
import com.hg.hollowgoods.Bean.Example.Ex38;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.Util.RecyclerViewAnim.adapters.ScaleInAnimationAdapter;
import com.hg.hollowgoods.Util.RecyclerViewAnim.animators.LandingAnimator;

import java.util.ArrayList;

/**
 * @ClassName:列表菜单特效示例
 * @Description:
 * @author: HollowGoods
 * @date: 2019年2月22日
 */
public class Ex38Activity extends BaseActivity {

    private RecyclerView result;

    private Ex38Adapter adapter;
    private ArrayList<Ex38> data = new ArrayList<>();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_38;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_ex37);
        baseUI.setCommonRightTitleText("添加");

        result = findViewById(R.id.rv_result);
        result.setHasFixedSize(true);
        result.setItemAnimator(new LandingAnimator());
        result.setLayoutManager(new LinearLayoutManager(baseUI.getBaseContext()));

        adapter = new Ex38Adapter(baseUI.getBaseContext(), R.layout.item_ex_38, data);
        result.setAdapter(new ScaleInAnimationAdapter(adapter));

        return null;
    }

    @Override
    public void setListener() {
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                data.remove(position);
                adapter.removeDatas(data, position, 1);
            }
        });
    }

    @Override
    public void onRightTitleClick(View view, int id) {
        data.add(new Ex38());
        adapter.addDatas(data, data.size() - 1, 1);
    }
}
