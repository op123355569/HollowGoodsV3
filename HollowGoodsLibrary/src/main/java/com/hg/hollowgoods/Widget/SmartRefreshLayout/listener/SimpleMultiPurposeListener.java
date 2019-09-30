package com.hg.hollowgoods.Widget.SmartRefreshLayout.listener;

import android.support.annotation.NonNull;

import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshFooter;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshHeader;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.api.RefreshLayout;
import com.hg.hollowgoods.Widget.SmartRefreshLayout.constant.RefreshState;

/**
 * 多功能监听器
 * Created by Hollow Goods on unknown.
 */
public class SimpleMultiPurposeListener implements OnMultiPurposeListener {

//    @Override
//    public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int maxDragHeight) {
//
//    }

    @Override
    public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

    }

//    @Override
//    public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int maxDragHeight) {
//
//    }

    @Override
    public void onHeaderStartAnimator(RefreshHeader header, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderFinish(RefreshHeader header, boolean success) {

    }

    @Override
    public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

    }

//    @Override
//    public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int maxDragHeight) {
//
//    }

    @Override
    public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }

//    @Override
//    public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int maxDragHeight) {
//
//    }

    @Override
    public void onFooterStartAnimator(RefreshFooter footer, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterFinish(RefreshFooter footer, boolean success) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

}
