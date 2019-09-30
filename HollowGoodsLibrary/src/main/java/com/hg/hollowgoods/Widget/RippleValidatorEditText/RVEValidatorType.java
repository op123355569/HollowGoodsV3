package com.hg.hollowgoods.Widget.RippleValidatorEditText;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Hollow Goods on unknown.
 */
public class RVEValidatorType {

    public final static int EMAIL = 1;
    public final static int EMPTY = 2;
    public final static int EQUAL = 3;
    public final static int MIN_LENGTH = 4;
    public final static int BEGIN = 5;
    public final static int END = 6;
    public final static int PHONE = 7;
    public final static int MAX_LENGTH = 8;
    public final static int MIN_VALUE = 9;
    public final static int MAX_VALUE = 10;
    public final static int CONTAINS = 11;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EMAIL, EMPTY, EQUAL, MIN_LENGTH, BEGIN, END, PHONE, MAX_LENGTH, MIN_VALUE, MAX_VALUE, CONTAINS})
    public @interface VType {
    }
}
