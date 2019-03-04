package com.hg.hollowgoods.Bean.CommonBean

import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

/**
 * 通用基类
 * Created by HG on 2018-03-22.
 */
open class CommonBean(itemType: Int) : Serializable {

    /**
     * 布局类型
     */
    var itemType: Int = 0
    /**
     * 是否只读
     */
    var isOnlyRead: Boolean = false
    /**
     * 项目意图
     */
    var itemActionCode: Int? = -1
    /**
     * 其他数据
     */
    private var obj: HashMap<String, Any>? = null
    /**
     * 可读性
     */
    private var isOnlyReads: HashMap<String, Boolean>? = null
    /**
     * 多媒体附件
     */
    var media: HashMap<Int, ArrayList<Media>> = hashMapOf()

    init {
        this.itemType = itemType
    }

    /**
     * 获取数据 不用强转
     *
     * @param <T>
     * @return
    </T> */
    fun <T : CommonBean> getData(): T {
        return this as T
    }

    /**
     * 添加其他数据
     *
     * @param key
     * @param value
     * @return
     */
    fun addObj(key: String, value: Any): CommonBean {

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
     */
    fun removeObj(key: String): CommonBean {
        obj!!.remove(key)
        return this
    }

    /**
     * 添加字段的可读性
     *
     * @param key
     * @param value
     * @return
     */
    fun addOnlyReadItem(key: String, value: Boolean): CommonBean {

        if (isOnlyReads == null) {
            isOnlyReads = HashMap()
        }
        isOnlyReads!!.put(key, value)

        return this
    }

    /**
     * 获取字段的可读性
     *
     * @param key
     * @return
     */
    fun getOnlyReadItem(key: String): Boolean? {

        return if (isOnlyReads == null) {
            null
        } else isOnlyReads!![key]
    }

    /**
     * 移除字段的可读性
     */
    fun removeOnlyReadItem(key: String): CommonBean {
        isOnlyReads!!.remove(key)
        return this
    }

}