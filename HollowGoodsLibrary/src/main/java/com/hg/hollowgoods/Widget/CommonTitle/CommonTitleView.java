package com.hg.hollowgoods.Widget.CommonTitle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.DensityUtils;

/**
 * 公共标题控件
 * Created by HG on 2017-10-12.
 */
public class CommonTitleView extends BaseCommonTitle {

    private Context mContext;
    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mRightTitle;

    private boolean needShadow;
    private boolean alwaysShow;

    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleView);
        needShadow = typedArray.getBoolean(R.styleable.CommonTitleView_ctv_needShadow, true);
        alwaysShow = typedArray.getBoolean(R.styleable.CommonTitleView_ctv_alwaysShow, true);
        typedArray.recycle();

        init();
    }

    private void init() {

        if (!needShadow) {
            this.setStateListAnimator(null);
        }

        mToolbar = (Toolbar) View.inflate(mContext, R.layout.common_title_view, null);
        this.addView(mToolbar);

        getToolbar().setTitle("");
        getToolbar().setBackgroundResource(HGCommonResource.TITLE_BAR_RESOURCE);
        if (alwaysShow) {
            LayoutParams lp = (LayoutParams) getToolbar().getLayoutParams();
            lp.setScrollFlags(LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Gravity.CENTER);
        mTitle = new TextView(mContext);
        mTitle.setLayoutParams(lp);
        mTitle.setId(R.id.commonTitleCenterText);
        mTitle.setGravity(Gravity.CENTER);
        mToolbar.addView(mTitle, lp);

        lp = new Toolbar.LayoutParams(Gravity.END);
        lp.setMarginEnd(DensityUtils.dp2px(mContext, DEFAULT_RIGHT_TEXT_MARGIN));
        mRightTitle = new TextView(mContext);
        mRightTitle.setLayoutParams(lp);
        mRightTitle.setId(R.id.commonTitleRightText);
        mRightTitle.setGravity(Gravity.CENTER);
        mToolbar.addView(mRightTitle, lp);

        setCenterTitleTextSize(DEFAULT_TEXT_SIZE);
        setCenterTitleTextColor(DEFAULT_TEXT_COLOR);
        setRightTitleTextSize(DEFAULT_RIGHT_TEXT_SIZE);
        setRightTitleTextColor(DEFAULT_TEXT_COLOR);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void setTitleBackgroundResource(int resId) {
        getToolbar().setBackgroundResource(resId);
    }

    @Override
    public void setTitleBackgroundColor(int color) {
        getToolbar().setBackgroundColor(color);
    }

    @Override
    public void setTitleBackground(Drawable background) {
        getToolbar().setBackground(background);
    }

    @Override
    public void setCenterTitleTextColorRes(@ColorRes int resId) {
        setCenterTitleTextColor(mContext.getResources().getColor(resId));
    }

    @Override
    public void setCenterTitleTextColor(@ColorInt int color) {
        mTitle.setTextColor(color);
    }

    @Override
    public void setCenterTitleTextSize(float size) {
        mTitle.setTextSize(size);
    }

    @Override
    public void setRightTitleTextColorRes(@ColorRes int resId) {
        setCenterTitleTextColor(mRightTitle.getResources().getColor(resId));
    }

    @Override
    public void setRightTitleTextColor(@ColorInt int color) {
        mRightTitle.setTextColor(color);
    }

    @Override
    public void setRightTitleTextSize(float size) {
        mRightTitle.setTextSize(size);
    }

    @Override
    public void setCenterTitle(@StringRes int resId) {
        mTitle.setText(resId);
    }

    @Override
    public void setCenterTitle(CharSequence title) {
        mTitle.setText(title);
    }

    @Override
    public void setRightTitle(@StringRes int resId) {
        mRightTitle.setText(resId);
    }

    @Override
    public void setRightTitle(CharSequence title) {
        mRightTitle.setText(title);
    }

    @Override
    public void setCenterTitleOnClickListener(OnClickListener onClickListener) {
        mTitle.setOnClickListener(onClickListener);
    }

    @Override
    public void setLeftIcon(@DrawableRes int resId) {
        setLeftIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    @Override
    public void setLeftIcon(@Nullable Drawable icon) {
        getToolbar().setNavigationIcon(icon);
    }

    @Override
    public void setLeftOnClickListener(OnClickListener onClickListener) {
        getToolbar().setNavigationOnClickListener(onClickListener);
    }

    @Override
    public void setRightOnClickListener(Toolbar.OnMenuItemClickListener onClickListener) {
        getToolbar().setOnMenuItemClickListener(onClickListener);
    }

    @Override
    public void setRightTitleTextOnClickListener(OnClickListener onClickListener) {
        mRightTitle.setOnClickListener(onClickListener);
    }

    @Override
    public void addOtherView(View view) {
        addOtherView(view, null);
    }

    @Override
    public void addOtherView(View view, Toolbar.LayoutParams layoutParams) {
        if (layoutParams == null) {
            getToolbar().addView(view);
        } else {
            getToolbar().addView(view, layoutParams);
        }
    }

    @Override
    public void setOverflowIcon(Drawable drawable) {
        getToolbar().setOverflowIcon(drawable);
    }

}
