package com.hg.hollowgoods.Adapter.Example.Ex12;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGConstants;

import java.util.List;

/**
 * Created by Hollow Goods on unknown.
 */

public class Ex12Adapter extends MultiItemTypeAdapter<CommonBean> {

    public Ex12Adapter(Context context, List<CommonBean> data) {
        super(context, data);
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_1, new ItemEx12_1());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_2, new ItemEx12_2());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_HEADER, new itemExHead());
    }

    public void refreshData(List<CommonBean> data) {
        super.mData = data;
        notifyDataSetChanged();
    }

}
