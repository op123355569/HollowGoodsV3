package com.hg.hollowgoods.Util

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * 常用单位转换工具类
 * Created by HG on 2018-03-22.
 */
object DensityUtils {

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    @JvmStatic
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    @JvmStatic
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                context.resources.displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    @JvmStatic
    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    @JvmStatic
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 汉字转码，例："世界"→"%E4%B8%96%E7%95%8C"
     *
     * @param text
     * @return
     */
    @JvmStatic
    fun toEncode(text: String): String {

        if (TextUtils.isEmpty(text)) {
            return ""
        }

        try {
            return URLEncoder.encode(text, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            return text
        }
    }

    /**
     * 汉字转码，例："%E4%B8%96%E7%95%8C"→"世界"
     *
     * @param text
     * @return
     */
    @JvmStatic
    fun toDecode(text: String): String {

        if (TextUtils.isEmpty(text)) {
            return ""
        }

        try {
            return URLDecoder.decode(text, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            return text
        }
    }

}