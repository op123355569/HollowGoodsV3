package com.hg.hollowgoods.Adapter.FastAdapter.Bean;

import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
import com.hg.hollowgoods.Adapter.FastAdapter.FastAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Util.StringUtils;

/**
 * Created by HG on 2018-06-14.
 */
public class FastItemData extends CommonBean {

    public int leftIconRes;
    public int rightIconRes;
    public String label;
    public int sortNumber;
    public Object content;
    public FastItemMode fastItemMode;
    public int marginTop;
    public boolean visible;
    public String itemsName;

    public boolean isShowLeftIconRes;
    public boolean isNotEmpty;
    public boolean isShowLabel;
    public boolean isNeedContent;

    public int fileMaxCount;

    public boolean isShowNumberPicker;
    public Double numberPickerMax;
    public Double numberPickerMin;
    public Double numberPickerDif;
    public Class<?> numberPickerType;
    public int numberPickerPointCount;

    public boolean isShowSwitchButton;
    public boolean isDate;
    public StringUtils.DateFormatMode dateFormatMode;

    public FastItemData() {
        super(FastAdapter.ITEM_TYPE_ITEM);
        this.isShowLeftIconRes = false;
        this.isNotEmpty = false;
        this.isShowLabel = true;
        this.isNeedContent = true;
        this.fileMaxCount = -1;
        this.isShowNumberPicker = false;
        this.numberPickerMax = null;
        this.numberPickerMin = null;
        this.numberPickerDif = null;
        this.numberPickerType = null;
        this.numberPickerPointCount = 0;
        isShowSwitchButton = false;
        isDate = false;
        setOnlyRead(true);
    }


}
