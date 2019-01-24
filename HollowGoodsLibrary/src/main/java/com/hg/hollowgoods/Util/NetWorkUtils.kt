package com.hg.hollowgoods.Util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager

/**
 * 网络工具类
 * Created by HG on 2018-03-22.
 */
object NetWorkUtils {

    /**
     * 检测网络是否连接
     *
     * @param context 上下文
     * @return 结果
     */
    @JvmStatic
    fun isNetConnected(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null) {
                return true
            }
        }

        return false
    }

    /**
     * 检测wifi是否连接
     *
     * @param context 上下文
     * @return 结果
     */
    @JvmStatic
    fun isWifiConnected(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return true
            }
        }

        return false
    }

    /**
     * 检测3G是否连接
     *
     * @param context 上下文
     * @return 结果
     */
    @JvmStatic
    fun is3gConnected(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
        }

        return false
    }

    /**
     * 检测GPS是否打开
     *
     * @param context 上下文
     * @return 结果
     */
    @JvmStatic
    fun isGpsEnabled(context: Context): Boolean {

        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val accessibleProviders = lm.getProviders(true)

        for (name in accessibleProviders) {
            if ("gps" == name) {
                return true
            }
        }

        return false
    }

}