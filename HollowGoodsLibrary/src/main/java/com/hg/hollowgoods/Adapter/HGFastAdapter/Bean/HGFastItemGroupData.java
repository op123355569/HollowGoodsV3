package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;

import java.util.ArrayList;

/**
 * 分组模板封装类
 * Created by Hollow Goods on 2019-05-14.
 */
public class HGFastItemGroupData extends BaseHGFastItemData {

    private int[] groupItemIds;
    private boolean isNeedCardView;
    private ArrayList<CommonBean> groupData = new ArrayList<>();

    public HGFastItemGroupData() {
        super(ItemType.ItemGroup.getValue());
    }

    public int[] getGroupItemIds() {
        return groupItemIds;
    }

    public void setGroupItemIds(int[] groupItemIds) {
        this.groupItemIds = groupItemIds;
    }

    public boolean isNeedCardView() {
        return isNeedCardView;
    }

    public void setNeedCardView(boolean needCardView) {
        isNeedCardView = needCardView;
    }

    public ArrayList<CommonBean> getGroupData() {
        return groupData;
    }

    public void setGroupData(ArrayList<CommonBean> groupData) {
        this.groupData = groupData;
    }

}
