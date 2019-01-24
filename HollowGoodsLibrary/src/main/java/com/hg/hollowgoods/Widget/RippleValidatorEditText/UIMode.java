package com.hg.hollowgoods.Widget.RippleValidatorEditText;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class UIMode {
    final static int NORMAL = 1;
    final static int ERROR = 2;
    final static int COMPLETE = 3;
    final static int TYPING = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NORMAL, ERROR, COMPLETE, TYPING})
    @interface UiType {
    }
}
