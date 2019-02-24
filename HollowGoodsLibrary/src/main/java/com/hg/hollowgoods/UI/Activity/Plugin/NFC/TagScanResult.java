package com.hg.hollowgoods.UI.Activity.Plugin.NFC;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @ClassName:标签扫描结果封装类
 * @Description:
 * @author: HollowGoods
 * @date: 2018年12月11日
 */
public class TagScanResult implements Serializable {

    private String hexResult;
    private String hexReversedResult;
    private String decResult;
    private String decReversedResult;

    public TagScanResult(String hexResult, String hexReversedResult, String decResult, String decReversedResult) {
        this.hexResult = hexResult;
        this.hexReversedResult = hexReversedResult;
        this.decResult = decResult;
        this.decReversedResult = decReversedResult;
    }

    public String getHexResult() {
        return hexResult;
    }

    public void setHexResult(String hexResult) {
        this.hexResult = hexResult;
    }

    public String getHexReversedResult() {
        return hexReversedResult;
    }

    public void setHexReversedResult(String hexReversedResult) {
        this.hexReversedResult = hexReversedResult;
    }

    public String getDecResult() {
        return decResult;
    }

    public void setDecResult(String decResult) {
        this.decResult = decResult;
    }

    public String getDecReversedResult() {
        return decReversedResult;
    }

    public void setDecReversedResult(String decReversedResult) {
        this.decReversedResult = decReversedResult;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
