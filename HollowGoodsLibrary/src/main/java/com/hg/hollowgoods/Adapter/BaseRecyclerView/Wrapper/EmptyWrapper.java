package com.hg.hollowgoods.Adapter.BaseRecyclerView.Wrapper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Util.WrapperUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by HG
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    @SuppressWarnings("rawtypes")
    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;

    @SuppressWarnings("rawtypes")
    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (isEmpty()) {
            ViewHolder holder;
            if (mEmptyView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, (gridLayoutManager, oldLookup, position) -> {
            if (isEmpty()) {
                return gridLayoutManager.getSpanCount();
            }
            if (oldLookup != null) {
                return oldLookup.getSpanSize(position);
            }
            return 1;
        });

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(@NotNull RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty())
            return 1;
        return mInnerAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

}
