package com.hg.hollowgoods.Adapter.BaseRecyclerView;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;

import java.util.List;

/**
 * Created by HG
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(final Context context, final int layoutId, List<T> data) {
        super(context, data);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder viewHolder, T item, int position) {
                CommonAdapter.this.convert(viewHolder, item, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
