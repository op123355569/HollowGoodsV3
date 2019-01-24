package com.hg.hollowgoods.UI.Activity.Example.Ex33.shop;

import android.content.Context;
import android.content.SharedPreferences;

import com.hg.hollowgoods.R;

import java.util.Arrays;
import java.util.List;

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get(Context context) {
        return new Shop(context);
    }

    private SharedPreferences storage;

    private Shop(Context context) {
        storage = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "三玻璃窗户", "￥3,000元", R.drawable.ic_ex_1),
                new Item(2, "巨型工具箱", "￥9,999元", R.drawable.ic_ex_2),
                new Item(3, "聚合型相册", "￥2,999元", R.drawable.ic_ex_6),
                new Item(4, "蜘蛛侠面罩", "￥999,999,999元", R.drawable.ic_ex_8),
                new Item(5, "爱的接待室", "￥666元", R.drawable.ic_ex_9),
                new Item(6, "汉字练习本", "￥3元", R.drawable.ic_ex_10));
    }

    public boolean isRated(int itemId) {
        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }

}
