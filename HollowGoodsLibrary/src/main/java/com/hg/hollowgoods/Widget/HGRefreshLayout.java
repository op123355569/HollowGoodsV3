package com.hg.hollowgoods.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.RecyclerViewAnim.adapters.ScaleInAnimationAdapter;
import com.hg.hollowgoods.Util.RecyclerViewAnim.animators.LandingAnimator;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.SmartRefreshLayout;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.listener.OnLoadMoreListener;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.listener.OnRefreshListener;

/**
 * 刷新加载控件
 * <p>在初始化界面的时候，最好调用一下自动刷新，否则滑动过快会出现黑球BUG</p>
 * Created by Hollow Goods on 2019-03-28.
 */
public class HGRefreshLayout extends FrameLayout {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView result;

    public HGRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public HGRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HGRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private boolean openRefresh = true;
    private boolean openLoadMore = true;
    private int listPadding = 0;

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HGRefreshLayout);
            openRefresh = typedArray.getBoolean(R.styleable.HGRefreshLayout_openRefresh, openRefresh);
            openLoadMore = typedArray.getBoolean(R.styleable.HGRefreshLayout_openLoadMore, openRefresh);
            listPadding = typedArray.getDimensionPixelOffset(R.styleable.HGRefreshLayout_listPadding, listPadding);
            typedArray.recycle();
        }

        View view = View.inflate(getContext(), R.layout.hg_refresh_layout, null);
        this.addView(view);

        refreshLayout = findViewById(R.id.smartRefreshLayout);
        result = findViewById(R.id.rv_result);

        refreshLayout.setEnableHeaderTranslationContent(false);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setPrimaryColorsId(HGCommonResource.TITLE_BAR_RESOURCE, R.color.white);

        refreshLayout.setEnableRefresh(openRefresh);
        refreshLayout.setEnableLoadMore(openLoadMore);

        if (listPadding != 0) {
            result.setPadding(listPadding, listPadding, listPadding, listPadding);
        }
    }

    /**
     * 初始化RecyclerView
     * <p>以 LinearLayoutManager 初始化</p>
     * <p>以 LandingAnimator 初始化</p>
     */
    public void initRecyclerView() {
        initRecyclerView(null);
    }

    /**
     * 初始化RecyclerView
     * <p>以 LandingAnimator 初始化</p>
     *
     * @param layout LayoutManager
     */
    public void initRecyclerView(RecyclerView.LayoutManager layout) {

        result.setHasFixedSize(true);
        result.setItemAnimator(new LandingAnimator());

        if (layout == null) {
            result.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            result.setLayoutManager(layout);
        }
    }

    /**
     * 设置RecyclerView的适配器
     * <p>以 ScaleInAnimationAdapter 设置</p>
     *
     * @param adapter adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        result.setAdapter(new ScaleInAnimationAdapter(adapter));
    }

    /**
     * 设置下拉刷新监听
     *
     * @param onRefreshListener onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置上拉加载监听
     *
     * @param onLoadMoreListener onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        refreshLayout.setOnLoadMoreListener(onLoadMoreListener);
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return result;
    }

}
