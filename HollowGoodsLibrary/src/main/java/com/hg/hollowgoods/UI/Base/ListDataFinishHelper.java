package com.hg.hollowgoods.UI.Base;

import android.support.annotation.NonNull;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshFooter;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshHeader;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshLayout;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.constant.RefreshState;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.listener.OnMultiPurposeListener;

/**
 * 列表获取数据结束助手
 * Created by Hollow Goods on 2019-07-31.
 */
public abstract class ListDataFinishHelper implements OnMultiPurposeListener, ListDataAnimFinishListener {

    @Override
    public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderFinish(RefreshHeader header, boolean success) {
        onAnimFinish(success);
    }

    @Override
    public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterFinish(RefreshFooter footer, boolean success) {
        onAnimFinish(success);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

}
