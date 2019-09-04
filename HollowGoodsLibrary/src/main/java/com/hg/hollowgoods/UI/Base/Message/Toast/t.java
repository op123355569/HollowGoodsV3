package com.hg.hollowgoods.UI.Base.Message.Toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.R;

/**
 * 吐司提示工具类
 * Created by HG on 2018-06-06.
 */
public class t {

    private static Context context;

    static {
        context = ApplicationBuilder.create();
    }

    private static void showToast(CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    /**
     * 显示短土司消息
     *
     * @param text 要显示的消息
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短土司消息
     *
     * @param textRes 要显示的消息
     */
    public static void showShortToast(int textRes) {
        showShortToast(context.getString(textRes));
    }

    /**
     * 显示短土司消息
     *
     * @param obj 要显示的消息
     */
    public static void showShortToastByObj(Object obj) {
        if (obj instanceof Integer) {
            showShortToast((Integer) obj);
        } else if (obj instanceof CharSequence) {
            showShortToast((CharSequence) obj);
        } else {
            showShortToast(obj + "");
        }
    }

    /**
     * 显示长土司消息
     *
     * @param text 要显示的消息
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长土司消息
     *
     * @param textRes 要显示的消息
     */
    public static void showLongToast(int textRes) {
        showLongToast(context.getString(textRes));
    }

    /**
     * 显示长土司消息
     *
     * @param obj 要显示的消息
     */
    public static void showLongToastByObj(Object obj) {
        if (obj instanceof Integer) {
            showLongToast((Integer) obj);
        } else if (obj instanceof CharSequence) {
            showLongToast((CharSequence) obj);
        } else {
            showLongToast(obj + "");
        }
    }

    /**** 新吐司 ****/

    /**
     * 文字颜色
     */
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = ContextCompat.getColor(context, R.color.white);
    /**
     * 错误吐司背景颜色
     */
    @ColorInt
    private static final int ERROR_COLOR = ContextCompat.getColor(context, R.color.google_red);
    /**
     * 信息吐司背景颜色
     */
    @ColorInt
    private static final int INFO_COLOR = ContextCompat.getColor(context, R.color.google_blue);
    /**
     * 成功吐司背景颜色
     */
    @ColorInt
    private static final int SUCCESS_COLOR = ContextCompat.getColor(context, R.color.google_green);
    /**
     * 警告吐司背景颜色
     */
    @ColorInt
    private static final int WARNING_COLOR = ContextCompat.getColor(context, R.color.google_yellow);

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    private static Toast currentToast;

    /**
     * 立即显示无需等待
     */
    private static Toast mToast;
    /**
     * 正常吐司背景
     */
    private static Drawable normalDrawable = getDrawable(R.drawable.toast_frame);
    /**
     * 信息吐司背景
     */
    private static Drawable infoDrawable = tint9PatchDrawableFrame(INFO_COLOR);
    /**
     * 警告吐司背景
     */
    private static Drawable warningDrawable = tint9PatchDrawableFrame(WARNING_COLOR);
    /**
     * 错误吐司背景
     */
    private static Drawable errorDrawable = tint9PatchDrawableFrame(ERROR_COLOR);
    /**
     * 成功吐司背景
     */
    private static Drawable successDrawable = tint9PatchDrawableFrame(SUCCESS_COLOR);

    public static void normalByObj(Object obj) {
        if (obj instanceof Integer) {
            normal((Integer) obj);
        } else if (obj instanceof CharSequence) {
            normal((CharSequence) obj);
        } else {
            normal(obj + "");
        }
    }

    public static void normal(@NonNull CharSequence text) {
        normal(text, Toast.LENGTH_SHORT, null, false).show();
    }

    public static void normal(@NonNull CharSequence text, ToastType toastType) {
        normal(text, Toast.LENGTH_SHORT, toastType, true).show();
    }

    public static void normal(@NonNull CharSequence text, int duration) {
        normal(text, duration, null, false).show();
    }

    public static void normal(@NonNull CharSequence text, int duration, ToastType toastType) {
        normal(text, duration, toastType, true).show();
    }

    public static Toast normal(@NonNull CharSequence text, int duration, ToastType toastType, boolean withIcon) {
        return custom(text, toastType, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public static void warning(@NonNull CharSequence text) {
        warning(text, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(@NonNull CharSequence text, int duration) {
        warning(text, duration, true).show();
    }

    public static Toast warning(@NonNull CharSequence text, int duration, boolean withIcon) {
        return custom(text, ToastType.Warning, DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static void warningByObj(Object obj) {
        if (obj instanceof Integer) {
            warning((Integer) obj, Toast.LENGTH_SHORT, true).show();
        } else if (obj instanceof CharSequence) {
            warning((CharSequence) obj, Toast.LENGTH_SHORT, true).show();
        } else {
            warning(obj + "", Toast.LENGTH_SHORT, true).show();
        }
    }

    public static void warningByObj(Object obj, int duration) {
        if (obj instanceof Integer) {
            warning((Integer) obj, duration, true).show();
        } else if (obj instanceof CharSequence) {
            warning((CharSequence) obj, duration, true).show();
        } else {
            warning(obj + "", duration, true).show();
        }
    }

    public static Toast warningByObj(Object obj, int duration, boolean withIcon) {
        if (obj instanceof Integer) {
            return custom((Integer) obj, ToastType.Warning, DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
        } else if (obj instanceof CharSequence) {
            return custom((CharSequence) obj, ToastType.Warning, DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
        } else {
            return custom(obj + "", ToastType.Warning, DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
        }
    }

    public static void info(@NonNull CharSequence text) {
        info(text, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(@NonNull CharSequence text, int duration) {
        info(text, duration, true).show();
    }

    public static Toast info(@NonNull CharSequence text, int duration, boolean withIcon) {
        return custom(text, ToastType.Info, DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static void infoByObj(Object obj) {
        if (obj instanceof Integer) {
            info((Integer) obj, Toast.LENGTH_SHORT, true).show();
        } else if (obj instanceof CharSequence) {
            info((CharSequence) obj, Toast.LENGTH_SHORT, true).show();
        } else {
            info(obj + "", Toast.LENGTH_SHORT, true).show();
        }
    }

    public static void infoByObj(Object obj, int duration) {
        if (obj instanceof Integer) {
            info((Integer) obj, duration, true).show();
        } else if (obj instanceof CharSequence) {
            info((CharSequence) obj, duration, true).show();
        } else {
            info(obj + "", duration, true).show();
        }
    }

    public static Toast infoByObj(Object obj, int duration, boolean withIcon) {
        if (obj instanceof Integer) {
            return custom((Integer) obj, ToastType.Info, DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
        } else if (obj instanceof CharSequence) {
            return custom((CharSequence) obj, ToastType.Info, DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
        } else {
            return custom(obj + "", ToastType.Info, DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
        }
    }

    public static void success(@NonNull CharSequence text) {
        success(text, Toast.LENGTH_SHORT, true).show();
    }

    public static void success(@NonNull CharSequence text, int duration) {
        success(text, duration, true).show();
    }

    public static Toast success(@NonNull CharSequence text, int duration, boolean withIcon) {
        return custom(text, ToastType.Success, DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static void successByObj(Object obj) {
        if (obj instanceof Integer) {
            success((Integer) obj, Toast.LENGTH_SHORT, true).show();
        } else if (obj instanceof CharSequence) {
            success((CharSequence) obj, Toast.LENGTH_SHORT, true).show();
        } else {
            success(obj + "", Toast.LENGTH_SHORT, true).show();
        }
    }

    public static void successByObj(Object obj, int duration) {
        if (obj instanceof Integer) {
            success((Integer) obj, duration, true).show();
        } else if (obj instanceof CharSequence) {
            success((CharSequence) obj, duration, true).show();
        } else {
            success(obj + "", duration, true).show();
        }
    }

    public static Toast successByObj(Object obj, int duration, boolean withIcon) {
        if (obj instanceof Integer) {
            return custom((Integer) obj, ToastType.Success, DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
        } else if (obj instanceof CharSequence) {
            return custom((CharSequence) obj, ToastType.Success, DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
        } else {
            return custom(obj + "", ToastType.Success, DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
        }
    }

    public static void error(@NonNull CharSequence text) {
        error(text, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(@NonNull CharSequence text, int duration) {
        error(text, duration, true).show();
    }

    public static Toast error(@NonNull CharSequence text, int duration, boolean withIcon) {
        return custom(text, ToastType.Error, DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    public static void errorByObj(Object obj) {
        if (obj instanceof Integer) {
            error((Integer) obj, Toast.LENGTH_SHORT, true).show();
        } else if (obj instanceof CharSequence) {
            error((CharSequence) obj, Toast.LENGTH_SHORT, true).show();
        } else {
            error(obj + "", Toast.LENGTH_SHORT, true).show();
        }
    }

    public static void errorByObj(Object obj, int duration) {
        if (obj instanceof Integer) {
            error((Integer) obj, duration, true).show();
        } else if (obj instanceof CharSequence) {
            error((CharSequence) obj, duration, true).show();
        } else {
            error(obj + "", duration, true).show();
        }
    }

    public static Toast errorByObj(Object obj, int duration, boolean withIcon) {
        if (obj instanceof Integer) {
            return custom((Integer) obj, ToastType.Error, DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
        } else if (obj instanceof CharSequence) {
            return custom((CharSequence) obj, ToastType.Error, DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
        } else {
            return custom(obj + "", ToastType.Error, DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
        }
    }

    public static void normal(@StringRes int textRes) {
        normal(textRes, Toast.LENGTH_SHORT, null, false).show();
    }

    public static void normal(@StringRes int textRes, ToastType toastType) {
        normal(textRes, Toast.LENGTH_SHORT, toastType, true).show();
    }

    public static void normal(@StringRes int textRes, int duration) {
        normal(textRes, duration, null, false).show();
    }

    public static void normal(@StringRes int textRes, int duration, ToastType toastType) {
        normal(textRes, duration, toastType, true).show();
    }

    public static Toast normal(@StringRes int textRes, int duration, ToastType toastType, boolean withIcon) {
        return custom(textRes, toastType, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public static void warning(@StringRes int textRes) {
        warning(textRes, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(@StringRes int textRes, int duration) {
        warning(textRes, duration, true).show();
    }

    public static Toast warning(@StringRes int textRes, int duration, boolean withIcon) {
        return custom(textRes, ToastType.Warning, DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static void info(@StringRes int textRes) {
        info(textRes, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(@StringRes int textRes, int duration) {
        info(textRes, duration, true).show();
    }

    public static Toast info(@StringRes int textRes, int duration, boolean withIcon) {
        return custom(textRes, ToastType.Info, DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static void success(@StringRes int textRes) {
        success(textRes, Toast.LENGTH_SHORT, true).show();
    }

    public static void success(@StringRes int textRes, int duration) {
        success(textRes, duration, true).show();
    }

    public static Toast success(@StringRes int textRes, int duration, boolean withIcon) {
        return custom(textRes, ToastType.Success, DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static void error(@StringRes int textRes) {
        error(textRes, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(@StringRes int textRes, int duration) {
        error(textRes, duration, true).show();
    }

    public static Toast error(@StringRes int textRes, int duration, boolean withIcon) {
        return custom(textRes, ToastType.Error, DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull CharSequence text, ToastType toastType, @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(text, toastType, textColor, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@StringRes int textRes, ToastType toastType, @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(textRes, toastType, textColor, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@StringRes int textRes, ToastType toastType, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context.getString(textRes), toastType, textColor, tintColor, duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull CharSequence text, ToastType toastType, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {

        if (currentToast == null) {
            currentToast = new Toast(context);
        }

        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            if (tintColor == INFO_COLOR) {
                drawableFrame = infoDrawable;
            } else if (tintColor == WARNING_COLOR) {
                drawableFrame = warningDrawable;
            } else if (tintColor == ERROR_COLOR) {
                drawableFrame = errorDrawable;
            } else {
                drawableFrame = successDrawable;
            }
        } else {
            drawableFrame = normalDrawable;
        }
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (toastType.getIconRes() == -1) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            setBackground(toastIcon, toastType.getIconRes());
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(textColor);
        toastTextView.setText(text);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);

        return currentToast;
    }

    private static final Drawable tint9PatchDrawableFrame(@ColorInt int tintColor) {

        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));

        return toastDrawable;
    }

    private static final void setBackground(@NonNull View view, int iconRes) {
        view.setBackgroundResource(iconRes);
    }

    private static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static final Drawable getDrawable(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * 显示长吐司-立即显示无需等待
     *
     * @param text
     */
    public static void showShortToastNotWait(CharSequence text) {
        showToastNotWait(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长吐司-立即显示无需等待
     *
     * @param textRes
     */
    public static void showShortToastNotWait(int textRes) {
        showToastNotWait(context.getString(textRes), Toast.LENGTH_SHORT);
    }

    /**
     * 显示长吐司-立即显示无需等待
     *
     * @param text
     */
    public static void showLongToastNotWait(CharSequence text) {
        showToastNotWait(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长吐司-立即显示无需等待
     *
     * @param textRes
     */
    public static void showLongToastNotWait(int textRes) {
        showToastNotWait(context.getString(textRes), Toast.LENGTH_LONG);
    }

    /**
     * 显示吐司-立即显示无需等待
     *
     * @param textRes  String资源ID
     * @param duration 显示时长
     */
    public static void showToastNotWait(int textRes, int duration) {
        showToastNotWait(context.getString(textRes), duration);
    }

    /**
     * 显示吐司-立即显示无需等待
     *
     * @param text
     * @param duration
     */
    public static void showToastNotWait(CharSequence text, int duration) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }

        mToast.show();
    }

}
