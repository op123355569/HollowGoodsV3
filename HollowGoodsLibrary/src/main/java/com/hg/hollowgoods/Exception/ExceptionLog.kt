package com.hg.hollowgoods.Exception

import java.io.Serializable

/**
 * 报错收集类
 * Created by Hollow Goods 2018-03-22.
 */
class ExceptionLog : Serializable {

    /**
     * 用户名
     */
    var username = ""
    /**
     * app名称
     */
    var appName = ""
    /**
     * app版本名
     */
    var appVersionName = ""
    /**
     * app版本号
     */
    var appVersionCode = 0
    /**
     * 报错界面名称
     */
    var activityName = ""
    /**
     * 设备信息
     */
    var deviceList: List<String> = arrayListOf()
    /**
     * 发生时间
     */
    var time = 0L
    /**
     * 提交状态 true：已提交，false：未提交
     */
    var uploadStatus = false
    /**
     * 异常信息
     */
    var errorList: List<String> = arrayListOf()
    /**
     * 出现错误
     */
    var errorMessage = ""
    /**
     * 出错原因
     */
    var errorReason = ""
    /**
     * 捕获异常
     */
    var errorAll = ""

}