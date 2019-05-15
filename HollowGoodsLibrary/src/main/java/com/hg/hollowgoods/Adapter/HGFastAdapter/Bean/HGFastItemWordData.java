package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.SingleChoiceMode;

import org.xutils.http.HttpMethod;

import java.lang.reflect.Type;

/**
 * 文字模板封装类
 * Created by Hollow Goods on 2019-05-07.
 */
public class HGFastItemWordData extends BaseHGFastItemData {

    private String contentHint;
    private String contentTextColorResName;
    private int contentTextColorRes;

    private String content;
    private SingleChoiceMode singleChoiceMode;
    private String singleChoiceName;
    private Class<?> singleChoiceNameClass;
    private Object singleChoiceItem;

    private HttpMethod httpMethod;
    private String singleChoiceNetRequestParamName;
    private String singleChoiceNetDataKeyName;
    private String singleChoiceNetDataValueName;
    private Object singleChoiceNetRequestParam;
    private String singleChoiceNetDataTypeName;
    private Type singleChoiceNetDataType;

    public HGFastItemWordData() {
        super(ItemType.ItemWord.getValue());
    }

    HGFastItemWordData(int itemType) {
        super(itemType);
    }

    public String getContentHint() {
        return contentHint;
    }

    public void setContentHint(String contentHint) {
        this.contentHint = contentHint;
    }

    public String getContentTextColorResName() {
        return contentTextColorResName;
    }

    public void setContentTextColorResName(String contentTextColorResName) {
        this.contentTextColorResName = contentTextColorResName;
    }

    public int getContentTextColorRes() {
        return contentTextColorRes;
    }

    public void setContentTextColorRes(int contentTextColorRes) {
        this.contentTextColorRes = contentTextColorRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SingleChoiceMode getSingleChoiceMode() {
        return singleChoiceMode;
    }

    public void setSingleChoiceMode(SingleChoiceMode singleChoiceMode) {
        this.singleChoiceMode = singleChoiceMode;
    }

    public String getSingleChoiceName() {
        return singleChoiceName;
    }

    public void setSingleChoiceName(String singleChoiceName) {
        this.singleChoiceName = singleChoiceName;
    }

    public Class<?> getSingleChoiceNameClass() {
        return singleChoiceNameClass;
    }

    public void setSingleChoiceNameClass(Class<?> singleChoiceNameClass) {
        this.singleChoiceNameClass = singleChoiceNameClass;
    }

    public Object getSingleChoiceItem() {
        return singleChoiceItem;
    }

    public void setSingleChoiceItem(Object singleChoiceItem) {
        this.singleChoiceItem = singleChoiceItem;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getSingleChoiceNetRequestParamName() {
        return singleChoiceNetRequestParamName;
    }

    public void setSingleChoiceNetRequestParamName(String singleChoiceNetRequestParamName) {
        this.singleChoiceNetRequestParamName = singleChoiceNetRequestParamName;
    }

    public String getSingleChoiceNetDataKeyName() {
        return singleChoiceNetDataKeyName;
    }

    public void setSingleChoiceNetDataKeyName(String singleChoiceNetDataKeyName) {
        this.singleChoiceNetDataKeyName = singleChoiceNetDataKeyName;
    }

    public String getSingleChoiceNetDataValueName() {
        return singleChoiceNetDataValueName;
    }

    public void setSingleChoiceNetDataValueName(String singleChoiceNetDataValueName) {
        this.singleChoiceNetDataValueName = singleChoiceNetDataValueName;
    }

    public Object getSingleChoiceNetRequestParam() {
        return singleChoiceNetRequestParam;
    }

    public void setSingleChoiceNetRequestParam(Object singleChoiceNetRequestParam) {
        this.singleChoiceNetRequestParam = singleChoiceNetRequestParam;
    }

    public String getSingleChoiceNetDataTypeName() {
        return singleChoiceNetDataTypeName;
    }

    public void setSingleChoiceNetDataTypeName(String singleChoiceNetDataTypeName) {
        this.singleChoiceNetDataTypeName = singleChoiceNetDataTypeName;
    }

    public Type getSingleChoiceNetDataType() {
        return singleChoiceNetDataType;
    }

    public void setSingleChoiceNetDataType(Type singleChoiceNetDataType) {
        this.singleChoiceNetDataType = singleChoiceNetDataType;
    }
}
