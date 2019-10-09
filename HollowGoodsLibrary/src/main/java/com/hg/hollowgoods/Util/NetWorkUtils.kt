package com.hg.hollowgoods.Util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

/**
 * 网络工具类
 * Created by Hollow Goods 2018-03-22.
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

        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

    /**
     * 当前网络类型是否高于2G
     *
     * @param context context
     * @return boolean
     */
    @JvmStatic
    fun isNetworkStateOver2G(context: Context): Boolean {

        // 获取网络服务
        val connManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // 为空则认为无网络
        if (connManager == null) {
            return false
        }

        // 获取网络类型，如果为空，返回无网络
        val activeNetInfo = connManager.activeNetworkInfo
        if (activeNetInfo == null || !activeNetInfo.isAvailable) {
            return false
        }
        // 判断是否为WIFI
        val wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (null != wifiInfo) {
            val state = wifiInfo.state
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return true
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkType = telephonyManager.networkType
        when (networkType) {
            /*
             GPRS : 2G(2.5) General Packet Radia Service 114kbps
             EDGE : 2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
             UMTS : 3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
             CDMA : 2G 电信 Code Division Multiple Access 码分多址
             EVDO_0 : 3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
             EVDO_A : 3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
             1xRTT : 2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
             HSDPA : 3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
             HSUPA : 3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
             HSPA : 3G (分HSDPA,HSUPA) High Speed Packet Access
             IDEN : 2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
             EVDO_B : 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
             LTE : 4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
             EHRPD : 3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
             HSPAP : 3G HSPAP 比 HSDPA 快些
             */
            // 2G网络
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return false
            // 3G网络
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> return true
            // 4G网络
            TelephonyManager.NETWORK_TYPE_LTE -> return true
            else -> return false
        }
    }

}