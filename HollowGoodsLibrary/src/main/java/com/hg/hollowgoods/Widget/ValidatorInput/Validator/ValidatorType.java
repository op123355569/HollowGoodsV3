package com.hg.hollowgoods.Widget.ValidatorInput.Validator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ValidatorType {

    /**** 最小长度 ****/
    public final static int MIN_LENGTH = 101;
    /**** 最大长度 ****/
    public final static int MAX_LENGTH = 102;
    /**** 输入值必须大于 MIN_VALUE ****/
    public final static int MIN_VALUE = 103;
    /**** 输入值必须小于 MAX_VALUE ****/
    public final static int MAX_VALUE = 104;
    /**** 非空 ****/
    public final static int EMPTY = 105;
    /**** 以什么开头 ****/
    public final static int BEGIN = 106;
    /**** 包含 ****/
    public final static int CONTAINS = 107;
    /**** 电子邮箱 ****/
    public final static int EMAIL = 108;
    /**** 以什么结尾 ****/
    public final static int END = 109;
    /**** 等于 ****/
    public final static int EQUAL = 110;
    /**** 手机号 ****/
    public final static int PHONE = 111;
    /**** 不是纯数字 数字、英文、符号、中文等 ****/
    public final static int NOT_ALL_NUMBER = 112;
    /**** 输入值必须大于等于 MIN_VALUE ****/
    public final static int MIN_EQUAL_VALUE = 113;
    /**** 输入值必须小于等于 MAX_VALUE ****/
    public final static int MAX_EQUAL_VALUE = 114;
    /**** 英文或数字 ****/
    public final static int ENGLISH_OR_NUMBER = 115;
    /**** 英文 ****/
    public final static int ENGLISH = 116;
    /**** 中文 ****/
    public final static int CHINESE = 117;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            MIN_LENGTH,
            MAX_LENGTH,
            MIN_VALUE,
            MAX_VALUE,
            EMPTY,
            BEGIN,
            CONTAINS,
            EMAIL,
            END,
            EQUAL,
            PHONE,
            NOT_ALL_NUMBER,
            MIN_EQUAL_VALUE,
            MAX_EQUAL_VALUE,
            ENGLISH_OR_NUMBER,
            ENGLISH,
            CHINESE,
    })
    public @interface VType {
    }

}
