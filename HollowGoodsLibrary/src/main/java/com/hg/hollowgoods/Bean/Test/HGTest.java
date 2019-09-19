package com.hg.hollowgoods.Bean.Test;

import java.io.Serializable;

public class HGTest implements Serializable {

    private String btnName;
    private String jumpClassName;
    private String[] keys;
    private Object[] values;

    public HGTest(String btnName, String jumpClassName, String[] keys, Object[] values) {
        this.btnName = btnName;
        this.jumpClassName = jumpClassName;
        this.keys = keys;
        this.values = values;
    }

    public HGTest(String btnName, String jumpClassName) {
        this.btnName = btnName;
        this.jumpClassName = jumpClassName;
        this.keys = null;
        this.values = null;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getJumpClassName() {
        return jumpClassName;
    }

    public void setJumpClassName(String jumpClassName) {
        this.jumpClassName = jumpClassName;
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

}
