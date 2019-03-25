package com.hg.hollowgoods.Adapter.BaseAdapterView

import android.content.Context
import com.hg.hollowgoods.Adapter.BaseAdapterView.Base.ItemViewDelegate

/**
 * AdapterView公共适配器
 * Created by HG on 2018-03-22.
 */
abstract class CommonAdapter<T>(context: Context, layoutId: Int, datas: List<T>) : MultiItemTypeAdapter<T>(context, datas) {

    init {
        addItemViewDelegate(object : ItemViewDelegate<T> {
            override fun getItemViewLayoutId(): Int {
                return layoutId
            }

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(viewHolder: ViewHolder, t: T, position: Int) {
                this@CommonAdapter.convert(viewHolder, t, position)
            }
        })
    }

    abstract override fun convert(viewHolder: ViewHolder, item: T, position: Int)

}