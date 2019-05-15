package com.hg.hollowgoods.Util

import android.app.Activity
import android.content.Context
import android.text.Selection
import android.text.Spannable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 界面操作工具类
 * Created by HG on 2018-03-21.
 */
object ViewUtils {

    @JvmField
    var TAG = "";

    /**
     * 将EditText的光标移到最后
     */
    @JvmStatic
    fun setEditTextCursorLocation(editText: EditText) {

        val text = editText.text

        if (text is Spannable) {
            val spanText = text as Spannable
            Selection.setSelection(spanText, text.length)
        }
    }

    /**
     * 动态隐藏软键盘
     */
    @JvmStatic
    fun hideSoftInput(v: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0) //强制隐藏键盘
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    @JvmStatic
    fun hideSoftInput(activity: Activity) {

        val view = activity.window.peekDecorView()

        if (view != null) {
            val inputManger = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManger.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 显示软键盘
     */
    @JvmStatic
    fun showSoftInput(v: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
    }

}