package com.hg.hollowgoods.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.NetWorkUtils;
import com.hg.hollowgoods.Util.SystemAppUtils;
import com.hg.hollowgoods.Widget.DesertPlaceholder.DesertPlaceholder;
import com.hg.hollowgoods.Widget.FloatingSearchView.FloatingSearchView;

/**
 * 状态布局
 * Created by Hollow Goods on 2019-04-26.
 */
public class HGStatusLayout extends FrameLayout {

    public enum Status {
        /**** 加载布局 ****/
        Loading,
        /**** 错误布局 ****/
        Error,
        /**** 无数据布局 ****/
        NoData,
        /**** 网络连接断开布局 ****/
        NetworkBreak,
        /**** 查询结果布局 ****/
        SearchResult,
        /**** 默认布局 ****/
        Default
    }

    private View loadingView;
    private View errorView;
    private View noDataView;
    private View networkBreakView;
    private View searchResultView;

    private int loadingViewRes;
    private int errorViewRes;
    private int noDataViewRes;
    private int networkBreakViewRes;
    private int searchResultViewRes;
    private boolean isLoadingViewTop;
    private boolean isErrorViewTop;
    private boolean isNoDataViewTop;
    private boolean isNetworkBreakViewTop;
    private boolean isSearchResultViewTop;

    private Status beforeStatus = Status.Default;
    private Status nowStatus = Status.Default;
    private int searchViewPosition = -1;

