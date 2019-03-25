package com.hg.hollowgoods.Util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper

/**
 * APP信息工具类
 * Created by HG on 2018-03-22.
 */
object APPUtils {

    /**
     * 获取应用程序名称
     */
    @JvmStatic
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    @JvmStatic
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本号
     */
    @JvmStatic
    fun getVersionCode(context: Context): Int {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 获取SDK版本号
     *
     * @return
     */
    @JvmStatic
    fun getAndroidSDKVersion(): Int {

        var version = 0

        try {
            version = Build.VERSION.SDK_INT
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return version
    }

    /**
     * 是否是Debug模式
     */
    @JvmStatic
    fun isDebug(context: Context): Boolean {
        return context.applicationInfo != null && context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }

}