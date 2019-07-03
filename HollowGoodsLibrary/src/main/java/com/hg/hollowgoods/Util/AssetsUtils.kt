package com.hg.hollowgoods.Util

import android.content.Context
import android.text.TextUtils
import java.io.*

/**
 * Assets文件读取工具类
 * Created by HG on 2018-03-22.
 */
object AssetsUtils {

    @JvmStatic
    fun getInputStream(path: String, context: Context): InputStream? {

        try {
            return context.assets.open(path)
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * 获取Assets文件中的txt文件内容
     *
     * @param path
     * @param context
     * @return
     */
    @JvmStatic
    fun getString(path: String, context: Context): String {
        return getString(path, context, null)
    }

    /**
     * 获取Assets文件中的txt文件内容
     *
     * @param path
     * @param context
     * @param textCode
     * @return
     */
    @JvmStatic
    fun getString(path: String, context: Context, textCode: String?): String {

        var result = ""

        try {
            var `is` = getInputStream(path, context)

            val code = if (TextUtils.isEmpty(textCode)) {
                getCode(`is`)
            } else {
                textCode
            }
            `is` = getInputStream(path, context)
            val br = BufferedReader(InputStreamReader(`is`!!, code!!))

            var line: String? = br.readLine()
            while (line != null) {
                result = result + line + "\n"
                line = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 获取txt文件内容的编码格式
     *
     * @param is
     * @return
     */
    private fun getCode(`is`: InputStream?): String? {

        try {

            val bin = BufferedInputStream(`is`!!)
            val p: Int

            p = (bin.read() shl 8) + bin.read()

            var code: String;

            when (p) {
                0xefbb -> code = "UTF-8"
                0xfffe -> code = "Unicode"
                0xfeff -> code = "UTF-16BE"
                else -> code = "GBK"
            }

            `is`.close()

            return code
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}