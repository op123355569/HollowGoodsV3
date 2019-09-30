package com.hg.hollowgoods.Widget.ValidatorInput.Validator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hg.hollowgoods.Util.PhoneUtils;
import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.RegexpValidator;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 验证工厂
 * Created by Hollow Goods on unknown.
 */
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
                return MinValueChecker(type, error, item == null ? new BigDecimal(Integer.MIN_VALUE) : new BigDecimal(item.toString()));
            case ValidatorType.MAX_VALUE:
                return MaxValueChecker(type, error, item == null ? new BigDecimal(Integer.MAX_VALUE) : new BigDecimal(item.toString()));
            case ValidatorType.CONTAINS:
                return ContainsChecker(type, error, (String) item);
            case ValidatorType.NOT_ALL_NUMBER:
                return notAllNumberChecker(type, error, (String) item);
            case ValidatorType.MIN_EQUAL_VALUE:
                return MinEqualValueChecker(type, error, item == null ? new BigDecimal(Integer.MIN_VALUE) : new BigDecimal(item.toString()));
            case ValidatorType.MAX_EQUAL_VALUE:
                return MaxEqualValueChecker(type, error, item == null ? new BigDecimal(Integer.MAX_VALUE) : new BigDecimal(item.toString()));
            case ValidatorType.ENGLISH_OR_NUMBER:
                return EnglishOrNumberChecker(type, error, item);
            case ValidatorType.ENGLISH:
                return EnglishChecker(type, error, item);
            case ValidatorType.CHINESE:
                return ChineseChecker(type, error, item);
        }
    }

    private static Validator EnglishOrNumberChecker(int type, String error, Object item) {
        return new Validator(type, error, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                return RegexUtils.isEnglishOrNumber(text.toString());
            }
        };
    }

    private static Validator EnglishChecker(int type, String error, Object item) {
        return new Validator(type, error, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                return RegexUtils.isEnglish(text.toString());
            }
        };
    }

    private static Validator ChineseChecker(int type, String error, Object item) {
        return new Validator(type, error, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                return RegexUtils.isChinese(text.toString());
            }
        };
    }

    private static Validator EmptyChecker(@ValidatorType.VType int type, final String errTxt, @Nullable Object item) {
        return new Validator(type, errTxt, item) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {
                return !text.toString().trim().equals("");
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
                return PhoneUtils.isPhoneNumber(text + "");
            }
        };
    }

    private static Validator BeginChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                boolean beginWith09 = false;

                for (String t : items) {
                    if (text.toString().startsWith(t)) {
                        beginWith09 = true;
                        break;
                    }
                }

                return beginWith09;
            }
        };
    }

    private static Validator EndChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                boolean endWith09 = false;

                for (String t : items) {
                    if (text.toString().endsWith(t)) {
                        endWith09 = true;
                        break;
                    }
                }

                return endWith09;
            }
        };
    }

    private static Validator ContainsChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                ArrayList<String> items = StringUtils.getStringArray(fullText, ",");
                boolean contains09 = false;

                for (String t : items) {
                    if (text.toString().contains(t)) {
                        contains09 = true;
                        break;
                    }
                }

                return contains09;
            }
        };
    }

    private static Validator notAllNumberChecker(@ValidatorType.VType int type, String error, final String fullText) {
        return new Validator(type, error, fullText) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                return !RegexUtils.isRealNumber1(text.toString());
            }
        };
    }

    private static Validator EmailChecker(@ValidatorType.VType int type, String error, @Nullable Object item) {
        return new RegexpValidator(type, error, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", item);
    }

    private static Validator MinValueChecker(@ValidatorType.VType int type, String errTxt, BigDecimal minValue) {
        return new Validator(type, errTxt, minValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                BigDecimal inputValue = new BigDecimal(text.toString());

                return inputValue.compareTo(minValue) > 0;
            }
        };
    }

    private static Validator MaxValueChecker(@ValidatorType.VType int type, String errTxt, BigDecimal maxValue) {
        return new Validator(type, errTxt, maxValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                BigDecimal inputValue = new BigDecimal(text.toString());

                return inputValue.compareTo(maxValue) < 0;
            }
        };
    }

    private static Validator MinEqualValueChecker(@ValidatorType.VType int type, String errTxt, BigDecimal minValue) {
        return new Validator(type, errTxt, minValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                BigDecimal inputValue = new BigDecimal(text.toString());

                return inputValue.compareTo(minValue) >= 0;
            }
        };
    }

    private static Validator MaxEqualValueChecker(@ValidatorType.VType int type, String errTxt, BigDecimal maxValue) {
        return new Validator(type, errTxt, maxValue) {
            @Override
            public boolean isValid(@NonNull CharSequence text) {

                if (TextUtils.isEmpty(text)) {
                    return true;
                }

                if (!RegexUtils.isRealNumber1(text.toString())) {
                    return false;
                }

                BigDecimal inputValue = new BigDecimal(text.toString());

                return inputValue.compareTo(maxValue) <= 0;
            }
        };
    }

}
