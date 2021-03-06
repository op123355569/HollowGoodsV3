package com.hg.hollowgoods.Adapter.Main

import android.view.View
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter
import com.hg.hollowgoods.Exception.ExceptionLog
import com.hg.hollowgoods.R
import com.hg.hollowgoods.UI.Activity.Main.HGFeedbackActivity
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener
import com.hg.hollowgoods.Util.StringUtils

/**
 * 反馈中心适配器
 * Created by Hollow Goods 2018-04-26.
 */
class HGFeedbackAdapter(context: HGFeedbackActivity?, layoutId: Int, data: List<ExceptionLog>?) : CommonAdapter<ExceptionLog>(context, layoutId, data) {

    private var activity: HGFeedbackActivity? = context

    override fun convert(viewHolder: ViewHolder?, item: ExceptionLog?, position: Int) {

        viewHolder?.setText(R.id.number, "${position + 1}")
        viewHolder?.setText(R.id.time, getName(item?.time!!))
        if (item?.uploadStatus!!) {
            viewHolder?.setText(R.id.reported, R.string.had_reported)
            viewHolder?.setBackgroundRes(R.id.reported, R.drawable.button_right_color)
        } else {
            viewHolder?.setText(R.id.reported, R.string.reported)
            viewHolder?.setBackgroundRes(R.id.reported, R.drawable.button_main_color)
        }

        viewHolder?.setClickable(R.id.reported, !item.uploadStatus)
        viewHolder?.setEnabled(R.id.reported, !item.uploadStatus)
        viewHolder?.setOnClickListener(R.id.reported, object : OnViewClickListener(false) {
            override fun onViewClick(view: View?, id: Int) {
                activity?.uploadException(position)
            }
        })
    }

    private fun getName(time: Long): String {
        return StringUtils.getDateTimeString(time, StringUtils.DateFormatMode.LINE_YMDHMS)
    }

}