package com.hg.hollowgoods.Adapter.Example;

import android.content.Context;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Bean.Example.Ex38;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.AnimMenuLayout.AnimMenuLayout;

import java.util.List;

public class Ex38Adapter extends CommonAdapter<Ex38> {

    public Ex38Adapter(Context context, int layoutId, List<Ex38> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Ex38 item, int position) {

        AnimMenuLayout animMenuLayout = viewHolder.getView(R.id.animMenuLayout);

        if (item.isOpen()) {
            animMenuLayout.openMenu(false);
        } else {
            animMenuLayout.closeMenu(false);
        }

        viewHolder.setOnClickListener(R.id.iv_bgImg, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                animMenuLayout.openMenu(true);
                mData.get(position).setOpen(true);
                refreshData(mData, position);
            }
        });

        viewHolder.setOnClickListener(R.id.ll_menu, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                animMenuLayout.closeMenu(true);
                mData.get(position).setOpen(false);
                refreshData(mData, position);
            }
        });

        viewHolder.setOnClickListener(R.id.ll_item1, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                t.info("测试1");
            }
        });

        viewHolder.setOnClickListener(R.id.ll_item2, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                t.info("测试2");
            }
        });
    }

}
