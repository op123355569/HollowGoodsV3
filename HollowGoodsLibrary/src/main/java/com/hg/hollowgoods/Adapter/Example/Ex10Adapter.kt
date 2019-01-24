package com.hg.hollowgoods.Adapter.Example

import android.content.Context
import com.hg.hollowgoods.Adapter.BaseAdapterView.CommonAdapter
import com.hg.hollowgoods.Adapter.BaseAdapterView.ViewHolder
import com.hg.hollowgoods.R

/**
 * 示例10适配器
 * Created by HG on 2018-03-22.
 */
class Ex10Adapter(context: Context, layoutId: Int, datas: List<String>) : CommonAdapter<String>(context, layoutId, datas) {

    override fun convert(viewHolder: ViewHolder, item: String, position: Int) {
        viewHolder.setText(R.id.tv_txt, item)
    }

}