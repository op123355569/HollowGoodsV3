package com.hg.hollowgoods.Adapter.BaseRecyclerView.Base;

/**
 * Created by HG
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder viewHolder, T item, int position);

}
