package com.hg.hollowgoods.Adapter.Example

import android.content.Context
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter
import com.hg.hollowgoods.Bean.Example.Ex16
import com.hg.hollowgoods.R

/**
 * 示例16适配器
 * Created by HG on 2018-03-22.
 */
class Ex16Adapter(context: Context, layoutId: Int, data: List<Ex16>) : CommonAdapter<Ex16>(context, layoutId, data) {

    override fun convert(viewHolder: ViewHolder, item: Ex16, position: Int) {
        viewHolder.setText(R.id.tv_num, "" + (position + 1))
        viewHolder.setText(R.id.tv_content, item.txt)
    }

}