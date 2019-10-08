package com.hg.hollowgoods.Adapter.FastAdapter.Constant;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ChoiceItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 变量翻译集合项
 * 支持String[]、Map、List
 * Created by Hollow Goods 2018-06-13.
 */
public class ParamItem {

    public static final String[] SEX_STR = {
            "女",
            "男"
    };

    public static final Integer[] SEX = {
            R.drawable.ic_female,
            R.drawable.ic_male,
    };

    public static final ArrayList<ChoiceItem> SEX_LIST = new ArrayList<ChoiceItem>() {
        {
            add(new ChoiceItem("女"));
            add(new ChoiceItem("男"));
        }
    };

    public static final HashMap<Boolean, String> MARRY = new HashMap<Boolean, String>() {
        {
            put(true, "已婚");
            put(false, "未婚");
        }
    };

}
