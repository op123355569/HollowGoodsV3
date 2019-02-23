package com.hg.hollowgoods.Bean.EventBus;

import android.os.Bundle;

import java.util.HashMap;

public class Event {

    /**
     * 意图
     */
    private int eventActionCode;
    /**
     * 数据
     */
    private Bundle data;
    /**
     * 其他数据(Bundle放不了的)
     */
    private HashMap<String, Object> obj;
    /**
     * 来源类
     */
    private Class<?> fromClass;

    /**
     * 构造方法
     *
     * @param eventActionCode 意图
     */
    public Event(int eventActionCode) {
        this.eventActionCode = eventActionCode;
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

    public Bundle getData() {
        return data;
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
     * @return
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
     * @return
     */
    public Object getObj(String key) {

        if (obj == null) {
            return null;
        }

        return obj.get(key);
    }

    /**
     * 移除其他数据
     *
     * @param key 键
     * @return
     */
    public Event removeObj(String key) {

        if (obj != null) {
            obj.remove(key);
        }

        return this;
    }

}
