package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;

/**
 * 自定义模板封装类
 * Created by Hollow Goods on 2019-05-09.
 */
public class HGFastItemCustomizeData extends BaseHGFastItemData {

    private Class<? extends ItemViewDelegate<CommonBean>> itemClass;

    public HGFastItemCustomizeData() {
        super(-1);
    }

    public Class<? extends ItemViewDelegate<CommonBean>> getItemClass() {
        return itemClass;
    }

    public void setItemClass(Class<? extends ItemViewDelegate<CommonBean>> itemClass) {
        this.itemClass = itemClass;
    }
}
