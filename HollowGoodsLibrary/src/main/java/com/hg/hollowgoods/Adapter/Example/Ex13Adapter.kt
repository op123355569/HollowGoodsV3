package com.hg.hollowgoods.Adapter.Example

import android.content.Context
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter
import com.hg.hollowgoods.Bean.Example.Ex13_1
import com.hg.hollowgoods.R

/**
 * 示例13适配器
 * Created by Hollow Goods 2018-03-22.
 */
class Ex13Adapter(context: Context, layoutId: Int, data: List<Ex13_1>) : CommonAdapter<Ex13_1>(context, layoutId, data) {

    override fun convert(viewHolder: ViewHolder, item: Ex13_1, position: Int) {
        viewHolder.setText(R.id.tvName, item.name)
        viewHolder.setText(R.id.tvPrecent, item.postition.toString() + " /" + mData.size)
    }

}