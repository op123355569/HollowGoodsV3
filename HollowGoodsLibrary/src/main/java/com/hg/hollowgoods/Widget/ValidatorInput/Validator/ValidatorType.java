package com.hg.hollowgoods.Widget.ValidatorInput.Validator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ValidatorType {

    public final static int MIN_LENGTH = 101;
    public final static int MAX_LENGTH = 102;
    public final static int MIN_VALUE = 103;
    public final static int MAX_VALUE = 104;
    public final static int EMPTY = 105;
    public final static int BEGIN = 106;
    public final static int CONTAINS = 107;
    public final static int EMAIL = 108;
    public final static int END = 109;
    public final static int EQUAL = 110;
    public final static int PHONE = 111;
    public final static int NOT_ALL_NUMBER = 112;

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
    })
    public @interface VType {
    }

}
