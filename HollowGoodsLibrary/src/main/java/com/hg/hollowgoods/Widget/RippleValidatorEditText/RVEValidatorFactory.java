package com.hg.hollowgoods.Widget.RippleValidatorEditText;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;

import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Widget.RippleValidatorEditText.validator.RVERegexpValidator;
import com.hg.hollowgoods.Widget.RippleValidatorEditText.validator.RVEValidator;

import java.util.ArrayList;

/**
 * Created by Hollow Goods on unknown.
 */
public class RVEValidatorFactory {

    public static RVEValidator getValidator(@RVEValidatorType.VType int type, String error, @Nullable Object item) {
        switch (type) {
            default:
            case RVEValidatorType.EMPTY:
                return EmptyChecker(type, error, item);
            case RVEValidatorType.EMAIL:
                return EmailChecker(type, error, item);
            case RVEValidatorType.EQUAL:
                return EqualChecker(type, (String) item, error, item);
            case RVEValidatorType.BEGIN:
                return BeginChecker(type, error, (String) item);
            case RVEValidatorType.END:
                return EndChecker(type, error, (String) item);
            case RVEValidatorType.PHONE:
                return PhoneNumberChecker(type, error, item);
            case RVEValidatorType.MIN_LENGTH:
                return MinLengthChecker(type, error, (int) item);
            case RVEValidatorType.MAX_LENGTH:
                return MaxLengthChecker(type, error, (int) item);
            case RVEValidatorType.MIN_VALUE:
                return MinValueChecker(type, error, Double.valueOf(item.toString()));
            case RVEValidatorType.MAX_VALUE:
                return MaxValueChecker(type, error, Double.valueOf(item.toString()));
            case RVEValidatorType.CONTAINS:
                return ContainsChecker(type, error, (String) item);
        }
    }

    private static RVEValidator EmptyChecker(@RVEValidatorType.VType int type, final String errTxt, @Nullable Object item) {
        return new RVEValidator(type, errTxt, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                if (text.toString().trim().equals(""))
                    return false;
                return true;
            }
        };
    }

    private static RVEValidator EqualChecker(@RVEValidatorType.VType int type, final String equalString, final String errTxt, @Nullable Object item) {
        return new RVEValidator(type, errTxt, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return equalString.equals(text.toString());
            }
        };
    }

    private static RVEValidator MinLengthChecker(@RVEValidatorType.VType int type, final String errTxt, final int minLength) {
        return new RVEValidator(type, errTxt, minLength) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return !(text.length() < minLength);
            }
        };
    }

    private static RVEValidator MaxLengthChecker(@RVEValidatorType.VType int type, final String errTxt, final int maxLength) {
        return new RVEValidator(type, errTxt, maxLength) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return !(text.length() > maxLength);
            }
        };
    }

    private static RVEValidator PhoneNumberChecker(@RVEValidatorType.VType int type, String error, @Nullable Object item) {
        return new RVEValidator(type, error, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                Boolean isNumber = Patterns.PHONE.matcher(text).matches();
                if (!isNumber) {
                    return false;
                }
                return true;
            }
        };
    }

    private static RVEValidator BeginChecker(@RVEValidatorType.VType int type, String error, final String fullText) {
        return new RVEValidator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                Boolean beginWith09 = false;

                for (String t : items) {
                    if (text.toString().startsWith(t)) {
                        beginWith09 = true;
                        break;
                    }
                }

                if (!beginWith09) {
                    return false;
                }
                return true;
            }
        };
    }

    private static RVEValidator EndChecker(@RVEValidatorType.VType int type, String error, final String fullText) {
        return new RVEValidator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                Boolean endWith09 = false;

                for (String t : items) {
                    if (text.toString().endsWith(t)) {
                        endWith09 = true;
                        break;
                    }
                }

                if (!endWith09) {
                    return false;
                }
                return true;
            }
        };
    }

    private static RVEValidator ContainsChecker(@RVEValidatorType.VType int type, String error, final String fullText) {
        return new RVEValidator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                Boolean contains09 = false;

                for (String t : items) {
                    if (text.toString().contains(t)) {
                        contains09 = true;
                        break;
                    }
                }

                if (!contains09) {
                    return false;
                }
                return true;
            }
        };
    }

    private static RVEValidator EmailChecker(@RVEValidatorType.VType int type, final String error, @Nullable Object item) {
        return new RVERegexpValidator(type, error, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", item);
    }

    private static RVEValidator MinValueChecker(@RVEValidatorType.VType int type, final String errTxt, final double minValue) {
        return new RVEValidator(type, errTxt, minValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                return !(Double.valueOf(text.toString()) < minValue);
            }
        };
    }

    private static RVEValidator MaxValueChecker(@RVEValidatorType.VType int type, final String errTxt, final double maxValue) {
        return new RVEValidator(type, errTxt, maxValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                return !(Double.valueOf(text.toString()) > maxValue);
            }
        };
    }

}
