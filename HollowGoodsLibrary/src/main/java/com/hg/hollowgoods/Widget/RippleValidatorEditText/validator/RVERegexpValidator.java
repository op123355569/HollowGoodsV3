package com.hg.hollowgoods.Widget.RippleValidatorEditText.validator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hg.hollowgoods.Widget.RippleValidatorEditText.RVEValidatorType;

import java.util.regex.Pattern;

/**
 * Created by Hollow Goods on unknown.
 */
public class RVERegexpValidator extends RVEValidator {

    private Pattern pattern;

    public RVERegexpValidator(@RVEValidatorType.VType int type, @NonNull String errorMessage, @NonNull String regex, @Nullable Object item) {
        super(type, errorMessage, item);
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text) {
        return pattern.matcher(text).matches();
    }

}