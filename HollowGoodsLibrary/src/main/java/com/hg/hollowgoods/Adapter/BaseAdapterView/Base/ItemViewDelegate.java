package com.hg.hollowgoods.Adapter.BaseAdapterView.Base;

import com.hg.hollowgoods.Adapter.BaseAdapterView.ViewHolder;

public interface ItemViewDelegate<T> {

	public abstract int getItemViewLayoutId();

	public abstract boolean isForViewType(T item, int position);

	public abstract void convert(ViewHolder holder, T t, int position);

}
