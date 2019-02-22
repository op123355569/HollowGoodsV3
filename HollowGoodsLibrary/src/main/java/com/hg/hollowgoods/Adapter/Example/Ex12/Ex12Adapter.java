package com.hg.hollowgoods.Adapter.Example.Ex12;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGConstants;

import java.util.List;

/**
 * Created by HG
 */

public class Ex12Adapter extends MultiItemTypeAdapter<CommonBean> {

    public Ex12Adapter(Context context, List<CommonBean> datas) {
        super(context, datas);
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_1, new ItemEx12_1());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_2, new ItemEx12_2());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_HEADER, new itemExHead());
    }

    public void refreshData(List<CommonBean> datas) {
        super.mDatas = datas;
        notifyDataSetChanged();
    }

}
