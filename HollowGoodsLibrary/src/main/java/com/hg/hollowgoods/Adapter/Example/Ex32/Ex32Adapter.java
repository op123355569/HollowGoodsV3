package com.hg.hollowgoods.Adapter.Example.Ex32;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGConstants;

import java.util.List;

/**
 * 示例32适配器
 * Created by HG on 2018-08-02.
 */
public class Ex32Adapter extends MultiItemTypeAdapter<CommonBean> {

    public Ex32Adapter(Context context, List<CommonBean> data) {
        super(context, data);
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_LEFT_MENU, new ItemEx32Left());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_RIGHT_MENU, new ItemEx32Right());
        addItemViewDelegate(HGConstants.LIST_ITEM_TYPE_LEFT_AND_RIGHT_MENU, new ItemEx32LeftAndRight());
    }

}
