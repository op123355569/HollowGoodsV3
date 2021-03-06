package com.hg.hollowgoods.Bean.CommonBean;

import java.io.Serializable;

/**
 * Created by Hollow Goods 2018-07-18.
 */
public class KeyValue implements Serializable {

    private String key;
    private Object value;

    public KeyValue() {
    }

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
