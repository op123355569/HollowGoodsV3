package com.hg.hollowgoods.Constant

import android.view.inputmethod.EditorInfo

/**
 * 常量
 * Created by HG on 2018-03-21.
 */
object HGConstants {

    /**
     * 列表布局类型
     */
    @JvmField
    val LIST_ITEM_TYPE_1 = 0
    @JvmField
    val LIST_ITEM_TYPE_2 = 1
    @JvmField
    val LIST_ITEM_TYPE_3 = 2
    @JvmField
    val LIST_ITEM_TYPE_4 = 3
    @JvmField
    val LIST_ITEM_TYPE_5 = 4
    @JvmField
    val LIST_ITEM_TYPE_6 = 5
    @JvmField
    val LIST_ITEM_TYPE_7 = 6
    @JvmField
    val LIST_ITEM_TYPE_8 = 7
    @JvmField
    val LIST_ITEM_TYPE_9 = 8
    @JvmField
    val LIST_ITEM_TYPE_10 = 9
    @JvmField
    val LIST_ITEM_TYPE_11 = 10
    @JvmField
    val LIST_ITEM_TYPE_12 = 11
    @JvmField
    val LIST_ITEM_TYPE_13 = 12
    @JvmField
    val LIST_ITEM_TYPE_14 = 13
    @JvmField
    val LIST_ITEM_TYPE_15 = 14
    @JvmField
    val LIST_ITEM_TYPE_LEFT_MENU = 5001
    @JvmField
    val LIST_ITEM_TYPE_RIGHT_MENU = 5002
    @JvmField
    val LIST_ITEM_TYPE_LEFT_AND_RIGHT_MENU = 5003

    /**
     * 列表布局类型-粘性标签
     */
    @JvmField
    val LIST_ITEM_TYPE_HEADER = 9999

    /**** 通用键值1 ****/
    @JvmField
    val PARAM_KEY_1 = "param.key.1"
    /**** 通用键值2 ****/
    @JvmField
    val PARAM_KEY_2 = "param.key.2"
    /**** 通用键值3 ****/
    @JvmField
    val PARAM_KEY_3 = "param.key.3"
    /**** 通用键值4 ****/
    @JvmField
    val PARAM_KEY_4 = "param.key.4"
    /**** 通用键值5 ****/
    @JvmField
    val PARAM_KEY_5 = "param.key.5"
    /**** 通用键值6 ****/
    @JvmField
    val PARAM_KEY_6 = "param.key.6"
    /**** 通用键值7 ****/
    @JvmField
    val PARAM_KEY_7 = "param.key.7"
    /**** 通用键值8 ****/
    @JvmField
    val PARAM_KEY_8 = "param.key.8"

    /**** 基本对话框相关 ****/
    /**
     * 默认请求码
     */
    @JvmField
    val DEFAULT_CODE = -1

    /**** 输入对话框相关 ****/
    /**
     * 输入类型——默认(任意字符)
     */
    @JvmField
    val INPUT_TYPE_DEFAULT = -1
    /**
     * 输入类型——整数
     */
    @JvmField
    val INPUT_TYPE_NUMBER = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_SIGNED
    /**
     * 输入类型——小数
     */
    @JvmField
    val INPUT_TYPE_FLOAT = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
    /**
     * 输入类型——文字密码
     */
    @JvmField
    val INPUT_TYPE_PASSWORD_TEXT = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
    /**
     * 输入类型——数字密码
     */
    @JvmField
    val INPUT_TYPE_PASSWORD_NUMBER = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD
    /**
     * 输入字数限制——无限制
     */
    @JvmField
    val INPUT_SIZE_DEFAULT = -1

    /**** 时间对话框相关 ****/
    /**
     * 默认时间
     */
    @JvmField
    val DEFAULT_TIME = -1L
    /**
     * 返回值的键——小时
     */
    @JvmField
    val VALUE_KEY_HOUR = "value.key.hour"
    /**
     * 返回值的键——分钟
     */
    @JvmField
    val VALUE_KEY_MINUTE = "value.key.minute"

    /**** 日期对话框相关 ****/
    /**
     * 默认年月日
     */
    @JvmField
    val DEFAULT_DATE = -1L

    /**
     * 返回值的键——年
     */
    @JvmField
    val VALUE_KEY_YEAR = "value.key.year"
    /**
     * 返回值的键——月
     */
    @JvmField
    val VALUE_KEY_MONTH = "value.key.month"
    /**
     * 返回值的键——日
     */
    @JvmField
    val VALUE_KEY_DATE = "value.key.date"

    /**** 日期、时间对话框通用 ****/
    /**
     * 返回值的键——时间戳
     */
    @JvmField
    val VALUE_KEY_TIMESTAMPS = "value.key.timestamps"

    /**** 多选对话框 ****/
    /**
     * 多选选对话框——无选择上限
     */
    @JvmField
    val MULTI_CHOICE_NO_MAX = -1

    /**
     * APP版本更新工具类——请求安装未知应用权限
     */
    @JvmField
    val UPDATE_APP_UTILS_REQUEST_CODE_INSTALL = 8888
    /**
     * APP版本更新工具类——检查更新进度
     */
    @JvmField
    val UPDATE_APP_UTILS_CHECK_PROGRESS_DIALOG_CODE = -9999
    /**
     * APP版本更新工具类——下载安装包进度
     */
    @JvmField
    val UPDATE_APP_UTILS_DOWNLOAD_PROGRESS_DIALOG_CODE = -9998

}