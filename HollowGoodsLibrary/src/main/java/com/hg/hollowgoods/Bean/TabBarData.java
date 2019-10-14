package com.hg.hollowgoods.Bean;

import android.support.v4.app.Fragment;

/**
 * TabBar数据
 * Created by Hollow Goods on 2019-07-04.
 */
public class TabBarData {

    /**** 碎片类 ****/
    private Class<? extends Fragment> fragmentClass;
    /**** 标签名 ****/
    private String labelName;
    /**** 激活状态图片资源 ****/
    private Integer activeIconRes;
    /**** 激活状态图片资源类型 ****/
    private String activeIconResType;
    /**** 激活状态图片资源名 ****/
    private String activeIconResName;
    /**** 沉默状态图片资源 ****/
    private Integer inactiveIconRes;
    /**** 沉默状态图片资源类型 ****/
    private String inactiveIconResType;
    /**** 沉默状态图片资源名 ****/
    private String inactiveIconResName;

    public Integer getActiveIconRes() {
        return activeIconRes;
    }

    public void setActiveIconRes(Integer activeIconRes) {
        this.activeIconRes = activeIconRes;
    }

    public Integer getInactiveIconRes() {
        return inactiveIconRes;
    }

    public void setInactiveIconRes(Integer inactiveIconRes) {
        this.inactiveIconRes = inactiveIconRes;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<? extends Fragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getActiveIconResType() {
        return activeIconResType;
    }

    public void setActiveIconResType(String activeIconResType) {
        this.activeIconResType = activeIconResType;
    }

    public String getActiveIconResName() {
        return activeIconResName;
    }

    public void setActiveIconResName(String activeIconResName) {
        this.activeIconResName = activeIconResName;
    }

    public String getInactiveIconResType() {
        return inactiveIconResType;
    }

    public void setInactiveIconResType(String inactiveIconResType) {
        this.inactiveIconResType = inactiveIconResType;
    }

    public String getInactiveIconResName() {
        return inactiveIconResName;
    }

    public void setInactiveIconResName(String inactiveIconResName) {
        this.inactiveIconResName = inactiveIconResName;
    }
}
