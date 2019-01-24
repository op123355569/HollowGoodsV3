package com.hg.hollowgoods.Bean.EventBus

import android.os.Bundle
import java.util.*

/**
 * EventBus通用传递类
 * Created by HG on 2018-03-22.
 */
class Event {

    /**
     * 构造方法
     *
     * @param eventAction 意图
     */
    constructor(eventAction: EventAction) : this(eventAction, null)

    /**
     * 构造方法
     *
     * @param eventAction 意图
     * @param data        数据
     */
    constructor(eventAction: EventAction, data: Bundle?) {

        this.eventAction = eventAction

        if (data == null) {
            this.data = Bundle()
        } else {
            this.data = data
        }
    }

    /**
     * 意图
     */
    var eventAction: EventAction? = null
    /**
     * 数据
     */
    var data: Bundle? = null
    /**
     * 其他数据(Bundle放不了的)
     */
    private var obj: HashMap<String, Any>? = null
    /**
     * 来源类
     */
    var fromClass: Class<*>? = null

    /**
     * 添加其他数据
     *
     * @param key
     * @param value
     * @return
     */
    fun addObj(key: String, value: Any): Event {

        if (obj == null) {
            obj = HashMap()
        }
        obj!!.put(key, value)

        return this
    }

    /**
     * 获取其他数据
     *
     * @param key
     * @return
     */
    fun getObj(key: String): Any? {
        return if (obj == null) {
            null
        } else obj!![key]
    }

    /**
     * 移除其他数据
     *
     * @param key
     * @return
     */
    fun removeObj(key: String): Event {
        obj!!.remove(key)
        return this
    }

}