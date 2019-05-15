package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;

/**
 * 数字模板封装类
 * Created by Hollow Goods on 2019-05-13.
 */
public class HGFastItemNumberData extends HGFastItemWordData {

    private int minValue;
    private int maxValue;
    private int difValue;

    public HGFastItemNumberData() {
        super(ItemType.ItemNumber.getValue());
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getDifValue() {
        return difValue;
    }

    public void setDifValue(int difValue) {
        this.difValue = difValue;
    }
}
