package com.hg.hollowgoods.Bean.EventBus;

import android.os.Bundle;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Event {

    /**** 意图 ****/
    private int eventActionCode;
    /**** Bundle数据 ****/
    private Bundle data;
    /**** 其他数据(Bundle放不了的) ****/
    private HashMap<String, Object> obj;
    /**** 来源类 ****/
    private Class<?> fromClass;

    /**
     * 构造方法
     *
     * @param eventActionCode 意图
     */
    public Event(int eventActionCode) {

        this.eventActionCode = eventActionCode;

        if (data == null) {
            this.data = new Bundle();
        }
    }

    /**
     * 构造方法
     *
     * @param eventActionCode 意图
     * @param data            数据
     */
    public Event(int eventActionCode, Bundle data) {

        this.eventActionCode = eventActionCode;

        if (data == null) {
            this.data = new Bundle();
        } else {
            this.data = data;
        }
    }

    public int getEventActionCode() {
        return eventActionCode;
    }

    public void setEventActionCode(int eventActionCode) {
        this.eventActionCode = eventActionCode;
    }

    /**
     * 获取Bundle数据
     *
     * @return Bundle
     */
    @Deprecated
    public Bundle getData() {
        return data;
    }

    /**
     * 获取Bundle数据
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <T>          根据接收类型定义返回类型
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(String key, T defaultValue) {
        Object o = data.get(key);
        return o == null ? defaultValue : (T) o;
    }

    /**
     * 获取Bundle数据
     *
     * @param key  键
     * @param type 数据类型
     * @param <T>  根据接收类型定义返回类型
     * @return <T>
     */
    public <T> T getData(String key, Type type) {
        return new Gson().fromJson(new Gson().toJson(data.get(key)), type);
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public Class<?> getFromClass() {
        return fromClass;
    }

    public void setFromClass(Class<?> fromClass) {
        this.fromClass = fromClass;
    }

    /**
     * 添加其他数据
     *
     * @param key   键
     * @param value 值
     * @return Event
     */
    public Event addObj(String key, Object value) {

        if (obj == null) {
            obj = new HashMap<>();
        }
        obj.put(key, value);

        return this;
    }

    /**
     * 获取其他数据
     *
     * @param key 键
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
     *
     * @param key 键
     * @return Event
     */
    public Event removeObj(String key) {

        if (obj != null) {
            obj.remove(key);
        }

        return this;
    }

}
