package com.hg.hollowgoods.Widget.ValidatorInput.Validator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;

import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.RegexpValidator;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;

import java.util.ArrayList;

public class ValidatorFactory {

    public static Validator getValidator(@ValidatorType.VType int type, String error, @Nullable Object item) {
        switch (type) {
            default:
            case ValidatorType.EMPTY:
                return EmptyChecker(type, error, item);
            case ValidatorType.EMAIL:
                return EmailChecker(type, error, item);
            case ValidatorType.EQUAL:
                return EqualChecker(type, (String) item, error, item);
            case ValidatorType.BEGIN:
                return BeginChecker(type, error, (String) item);
            case ValidatorType.END:
                return EndChecker(type, error, (String) item);
            case ValidatorType.PHONE:
                return PhoneNumberChecker(type, error, item);
            case ValidatorType.MIN_LENGTH:
                return MinLengthChecker(type, error, (int) item);
            case ValidatorType.MAX_LENGTH:
                return MaxLengthChecker(type, error, (int) item);
            case ValidatorType.MIN_VALUE:
                return MinValueChecker(type, error, Double.valueOf(item.toString()));
            case ValidatorType.MAX_VALUE:
                return MaxValueChecker(type, error, Double.valueOf(item.toString()));
            case ValidatorType.CONTAINS:
                return ContainsChecker(type, error, (String) item);
        }
    }

    private static Validator EmptyChecker(@ValidatorType.VType int type, final String errTxt, @Nullable Object item) {
        return new Validator(type, errTxt, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                if (text.toString().trim().equals(""))
                    return false;
                return true;
            }
        };
    }

    private static Validator EqualChecker(@ValidatorType.VType int type, final String equalString, final String errTxt, @Nullable Object item) {
        return new Validator(type, errTxt, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return equalString.equals(text.toString());
            }
        };
    }

    private static Validator MinLengthChecker(@ValidatorType.VType int type, final String errTxt, final int minLength) {
        return new Validator(type, errTxt, minLength) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return !(text.length() < minLength);
            }
        };
    }

    private static Validator MaxLengthChecker(@ValidatorType.VType int type, final String errTxt, final int maxLength) {
        return new Validator(type, errTxt, maxLength) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return !(text.length() > maxLength);
            }
        };
    }

    private static Validator PhoneNumberChecker(@ValidatorType.VType int type, String error, @Nullable Object item) {
        return new Validator(type, error, item) {
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

    private static Validator BeginChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
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

    private static Validator EndChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
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

    private static Validator ContainsChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
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

    private static Validator EmailChecker(@ValidatorType.VType int type, final String error, @Nullable Object item) {
        return new RegexpValidator(type, error, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", item);
    }

    private static Validator MinValueChecker(@ValidatorType.VType int type, final String errTxt, final double minValue) {
        return new Validator(type, errTxt, minValue) {
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

    private static Validator MaxValueChecker(@ValidatorType.VType int type, final String errTxt, final double maxValue) {
        return new Validator(type, errTxt, maxValue) {
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
