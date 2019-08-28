package com.hg.hollowgoods.Widget.CommonTitle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.DensityUtils;

/**
 * 公共标题控件
 * Created by Hollow Goods on 2019-04-18.
 */
public class CommonTitleLayout extends BaseCommonTitle {

    private Context mContext;

    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mRightTitle;
    private FrameLayout mHeader;
    private CollapsingToolbarLayout parent;

    private boolean needShadow;
    private boolean openHeader;
    private int headerHeight;
    private boolean isHeaderExpanded;

    public CommonTitleLayout(Context context) {
        this(context, null);
    }

    public CommonTitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleLayout);
        needShadow = typedArray.getBoolean(R.styleable.CommonTitleLayout_ctl_needShadow, true);
        openHeader = typedArray.getBoolean(R.styleable.CommonTitleLayout_ctl_openHeader, false);
        headerHeight = typedArray.getDimensionPixelSize(R.styleable.CommonTitleLayout_ctl_headerHeight, -1);
        typedArray.recycle();

        init();
    }

    private void init() {

        if (!needShadow) {
            this.setStateListAnimator(null);
        }

        parent = (CollapsingToolbarLayout) View.inflate(mContext, R.layout.common_title_layout, null);
        this.addView(parent);

        LayoutParams alp = (LayoutParams) parent.getLayoutParams();
        if (openHeader) {
            alp.setScrollFlags(LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        } else {
            alp.setScrollFlags(LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }

        setTitleBackgroundResource(HGCommonResource.TITLE_BAR_RESOURCE);
        // 收缩前字体颜色
        parent.setExpandedTitleColor(ContextCompat.getColor(mContext, R.color.white));
        // 收缩后字体颜色
        parent.setCollapsedTitleTextColor(ContextCompat.getColor(mContext, R.color.white));

        mHeader = parent.findViewById(R.id.header);
        mToolbar = parent.findViewById(R.id.toolbar);

        // 设置头部
        isHeaderExpanded = openHeader;
        mHeader.setVisibility(openHeader ? VISIBLE : GONE);
        if (headerHeight != -1) {
            CollapsingToolbarLayout.LayoutParams clp = (CollapsingToolbarLayout.LayoutParams) mHeader.getLayoutParams();
            clp.height = headerHeight;
        }

        // 设置Toolbar
        getToolbar().setTitle("");
        if (openHeader) {
            getToolbar().setAlpha(0);
        }

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Gravity.CENTER);
        int maxTitleWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.5f);
        mTitle = new TextView(mContext);
        mTitle.setLayoutParams(lp);
        mTitle.setId(R.id.commonTitleCenterText);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setMaxWidth(maxTitleWidth);
        mTitle.setMaxLines(2);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        getToolbar().addView(mTitle, lp);

        lp = new Toolbar.LayoutParams(Gravity.END);
        lp.setMarginEnd(DensityUtils.dp2px(mContext, DEFAULT_RIGHT_TEXT_MARGIN));
        mRightTitle = new TextView(mContext);
        mRightTitle.setLayoutParams(lp);
        mRightTitle.setId(R.id.commonTitleRightText);
        mRightTitle.setGravity(Gravity.CENTER);
        getToolbar().addView(mRightTitle, lp);

        setCenterTitleTextSize(DEFAULT_TEXT_SIZE);
        setCenterTitleTextColor(DEFAULT_TEXT_COLOR);
        setRightTitleTextSize(DEFAULT_RIGHT_TEXT_SIZE);
        setRightTitleTextColor(DEFAULT_TEXT_COLOR);

        addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            isHeaderExpanded = verticalOffset == 0;
            getToolbar().setAlpha(Math.abs(verticalOffset) * 1F / appBarLayout.getTotalScrollRange());
        });
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void setTitleBackgroundResource(@DrawableRes int resId) {
        // 收缩前背景颜色
        parent.setBackgroundResource(resId);
        // 收缩后背景颜色
        parent.setContentScrimResource(resId);
    }

    @Override
    public void setTitleBackgroundColor(@ColorInt int color) {
        // 收缩前背景颜色
        parent.setBackgroundColor(color);
        // 收缩后背景颜色
        parent.setContentScrimColor(color);
    }

    @Override
    public void setTitleBackground(Drawable background) {
        // 收缩前背景颜色
        parent.setBackground(background);
        // 收缩后背景颜色
        parent.setContentScrim(background);
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
        setRightTitleTextColor(mRightTitle.getResources().getColor(resId));
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

    @Override
    public void addHeader(@LayoutRes int headerLayoutRes) {
        addHeader(View.inflate(mContext, headerLayoutRes, null));
    }

    @Override
    public void addHeader(View headerView) {
        mHeader.addView(headerView);
    }

    @Override
    public void openHeader() {
        if (openHeader) {
            isHeaderExpanded = true;
            switchHeader();
        }
    }

    @Override
    public void closeHeader() {
        if (openHeader) {
            isHeaderExpanded = false;
            switchHeader();
        }
    }

    @Override
    public void autoSwitchHeader() {
        if (openHeader) {
            isHeaderExpanded = !isHeaderExpanded;
            switchHeader();
        }
    }

    private void switchHeader() {
        setExpanded(isHeaderExpanded);
    }

}
