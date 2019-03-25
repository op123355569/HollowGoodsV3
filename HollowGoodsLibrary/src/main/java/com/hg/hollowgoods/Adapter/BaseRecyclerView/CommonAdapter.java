package com.hg.hollowgoods.Adapter.BaseRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;

import java.util.List;

/**
 * Created by HG
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

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
