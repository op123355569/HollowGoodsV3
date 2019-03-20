package com.hg.hollowgoods.Adapter.BaseRecyclerView;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegateManager;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Helper.ItemTouchHelperAdapter;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Helper.OnStartDragListener;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Util.FullSpanUtil;
import com.hg.hollowgoods.Constant.HGConstants;

import java.util.Collections;
import java.util.List;

/**
 * Created by HG
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperAdapter {

    protected Context mContext;
    protected List<T> mData;

    @SuppressWarnings("rawtypes")
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    @SuppressWarnings("rawtypes")
    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager())
            return super.getItemViewType(position);

        int result = mItemViewDelegateManager.getItemViewType(mData.get(position), position);
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    @SuppressWarnings("unchecked")
    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType))
            return;
        viewHolder.getConvertView().setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                mOnItemClickListener.onItemClick(v, viewHolder, position);
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(v -> {
            if (mOnItemClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
            }
            return false;
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        convert(holder, mData.get(position));
        if (mDragStartListener != null && dragViewId != -1) {
            holder.getView(dragViewId).setOnTouchListener((v, event) -> {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mData.size();
        return itemCount;
    }

    public List<T> getData() {
        return mData;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, HGConstants.LIST_ITEM_TYPE_HEADER);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {

        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, HGConstants.LIST_ITEM_TYPE_HEADER);
    }

    public void refreshData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void refreshData(int oldSize, List<T> data) {

        int newSize = data.size();

        if (oldSize > newSize) {
            removeData(data, newSize, oldSize - newSize);
        } else if (newSize > oldSize) {
            addData(data, oldSize, newSize - oldSize);
        } else {
            mData = data;
            notifyItemRangeChanged(0, data.size());
        }
    }

    public void refreshData(List<T> data, int position) {

        mData = data;
        notifyItemChanged(position);
    }

    public void refreshData(List<T> data, int position, int size) {

        mData = data;
        notifyItemRangeChanged(position, size);
    }

    public void addData(List<T> data, int position, int itemCount) {

        mData = data;

        notifyItemRangeInserted(position, itemCount);
        notifyItemRangeChanged(0, data.size());
    }

    public void removeData(List<T> data, int position, int itemCount) {

        mData = data;

        notifyItemRangeRemoved(position, itemCount);
        notifyItemRangeChanged(0, data.size());
    }

    private OnStartDragListener mDragStartListener = null;
    private int dragViewId = -1;

    public void setOnStartDragListener(OnStartDragListener mDragStartListener, int dragViewId) {
        this.mDragStartListener = mDragStartListener;
        this.dragViewId = dragViewId;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {

        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMoveOver() {
        notifyItemRangeChanged(0, mData.size());
    }

}
