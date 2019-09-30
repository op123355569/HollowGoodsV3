package com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hg.hollowgoods.Widget.ValidatorInput.Validator.ValidatorType;

import java.util.regex.Pattern;

/**
 * 正则验证
 * Created by Hollow Goods on unknown.
 */
public class RegexpValidator extends Validator {

    private Pattern pattern;

    public RegexpValidator(@ValidatorType.VType int type, @NonNull String errorMessage, @NonNull String regex, @Nullable Object item) {
        super(type, errorMessage, item);
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text) {
        return pattern.matcher(text).matches();
    }

}