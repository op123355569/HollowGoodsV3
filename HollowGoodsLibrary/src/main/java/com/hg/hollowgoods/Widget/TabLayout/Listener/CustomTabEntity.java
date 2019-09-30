package com.hg.hollowgoods.Widget.TabLayout.Listener;

import android.support.annotation.DrawableRes;

/**
 * Created by Hollow Goods on unknown.
 */
public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}