package com.hg.hollowgoods.Bean.CommonBean

import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media
import com.hg.hollowgoods.UI.Base.Message.Dialog.ConfigInput
import java.io.Serializable

/**
 * 通用基类
 * Created by HG on 2018-03-22.
 */
open class CommonBean(itemType: Int) : Serializable {

    /**
     * HGFastAdapter用的项id
     */
    var itemId: Int = 0
    /**
     * 布局类型
     */
    var itemType: Int = 0
    /**
     * 是否只读
     * HGFastAdapter用的项可读性
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
     * 可读性
     * HGFastAdapter用的
     */
    private var isItemOnlyReads: HashMap<Int, Boolean>? = null
    /**
     * 多媒体附件
     */
    var media: HashMap<Int, ArrayList<Media>> = hashMapOf()
    /**
     * 多媒体附件
     * HGFastAdapter用的
     */
    var itemMedia: ArrayList<Media>? = null
    /**
     * 输入配置
     * HGFastAdapter用的
     */
    var configInputs: HashMap<Int, ConfigInput> = hashMapOf()
    /**
     * 输入配置
     * HGFastAdapter用的
     */
    var configInput: ConfigInput? = null
    /**
     * 是否需要移除
     */
    var isNeedRemove: Boolean = false

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
     * @param key key
     * @param value value
     * @return CommonBean
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
     * @param key key
     * @return Boolean
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

    /**
     * 添加字段的可读性
     *
     * @param key
     * @param value
     * @return CommonBean
     */
    fun addOnlyReadItem(key: Int, value: Boolean): CommonBean {

        if (isItemOnlyReads == null) {
            isItemOnlyReads = HashMap()
        }
        isItemOnlyReads!![key] = value

        return this
    }

    /**
     * 获取字段的可读性
     *
     * @param key key
     * @return Boolean
     */
    fun getOnlyReadItem(key: Int): Boolean {

        if (isItemOnlyReads == null) {
            return true
        }

        return isItemOnlyReads!![key] ?: return true
    }

    /**
     * 移除字段的可读性
     */
    fun removeOnlyReadItem(key: Int): CommonBean {
        isItemOnlyReads!!.remove(key)
        return this
    }

    /**
     * 添加输入配置
     *
     * @param key key
     * @param value value
     * @return CommonBean
     */
    fun addConfigInput(key: Int, value: ConfigInput): CommonBean {
        configInputs[key] = value
        return this
    }

    /**
     * 获取输入配置
     *
     * @param key key
     * @return ConfigInput
     */
    fun getConfigInput(key: Int): ConfigInput? {
        return configInputs!![key]
    }

    /**
     * 移除输入配置
     * @param key key
     * @return CommonBean
     */
    fun removeConfigInput(key: Int): CommonBean {
        configInputs!!.remove(key)
        return this
    }

}