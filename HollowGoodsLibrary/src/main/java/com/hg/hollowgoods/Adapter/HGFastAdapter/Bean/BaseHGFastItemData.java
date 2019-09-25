package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;

/**
 * Created by Hollow Goods on 2019-05-07.
 */
public class BaseHGFastItemData extends CommonBean {

    private int sortNumber;
    private ItemMode itemMode;
    private boolean isNotEmpty;
    private String label;
    private int leftIconRes;
    private String leftIconResType;
    private String leftIconResName;
    private int rightIconRes;
    private String rightIconResType;
    private String rightIconResName;
    private String visibleName;
    private String labelTextColorResName;
    private int labelTextColorRes;
    private int marginTop;
    private int marginLeft;
    private int marginBottom;
    private int marginRight;

    BaseHGFastItemData(int itemType) {
        super(itemType);
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public ItemMode getItemMode() {
        return itemMode;
    }

    public void setItemMode(ItemMode itemMode) {
        this.itemMode = itemMode;
    }

    public boolean isNotEmpty() {
        return isNotEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        isNotEmpty = notEmpty;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLeftIconRes() {
        return leftIconRes;
    }

    public void setLeftIconRes(int leftIconRes) {
        this.leftIconRes = leftIconRes;
    }

    public int getRightIconRes() {
        return rightIconRes;
    }

    public void setRightIconRes(int rightIconRes) {
        this.rightIconRes = rightIconRes;
    }

    public String getLeftIconResType() {
        return leftIconResType;
    }

    public void setLeftIconResType(String leftIconResType) {
        this.leftIconResType = leftIconResType;
    }

    public String getLeftIconResName() {
        return leftIconResName;
    }

    public void setLeftIconResName(String leftIconResName) {
        this.leftIconResName = leftIconResName;
    }

    public String getRightIconResType() {
        return rightIconResType;
    }

    public void setRightIconResType(String rightIconResType) {
        this.rightIconResType = rightIconResType;
    }

    public String getRightIconResName() {
        return rightIconResName;
    }

    public void setRightIconResName(String rightIconResName) {
        this.rightIconResName = rightIconResName;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public String getLabelTextColorResName() {
        return labelTextColorResName;
    }

    public void setLabelTextColorResName(String labelTextColorResName) {
        this.labelTextColorResName = labelTextColorResName;
    }

    public int getLabelTextColorRes() {
        return labelTextColorRes;
    }

    public void setLabelTextColorRes(int labelTextColorRes) {
        this.labelTextColorRes = labelTextColorRes;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }
}
