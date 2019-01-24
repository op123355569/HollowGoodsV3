package com.hg.hollowgoods.Bean.Example;

/**
 * Created by HG on 2018-06-06.
 */
public class Example {

    private String label;
    private Class<?> jumpClass;
    private String[] keys;
    private Object[] values;
    private int jumpType = 1;
    private String shareKey;
    private int iconRes;

    public Example(String label, Class<?> jumpClass, String[] keys, Object[] values, int jumpType, String shareKey, int iconRes) {
        this.label = label;
        this.jumpClass = jumpClass;
        this.keys = keys;
        this.values = values;
        this.jumpType = jumpType;
        this.shareKey = shareKey;
        this.iconRes = iconRes;
    }

    public Example(String label, Class<?> jumpClass, String[] keys, Object[] values, int iconRes) {
        this(label, jumpClass, keys, values, 1, null, iconRes);
    }

    public Example(String label, Class<?> jumpClass, int iconRes) {
        this(label, jumpClass, null, null, 1, null, iconRes);
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getJumpClass() {
        return jumpClass;
    }

    public void setJumpClass(Class<?> jumpClass) {
        this.jumpClass = jumpClass;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public int getJumpType() {
        return jumpType;
    }

    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

}
