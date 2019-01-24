package com.hg.hollowgoods.Util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * 正则工具类
 * Created by HG on 2018-03-22.
 */
object RegexUtils {

    /**
     * 正则表达式：验证用户名
     */
    private val REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$"

    /**
     * 正则表达式：验证密码
     */
    private val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$"

    /**
     * 正则表达式：验证手机号
     */
    private val REGEX_MOBILE = "^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})\$"

    /**
     * 正则表达式：验证邮箱
     */
    private val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 正则表达式：验证汉字
     */
    private val REGEX_CHINESE = "^[\u4e00-\u9fa5]{%1\$s}$"

    /**
     * 正则表达式：验证身份证
     */
    private val REGEX_ID_CARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$"

    /**
     * 正则表达式：验证URL
     */
    private val REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"

    /**
     * 正则表达式：验证IP地址
     */
    private val REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"

    /**
     * 是否为匹配规则字符串
     *
     * @param regex
     * @param orginal
     * @return
     */
    private fun isMatch(regex: String, orginal: String?): Boolean {

        if (orginal == null || orginal.trim { it <= ' ' } == "") {
            return false
        }
        val pattern = Pattern.compile(regex)
        val isNum = pattern.matcher(orginal)

        return isNum.matches()
    }

    /**
     * 校验用户名
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isUsername(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_USERNAME, orginal)
    }

    /**
     * 校验密码
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isPassword(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_PASSWORD, orginal)
    }

    /**
     * 校验手机号
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isMobile(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_MOBILE, orginal)
    }

    /**
     * 校验邮箱
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isEmail(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_EMAIL, orginal)
    }

    /**
     * 校验汉字
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isChinese(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(String.format(REGEX_CHINESE, orginal.length), orginal)
    }

    /**
     * 校验身份证
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isIDCard(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_ID_CARD, orginal)
    }

    /**
     * 校验URL
     *
     * @param orginal
     * @return 校验通过返回true，否则返回false
     */
    @JvmStatic
    fun isUrl(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_URL, orginal)
    }

    /**
     * 校验IP地址
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isIPAddr(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch(REGEX_IP_ADDR, orginal)
    }

    /**
     * 是否为正整数(+)
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isPositiveInteger(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("^\\+{0,1}[1-9]\\d*", orginal)
    }

    /**
     * 是否为负整数(-)
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isNegativeInteger(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("^-[1-9]\\d*", orginal)
    }

    /**
     * 是否为整数(+-)
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isWholeNumber(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal)
    }

    /**
     * 是否为正实数(+)，不包括整数
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isPositiveDecimal(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal)
    }

    /**
     * 是否为正实数(-)，不包括整数
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isNegativeDecimal(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal)
    }

    /**
     * 是否为实数，不包括整数
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isDecimal(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal)
    }

    /**
     * 是否为实数，包括整数，可能匹配2次，效率低
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isRealNumber2(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isWholeNumber(orginal) || isDecimal(orginal)
    }

    /**
     * 是否为实数，包括整数，只匹配1次，效率高
     *
     * @param orginal
     * @return
     */
    @JvmStatic
    fun isRealNumber1(orginal: String): Boolean {

        return if (TextUtils.isEmpty(orginal)) {
            false
        } else isMatch("[-+]{0,1}\\d+\\.{0,1}\\d*|[-+]{0,1}\\d*\\.\\d+", orginal)
    }

}