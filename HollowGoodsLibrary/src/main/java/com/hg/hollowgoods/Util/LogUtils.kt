package com.hg.hollowgoods.Util

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.hg.hollowgoods.Constant.HGSystemConfig
import com.hg.hollowgoods.R

/**
 * 日志辅助类
 * Created by HG on 2018-03-21.
 */
object LogUtils {

    private var TAG = "";

    @JvmStatic
    fun init(context: Context) {
        TAG = context.getString(R.string.app_name)
    }

    @JvmStatic
    fun LogRequest(url: String?, msg: String?) {
        if (HGSystemConfig.IS_DEBUG_MODEL) {
            Log.e("*请求结果", "***********************************************************************************")
            Log.e("************", "\r" + url)
            Log.e("************", "\r" + msg)
            Log.e("*请求结果", "***********************************************************************************")
        }
    }

    @JvmStatic
    fun LogRequest(msg: String?) {
        LogRequest("", msg)
    }

    @JvmStatic
    fun Log(msg: Any?) {
        if (HGSystemConfig.IS_DEBUG_MODEL) {
            if (TextUtils.isEmpty(TAG)) {
                TAG = "TAG"
            }

            if (msg == null) {
                Log.e(TAG, TAG)
            } else {
                Log.e(TAG, "" + msg)
            }
        }
    }

    @JvmStatic
    fun Log(msg: Any?, tag: String?) {
        if (HGSystemConfig.IS_DEBUG_MODEL) {
            if (msg == null) {
                Log.e(tag, "")
            } else {
                Log.e(tag, "" + msg)
            }
        }
    }

    @JvmStatic
    fun Log(msg: Any?, clazz: Class<*>) {
        if (HGSystemConfig.IS_DEBUG_MODEL) {
            if (msg == null) {
                Log.e(clazz.simpleName, "")
            } else {
                Log.e(clazz.simpleName, "" + msg)
            }
        }
    }

}