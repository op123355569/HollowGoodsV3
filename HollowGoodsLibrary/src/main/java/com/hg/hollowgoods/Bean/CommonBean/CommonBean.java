package com.hg.hollowgoods.Bean.CommonBean;

import com.google.gson.Gson;
import com.hg.hollowgoods.Bean.AppFile;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ConfigInput;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hollow Goods on 2019-07-11.
 */
public class CommonBean implements Serializable {

    /**** HGFastAdapter用的项id ****/
    private int itemId = 0;
    /**** 布局类型 ****/
    private int itemType;
    /**** 是否只读 HGFastAdapter用的项可读性 ****/
    private boolean isOnlyRead = false;
    /**** 项目意图 ****/
    private int itemActionCode = -1;
    /**** 其他数据 ****/
    private HashMap<String, Object> obj = null;

    /**** 可读性 ****/
    private HashMap<String, Boolean> isOnlyReads = null;
    /**** 可读性 HGFastAdapter用的 ****/
    private HashMap<Integer, Boolean> isItemOnlyReads = null;

    /**** 多媒体附件 ****/
    private HashMap<Integer, ArrayList<AppFile>> media = new HashMap<>();
    /**** 多媒体附件 HGFastAdapter用的 ****/
    private ArrayList<AppFile> itemMedia = null;

    /**** 输入配置 HGFastAdapter用的 ****/
    private HashMap<Integer, ConfigInput> configInputs = new HashMap<>();
    /**** 输入配置 HGFastAdapter用的 ****/
    private ConfigInput configInput = null;

    /**** 是否需要移除 ****/
    private boolean isNeedRemove = false;

    public CommonBean(int itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isOnlyRead() {
        return isOnlyRead;
    }

    public void setOnlyRead(boolean onlyRead) {
        isOnlyRead = onlyRead;
    }

    public int getItemActionCode() {
        return itemActionCode;
    }

    public void setItemActionCode(int itemActionCode) {
        this.itemActionCode = itemActionCode;
    }

    public ConfigInput getConfigInput() {
        return configInput;
    }

    public void setConfigInput(ConfigInput configInput) {
        this.configInput = configInput;
    }

    public boolean isNeedRemove() {
        return isNeedRemove;
    }

    public void setNeedRemove(boolean needRemove) {
        isNeedRemove = needRemove;
    }

    public HashMap<Integer, ArrayList<AppFile>> getMedia() {
        return media;
    }

    public void setMedia(HashMap<Integer, ArrayList<AppFile>> media) {
        this.media = media;
    }

    public ArrayList<AppFile> getItemMedia() {
        return itemMedia;
    }

    public void setItemMedia(ArrayList<AppFile> itemMedia) {
        this.itemMedia = itemMedia;
    }

    /**
     * 获取数据 不用强转
     *
     * @param <T> 根据接收数据类型定义返回数据类型
     * @return <T>
     */
    public <T extends CommonBean> T getData() {
        return (T) this;
    }

    /**
     * 添加其他数据
     *
     * @param key   String
     * @param value Object
     * @return CommonBean
     */
    public CommonBean addObj(String key, Object value) {

        if (obj == null) {
            obj = new HashMap<>();
        }

        obj.put(key, value);

        return this;
    }

    /**
     * 获取其他数据
     *
     * @param key String
     * @return Object
     */
    @Deprecated
    public Object getObj(String key) {

        if (obj == null) {
            return null;
        }

        return obj.get(key);
    }

    /**
     * 获取其他数据
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <T>          根据接收类型定义返回类型
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getObj(String key, T defaultValue) {

        if (obj == null) {
            return defaultValue;
        }

        Object o = obj.get(key);

        return o == null ? defaultValue : (T) o;
    }

    /**
     * 获取其他数据
     *
     * @param key  键
     * @param type 数据类型
     * @param <T>  根据接收类型定义返回类型
     * @return <T>
     */
    public <T> T getObj(String key, Type type) {

        if (obj == null) {
            return null;
        }

        return new Gson().fromJson(new Gson().toJson(obj.get(key)), type);
    }

    /**
     * 移除其他数据
     */
    public CommonBean removeObj(String key) {

        if (obj != null) {
            obj.remove(key);
        }

        return this;
    }

    /**
     * 添加字段的可读性
     *
     * @param key   key
     * @param value value
     * @return CommonBean
     */
    public CommonBean addOnlyReadItem(String key, Boolean value) {

        if (isOnlyReads == null) {
            isOnlyReads = new HashMap<>();
        }

        isOnlyReads.put(key, value);

        return this;
    }

    /**
     * 获取字段的可读性
     *
     * @param key key
     * @return boolean
     */
    public boolean getOnlyReadItem(String key) {

        if (isOnlyReads == null) {
            return true;
        }

        return isOnlyReads.get(key);
    }

    /**
     * 移除字段的可读性
     */
    public CommonBean removeOnlyReadItem(String key) {

        if (isOnlyReads != null) {
            isOnlyReads.remove(key);
        }

        return this;
    }

    /**
     * 添加字段的可读性
     *
     * @param key   int
     * @param value boolean
     * @return CommonBean
     */
    public CommonBean addOnlyReadItem(int key, boolean value) {

        if (isItemOnlyReads == null) {
            isItemOnlyReads = new HashMap<>();
        }

        isItemOnlyReads.put(key, value);

        return this;
    }

    /**
     * 获取字段的可读性
     *
     * @param key key
     * @return Boolean
     */
    public boolean getOnlyReadItem(int key) {

        if (isItemOnlyReads == null) {
            return true;
        }

        return isItemOnlyReads.get(key);
    }

    /**
     * 移除字段的可读性
     */
    public CommonBean removeOnlyReadItem(int key) {

        if (isItemOnlyReads != null) {
            isItemOnlyReads.remove(key);
        }

        return this;
    }

    /**
     * 添加输入配置
     *
     * @param key   key
     * @param value value
     * @return CommonBean
     */
    public CommonBean addConfigInput(int key, ConfigInput value) {
        configInputs.put(key, value);
        return this;
    }

    /**
     * 获取输入配置
     *
     * @param key key
     * @return ConfigInput
     */
    public ConfigInput getConfigInput(int key) {
        return configInputs.get(key);
    }

    /**
     * 移除输入配置
     *
     * @param key key
     * @return CommonBean
     */
    public CommonBean removeConfigInput(int key) {

        if (configInputs != null) {
            configInputs.remove(key);
        }

        return this;
    }

}
