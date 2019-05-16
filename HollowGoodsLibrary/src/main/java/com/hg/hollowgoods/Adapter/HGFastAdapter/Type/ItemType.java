package com.hg.hollowgoods.Adapter.HGFastAdapter.Type;

/**
 * 模板类型
 * Created by Hollow Goods on 2019-05-07.
 */
public enum ItemType {

    /**** 文字模板 ****/
    ItemWord(-100),
    /**** 文件模板 ****/
    ItemFile(-101),
    /**** 日期模板 ****/
    ItemDate(-102),
    /**** 数字模板 ****/
    ItemNumber(-103),
    /**** 分组模板 ****/
    ItemGroup(-104),
    //
    ;

    private int value;

    ItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
