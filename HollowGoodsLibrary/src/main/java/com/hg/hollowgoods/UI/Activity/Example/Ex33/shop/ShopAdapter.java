package com.hg.hollowgoods.UI.Activity.Example.Ex33.shop;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.R;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;

import java.util.List;

public class ShopAdapter extends CommonAdapter<Item> {

    private InfiniteScrollAdapter infiniteAdapter;

    public ShopAdapter(Context context, int layoutId, List<Item> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Item item, int position) {

        viewHolder.setImageResource(R.id.iv_img, item.getImage());

        if (infiniteAdapter != null) {
            viewHolder.setText(R.id.tv_number, (infiniteAdapter.getRealPosition(position) + 1) + "/" + mDatas.size());
        } else {
            viewHolder.setText(R.id.tv_number, "");
        }
    }

    public void setInfiniteAdapter(InfiniteScrollAdapter infiniteAdapter) {
        this.infiniteAdapter = infiniteAdapter;
    }

}
