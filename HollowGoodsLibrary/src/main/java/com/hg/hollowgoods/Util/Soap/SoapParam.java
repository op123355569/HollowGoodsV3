package com.hg.hollowgoods.Util.Soap;

/**
 * 网络请求参数
 * Created by Hollow Goods on unknown.
 */

public class SoapParam {

    private String key;
    private Object value;

    public SoapParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

}
