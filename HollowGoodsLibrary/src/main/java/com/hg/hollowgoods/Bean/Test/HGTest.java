package com.hg.hollowgoods.Bean.Test;

import java.io.Serializable;

public class HGTest implements Serializable {

    private String btnName;
    private Class<?> jumpClass;
    private String[] keys;
    private Object[] values;

    public HGTest(String btnName, Class<?> jumpClass, String[] keys, Object[] values) {
        this.btnName = btnName;
        this.jumpClass = jumpClass;
        this.keys = keys;
        this.values = values;
    }

    public HGTest(String btnName, Class<?> jumpClass) {
        this.btnName = btnName;
        this.jumpClass = jumpClass;
        this.keys = null;
        this.values = null;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
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

}
