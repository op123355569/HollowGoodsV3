package com.hg.hollowgoods.Adapter.BaseRecyclerView.Wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Util.WrapperUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by HG
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    @SuppressWarnings("rawtypes")
    private RecyclerView.Adapter mInnerAdapter;

    @SuppressWarnings("rawtypes")
    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {

        if (isHeaderViewPos(position)) {
            return;
        }

        if (isFooterViewPos(position)) {
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, (layoutManager, oldLookup, position) -> {

            int viewType = getItemViewType(position);

            if (mHeaderViews.get(viewType) != null) {
                return layoutManager.getSpanCount();
            } else if (mFootViews.get(viewType) != null) {
                return layoutManager.getSpanCount();
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
        int position = holder.getLayoutPosition();

        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

}