    public HGStatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public HGStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HGStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HGStatusLayout);

        loadingViewRes = typedArray.getResourceId(R.styleable.HGStatusLayout_sl_loadingView, R.layout.status_view_loading);
        errorViewRes = typedArray.getResourceId(R.styleable.HGStatusLayout_sl_errorView, R.layout.status_view_error);
        noDataViewRes = typedArray.getResourceId(R.styleable.HGStatusLayout_sl_noDataView, R.layout.status_view_no_data);
        networkBreakViewRes = typedArray.getResourceId(R.styleable.HGStatusLayout_sl_networkBreakView, R.layout.status_view_network_break);
        searchResultViewRes = typedArray.getResourceId(R.styleable.HGStatusLayout_sl_searchResultView, R.layout.status_view_search_result);

        isLoadingViewTop = typedArray.getBoolean(R.styleable.HGStatusLayout_sl_loadingViewTop, true);
        isErrorViewTop = typedArray.getBoolean(R.styleable.HGStatusLayout_sl_errorViewTop, true);
        isNoDataViewTop = typedArray.getBoolean(R.styleable.HGStatusLayout_sl_noDataViewTop, false);
        isNetworkBreakViewTop = typedArray.getBoolean(R.styleable.HGStatusLayout_sl_networkBreakViewTop, true);
        isSearchResultViewTop = typedArray.getBoolean(R.styleable.HGStatusLayout_sl_searchResultViewTop, false);

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        int childCount = getChildCount();

        for (int i = childCount - 1; i >= 0; i--) {
            if (getChildAt(i) instanceof FloatingSearchView) {
                searchViewPosition = i;
                break;
            }
        }

        if (getContext() != null && !NetWorkUtils.isNetConnected(getContext())) {
            setStatus(Status.NetworkBreak);
        }
    }

    public void networkLink() {
        if (!canChangeNetworkStatus()) {
            removeAllStatusView();
            nowStatus = Status.Default;
            setStatus(beforeStatus);
        }
    }

    public void setStatus(Status status) {

        switch (status) {
            case Loading:
                showLoadingView(status);
                break;
            case Error:
                showErrorView(status);
                break;
            case NoData:
                showNoDataView(status);
                break;
            case NetworkBreak:
                showNetworkBreakView();
                break;
            case SearchResult:
                showSearchResultView(status);
                break;
            case Default:
                if (canChangeStatus(status)) {
                    removeAllStatusView();
                }
                break;
        }
    }

    public Status getNowStatus() {
        return nowStatus;
    }

    private void showLoadingView(Status status) {
        if (canChangeStatus(status)) {

            beforeStatus = nowStatus;
            nowStatus = Status.Loading;
            removeAllStatusView();

            if (loadingView == null) {
                loadingView = View.inflate(getContext(), loadingViewRes, null);
            }

            if (isLoadingViewTop) {
                addView(loadingView);
            } else {
                if (searchViewPosition != -1) {
                    addView(loadingView, searchViewPosition);
                } else {
                    addView(loadingView);
                }
            }
        }
    }

    private void showErrorView(Status status) {
        if (canChangeStatus(status)) {

            beforeStatus = nowStatus;
            nowStatus = Status.Error;
            removeAllStatusView();

            if (errorView == null) {
                errorView = View.inflate(getContext(), errorViewRes, null);
            }

            if (isErrorViewTop) {
                addView(errorView);
            } else {
                if (searchViewPosition != -1) {
                    addView(errorView, searchViewPosition);
                } else {
                    addView(errorView);
                }
            }
        }
    }

    private void showNoDataView(Status status) {
        if (canChangeStatus(status)) {

            beforeStatus = nowStatus;
            nowStatus = Status.NoData;
            removeAllStatusView();

            if (noDataView == null) {
                noDataView = View.inflate(getContext(), noDataViewRes, null);
            }

            if (isNoDataViewTop) {
                addView(noDataView);
            } else {
                if (searchViewPosition != -1) {
                    addView(noDataView, searchViewPosition);
                } else {
                    addView(noDataView);
                }
            }
        }
    }

    private void showNetworkBreakView() {
        if (canChangeNetworkStatus()) {
            beforeStatus = nowStatus;
            nowStatus = Status.NetworkBreak;
            removeAllStatusView();

            if (networkBreakView == null) {
                networkBreakView = View.inflate(getContext(), networkBreakViewRes, null);

                if (networkBreakViewRes == R.layout.status_view_network_break) {
                    DesertPlaceholder placeholder = networkBreakView.findViewById(R.id.placeholder);
                    placeholder.setOnButtonClickListener(v -> new SystemAppUtils().openNetworkSetting(getContext()));
                }
            }

            if (isNetworkBreakViewTop) {
                addView(networkBreakView);
            } else {
                if (searchViewPosition != -1) {
                    addView(networkBreakView, searchViewPosition);
                } else {
                    addView(networkBreakView);
                }
            }
        }
    }

    private void showSearchResultView(Status status) {
        if (canChangeStatus(status)) {

            beforeStatus = nowStatus;
            nowStatus = Status.SearchResult;
            removeAllStatusView();

            if (searchResultView == null) {
                searchResultView = View.inflate(getContext(), searchResultViewRes, null);
            }

            if (isSearchResultViewTop) {
                addView(searchResultView);
            } else {
                if (searchViewPosition != -1) {
                    addView(searchResultView, searchViewPosition);
                } else {
                    addView(searchResultView);
                }
            }
        }
    }

    private void removeAllStatusView() {

        if (loadingView != null) {
            int index = indexOfChild(loadingView);
            if (index != -1) {
                removeViewAt(index);
            }
        }

        if (errorView != null) {
            int index = indexOfChild(errorView);
            if (index != -1) {
                removeViewAt(index);
            }
        }

        if (noDataView != null) {
            int index = indexOfChild(noDataView);
            if (index != -1) {
                removeViewAt(index);
            }
        }

        if (networkBreakView != null) {
            int index = indexOfChild(networkBreakView);
            if (index != -1) {
                removeViewAt(index);
            }
        }

        if (searchResultView != null) {
            int index = indexOfChild(searchResultView);
            if (index != -1) {
                removeViewAt(index);
            }
        }
    }

    private boolean canChangeStatus(Status status) {
        return nowStatus != status
                && nowStatus != Status.Error
                && canChangeNetworkStatus();
    }

    private boolean canChangeNetworkStatus() {
        return nowStatus != Status.NetworkBreak;
    }

}
