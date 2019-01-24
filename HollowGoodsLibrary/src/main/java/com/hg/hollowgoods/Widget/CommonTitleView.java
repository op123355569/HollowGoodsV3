package com.hg.hollowgoods.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.DensityUtils;

/**
 * 公共标题控件
 * Created by HG on 2017-10-12.
 */

public class CommonTitleView extends AppBarLayout {

    private final float DEFAULT_TEXT_SIZE = 16f;
    private final float DEFAULT_RIGHT_TEXT_SIZE = 14f;
    private final float DEFAULT_RIGHT_TEXT_MARGIN = 16f;
    private final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private Context mContext;
    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mRightTitle;

    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleView);
        boolean needShadow = typedArray.getBoolean(R.styleable.CommonTitleView_needShadow, true);
        typedArray.recycle();

        init(needShadow);
    }

    private void init(boolean needShadow) {

        if (!needShadow) {
            this.setStateListAnimator(null);
        }

        mToolbar = (Toolbar) View.inflate(mContext, R.layout.common_title_view, null);
        this.addView(mToolbar);

        getToolbar().setTitle("");
        getToolbar().setBackgroundResource(CommonResource.TITLE_BAR_RESOURCE);

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        mTitle = new TextView(mContext);
        mTitle.setLayoutParams(lp);
        mTitle.setId(R.id.commonTitleCenterText);
        mToolbar.addView(mTitle, lp);

        lp = new Toolbar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMarginEnd(DensityUtils.dp2px(mContext, DEFAULT_RIGHT_TEXT_MARGIN));
        lp.gravity = Gravity.RIGHT;
        mRightTitle = new TextView(mContext);
        mRightTitle.setLayoutParams(lp);
        mRightTitle.setId(R.id.commonTitleRightText);
        mToolbar.addView(mRightTitle, lp);

        setCenterTitleTextSize(DEFAULT_TEXT_SIZE);
        setCenterTitleTextColor(DEFAULT_TEXT_COLOR);
        setRightTitleTextSize(DEFAULT_RIGHT_TEXT_SIZE);
        setRightTitleTextColor(DEFAULT_TEXT_COLOR);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitleBackgroundResource(int resId) {
        getToolbar().setBackgroundResource(resId);
    }

    public void setTitleBackgroundColor(int color) {
        getToolbar().setBackgroundColor(color);
    }

    public void setTitleBackground(Drawable background) {
        getToolbar().setBackground(background);
    }

    public void setCenterTitleTextColorRes(@ColorRes int resId) {
        setCenterTitleTextColor(mContext.getResources().getColor(resId));
    }

    public void setCenterTitleTextColor(@ColorInt int color) {
        mTitle.setTextColor(color);
    }

    public void setCenterTitleTextSize(float size) {
        mTitle.setTextSize(size);
    }

    public void setRightTitleTextColorRes(@ColorRes int resId) {
        setCenterTitleTextColor(mRightTitle.getResources().getColor(resId));
    }

    public void setRightTitleTextColor(@ColorInt int color) {
        mRightTitle.setTextColor(color);
    }

    public void setRightTitleTextSize(float size) {
        mRightTitle.setTextSize(size);
    }

    public void setCenterTitle(@StringRes int resId) {
        mTitle.setText(resId);
    }

    public void setCenterTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setRightTitle(@StringRes int resId) {
        mRightTitle.setText(resId);
    }

    public void setRightTitle(CharSequence title) {
        mRightTitle.setText(title);
    }

    public void setCenterTitleOnClickListener(OnClickListener onClickListener) {
        mTitle.setOnClickListener(onClickListener);
    }

    public void setLeftIcon(@DrawableRes int resId) {
        setLeftIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setLeftIcon(@Nullable Drawable icon) {
        getToolbar().setNavigationIcon(icon);
    }

    public void setLeftOnClickListener(OnClickListener onClickListener) {
        getToolbar().setNavigationOnClickListener(onClickListener);
    }

    public void setRightOnClickListener(Toolbar.OnMenuItemClickListener onClickListener) {
        getToolbar().setOnMenuItemClickListener(onClickListener);
    }

    public void setRightTitleTextOnClickListener(OnClickListener onClickListener) {
        mRightTitle.setOnClickListener(onClickListener);
    }

    public void addOtherView(View view) {
        addOtherView(view, null);
    }

    public void addOtherView(View view, Toolbar.LayoutParams layoutParams) {
        if (layoutParams == null) {
            getToolbar().addView(view);
        } else {
            getToolbar().addView(view, layoutParams);
        }
    }

    public void setOverflowIcon(Drawable drawable) {
        getToolbar().setOverflowIcon(drawable);
    }

}
