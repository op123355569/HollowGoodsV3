package com.hg.hollowgoods.Util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具类
 * Created by Hollow Goods on unknown.
 */
public class PhoneUtils {

    /**
     * 是否为手机号
     *
     * @param phoneNumber String
     * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNumber) {

        StringBuffer regexMobile = new StringBuffer();
        regexMobile.append("((^");
        regexMobile.append("(13[0-9]{1})");
        regexMobile.append("|(14[0-9]{1})");
        regexMobile.append("|(15[0-9]{1})");
        regexMobile.append("|(16[6]{1})");
        regexMobile.append("|(17[0-9]{1})");
        regexMobile.append("|(18[0-9]{1})");
        regexMobile.append("|(19[89]{1})");
        regexMobile.append(")+\\d{8})$");

        return isMatch(regexMobile.toString(), phoneNumber);
    }

    /**
     * 是否为匹配规则字符串
     *
     * @param regex   String
     * @param orginal String
     * @return boolean
     */
    private static boolean isMatch(String regex, String orginal) {

        if (TextUtils.isEmpty(orginal)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);

        return isNum.matches();
    }

}
