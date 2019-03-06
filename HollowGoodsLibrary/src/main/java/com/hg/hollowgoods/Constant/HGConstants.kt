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

    /**** 日期、时间对话框相关 ****/
    /**
     * 默认时间
     */
    @JvmField
    val DEFAULT_TIME = -1L

    /**** 多选对话框 ****/
    /**
     * 多选选对话框——无选择上限
     */
    @JvmField
    var MULTI_CHOICE_NO_MAX = -1

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

    /**
     * 图片加载类型——快速适配器列表类型小图
     */
    @JvmField
    val IMG_LOAD_TYPE_FAST_ADAPTER_ITEM_SMALL = -1000
    /**
     * 图片加载类型——图片预览插件小图
     */
    @JvmField
    val IMG_LOAD_TYPE_IMAGE_PRE_ACTIVITY_SMALL = -1001

}