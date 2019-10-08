package com.hg.hollowgoods.Adapter.Example

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.graphics.Palette
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.hg.hollowgoods.Adapter.BaseAdapterView.CommonAdapter
import com.hg.hollowgoods.Adapter.BaseAdapterView.ViewHolder
import com.hg.hollowgoods.Constant.HGCommonResource
import com.hg.hollowgoods.R
import com.hg.hollowgoods.Util.FormatUtils
import com.hg.hollowgoods.Util.Glide.GlideLoadImgListener
import com.hg.hollowgoods.Util.Glide.GlideOptions

/**
 * 示例6适配器
 * Created by Hollow Goods 2018-03-22.
 */
class Ex6Adapter(context: Context, layoutId: Int, data: List<String>) : CommonAdapter<String>(context, layoutId, data) {

    override fun convert(viewHolder: ViewHolder, item: String, position: Int) {

        val options = RequestOptions()
                .centerCrop()
                .placeholder(HGCommonResource.IMAGE_LOADING)
                .error(HGCommonResource.IMAGE_LOAD_ERROR)
        val glideOptions = GlideOptions(item, null, GlideOptions.NORMAL_FADE_IN, options)
        glideOptions.thumbnail = 0.3f

        glideOptions.glideLoadImgListener = object : GlideLoadImgListener {
            override fun onImgLoadSuccess(v: View, drawable: Drawable) {

                Palette.from(FormatUtils.drawable2Bitmap(drawable)).generate { palette ->
                    val vibrant = palette.darkVibrantSwatch
                    if (vibrant != null) {
                        // If we have a vibrant color
                        // update the title TextView
                        viewHolder.setViewBackground(R.id.tv_txt, vibrant.rgb)
                        viewHolder.setTextColor(R.id.tv_txt, vibrant.titleTextColor)
                    }
                }
            }

            override fun onImgLoadFail(v: View) {

            }
        }
        viewHolder.setImageByUrl(R.id.iv_img, glideOptions)
    }

}