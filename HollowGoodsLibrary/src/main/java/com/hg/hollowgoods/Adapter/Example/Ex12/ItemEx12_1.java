package com.hg.hollowgoods.Adapter.Example.Ex12;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.Example.Ex12;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Widget.AlignTextView;

/**
 * Created by HG
 */

public class ItemEx12_1 implements ItemViewDelegate<CommonBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_ex_12_1;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == HGConstants.LIST_ITEM_TYPE_1;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        Ex12 data = item.getData();

        viewHolder.setImageResource(R.id.iv_img, data.getRes());
        ((AlignTextView) viewHolder.getView(R.id.tv_txt)).setAlingText(data.getM1());
    }
}
