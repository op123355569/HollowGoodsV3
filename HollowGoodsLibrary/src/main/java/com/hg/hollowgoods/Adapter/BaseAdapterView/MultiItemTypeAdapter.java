package com.hg.hollowgoods.Adapter.BaseAdapterView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hg.hollowgoods.Adapter.BaseAdapterView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseAdapterView.Base.ItemViewDelegateManager;

import java.util.ArrayList;
import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;

    @SuppressWarnings({"rawtypes"})
    private ItemViewDelegateManager mItemViewDelegateManager;

    @SuppressWarnings({"rawtypes"})
    public MultiItemTypeAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(mData.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mData.get(position),
                position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }

        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    @SuppressWarnings({"unchecked"})
    protected void convert(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public void refreshData(ArrayList<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

}
