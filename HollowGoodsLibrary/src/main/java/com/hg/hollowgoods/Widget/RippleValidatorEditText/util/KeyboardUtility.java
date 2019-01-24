package com.hg.hollowgoods.Widget.RippleValidatorEditText.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtility {

    public static void showKeyboard(Context ct, View v) {
        if (ct != null && v != null)
            ((InputMethodManager) ct.getSystemService(ct.INPUT_METHOD_SERVICE))
                    .showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(Context ct, EditText v) {
        if (ct != null && v != null) {
            v.requestFocus();
            ((InputMethodManager) ct.getSystemService(ct.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
