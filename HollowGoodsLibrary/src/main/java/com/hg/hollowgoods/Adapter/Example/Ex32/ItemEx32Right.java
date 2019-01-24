package com.hg.hollowgoods.Adapter.Example.Ex32;

import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.SwipeMenuRecyclerView.SwipeItemLayout;

/**
 * Created by HG on 2018-08-02.
 */
public class ItemEx32Right implements ItemViewDelegate<CommonBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_ex_32_right;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == Constants.LIST_ITEM_TYPE_RIGHT_MENU;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        SwipeItemLayout swipeLayout = viewHolder.getView(R.id.swipeLayout);
        swipeLayout.setSwipeEnable(true);

        viewHolder.setOnClickListener(R.id.rightMenu, new OnViewClickListener(false) {

            @Override
            public void onViewClick(View view, int id) {
                t.info("Right menu click");
                swipeLayout.close();
            }
        });
    }

}
