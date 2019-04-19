package com.hg.hollowgoods.Widget.CommonTitle;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Hollow Goods on 2019-04-19.
 */
public interface IBaseCommonTitle {

    /**
     * 获取Toolbar
     *
     * @return Toolbar
     */
    Toolbar getToolbar();

    /**
     * 设置公共标题背景颜色
     *
     * @param resId resId
     */
    void setTitleBackgroundResource(@DrawableRes int resId);

    /**
     * 设置公共标题背景颜色
     *
     * @param color color
     */
    void setTitleBackgroundColor(@ColorInt int color);

    /**
     * 设置公共标题背景颜色
     *
     * @param background background
     */
    void setTitleBackground(Drawable background);

    /**
     * 设置标题字体颜色
     *
     * @param resId resId
     */
    void setCenterTitleTextColorRes(@ColorRes int resId);

    /**
     * 设置标题字体颜色
     *
     * @param color color
     */
    void setCenterTitleTextColor(@ColorInt int color);

    /**
     * 设置标题字体大小
     *
     * @param size size
     */
    void setCenterTitleTextSize(float size);

    /**
     * 设置右侧标题字体颜色
     *
     * @param resId
     */
    void setRightTitleTextColorRes(@ColorRes int resId);

    /**
     * 设置右侧标题字体颜色
     *
     * @param color
     */
    void setRightTitleTextColor(@ColorInt int color);

    /**
     * 设置右侧标题字体大小
     *
     * @param size
     */
    void setRightTitleTextSize(float size);

    /**
     * 设置标题文字
     *
     * @param resId resId
     */
    void setCenterTitle(@StringRes int resId);

    /**
     * 设置标题文字
     *
     * @param title title
     */
    void setCenterTitle(CharSequence title);

    /**
     * 设置右侧标题文字
     *
     * @param resId resId
     */
    void setRightTitle(@StringRes int resId);

    /**
     * 设置右侧标题文字
     *
     * @param title title
     */
    void setRightTitle(CharSequence title);

    /**
     * 设置标题点击事件
     *
     * @param onClickListener onClickListener
     */
    void setCenterTitleOnClickListener(View.OnClickListener onClickListener);

    /**
     * 设置左侧图标
     *
     * @param resId resId
     */
    void setLeftIcon(@DrawableRes int resId);

    /**
     * 设置左侧图标
     *
     * @param icon icon
     */
    void setLeftIcon(@Nullable Drawable icon);

    /**
     * 设置左侧图标点击事件
     *
     * @param onClickListener onClickListener
     */
    void setLeftOnClickListener(View.OnClickListener onClickListener);

    /**
     * 设置右侧菜单点击事件
     *
     * @param onClickListener onClickListener
     */
    void setRightOnClickListener(Toolbar.OnMenuItemClickListener onClickListener);

    /**
     * 设置右侧标题点击事件
     *
     * @param onClickListener onClickListener
     */
    void setRightTitleTextOnClickListener(View.OnClickListener onClickListener);

    /**
     * 添加额外控件
     *
     * @param view view
     */
    void addOtherView(View view);

    /**
     * 添加额外控件
     *
     * @param view         view
     * @param layoutParams layoutParams
     */
    void addOtherView(View view, Toolbar.LayoutParams layoutParams);

    /**
     * 设置右侧菜单溢出图标
     *
     * @param drawable drawable
     */
    void setOverflowIcon(Drawable drawable);

    /**
     * 添加头部
     * <p>
     * {@link CommonTitleLayout}
     *
     * @param headerLayoutRes headerLayoutRes
     */
    default void addHeader(@LayoutRes int headerLayoutRes) {

    }

    /**
     * 添加头部
     * <p>
     * {@link CommonTitleLayout}
     *
     * @param headerView
     */
    default void addHeader(View headerView) {

    }

    /**
     * 打开头部
     * <p>
     * {@link CommonTitleLayout}
     */
    default void openHeader() {

    }

    /**
     * 关闭头部
     * <p>
     * {@link CommonTitleLayout}
     */
    default void closeHeader() {

    }

    /**
     * 自动开关头部
     * <p>
     * {@link CommonTitleLayout}
     */
    default void autoSwitchHeader() {

    }

}
