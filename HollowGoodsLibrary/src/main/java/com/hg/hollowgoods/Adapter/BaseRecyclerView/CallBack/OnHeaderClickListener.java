package com.hg.hollowgoods.Adapter.BaseRecyclerView.CallBack;

import android.view.View;

/**
 * 顶部标签点击监听
 * Created by Hollow Goods on unknown.
 */
public interface OnHeaderClickListener {

    void onHeaderClick(View view, int id, int position);

    void onHeaderLongClick(View view, int id, int position);

    void onHeaderDoubleClick(View view, int id, int position);

}
