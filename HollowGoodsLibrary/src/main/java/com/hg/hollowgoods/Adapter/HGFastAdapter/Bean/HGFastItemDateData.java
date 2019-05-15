package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Util.StringUtils;

/**
 * 日期模板封装类
 * Created by Hollow Goods on 2019-05-10.
 */
public class HGFastItemDateData extends HGFastItemWordData {

    private StringUtils.DateFormatMode dateFormatMode;

    public HGFastItemDateData() {
        super(ItemType.ItemDate.getValue());
    }

    public StringUtils.DateFormatMode getDateFormatMode() {
        return dateFormatMode;
    }

    public void setDateFormatMode(StringUtils.DateFormatMode dateFormatMode) {
        this.dateFormatMode = dateFormatMode;
    }
}
