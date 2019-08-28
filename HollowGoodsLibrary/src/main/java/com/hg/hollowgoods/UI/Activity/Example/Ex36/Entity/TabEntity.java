package com.hg.hollowgoods.UI.Activity.Example.Ex36.Entity;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Widget.TabLayout.Listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title) {
        this.title = title;
        this.selectedIcon = R.drawable.ic_android_green_24dp;
        this.unSelectedIcon = R.drawable.ic_android_white_24dp;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
