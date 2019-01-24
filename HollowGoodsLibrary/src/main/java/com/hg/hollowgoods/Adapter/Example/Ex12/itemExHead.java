package com.hg.hollowgoods.Adapter.Example.Ex12;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;

/**
 * Created by HG on 2016-12-30.
 */

public class itemExHead implements ItemViewDelegate<CommonBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_ex_12_title;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == Constants.LIST_ITEM_TYPE_HEADER;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

    }
}
