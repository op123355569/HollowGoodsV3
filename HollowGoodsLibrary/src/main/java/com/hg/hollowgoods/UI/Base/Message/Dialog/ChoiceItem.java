package com.hg.hollowgoods.UI.Base.Message.Dialog;

/**
 * 选项
 * Created by Hollow Goods on 2019-05-21.
 */
public class ChoiceItem {

    private Object item;
    private String describe;
    private boolean isOpen;

    public ChoiceItem(Object item) {
        this.item = item;
    }

    public ChoiceItem(Object item, String describe) {
        this.item = item;
        this.describe = describe;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
