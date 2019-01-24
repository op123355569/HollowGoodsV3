package com.hg.hollowgoods.Adapter.Plugin;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.Glide.GlideOptions;

import java.util.List;

/**
 * Created by HG on 2018-06-15.
 */
public class MediaAdapter extends CommonAdapter<Media> {

    private OnViewClickListener onViewClickListener;

    public MediaAdapter(Context context, int layoutId, List<Media> datas, OnViewClickListener onViewClickListener) {
        super(context, layoutId, datas);
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Media item, final int position) {

        if (item.getFile() == null && TextUtils.isEmpty(item.getUrl())) {
            viewHolder.setText(R.id.tv_name, R.string.load_error);
            viewHolder.setImageResource(R.id.iv_img, CommonResource.IMAGE_LOAD_ERROR);
        } else {
            String name;
            String url;
            if (item.getFile() == null) {
                name = item.getOldName();
                url = item.getUrl();
            } else {
                name = item.getFile().getName();
                url = item.getFile().getAbsolutePath();
            }

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(CommonResource.IMAGE_LOADING)
                    .error(CommonResource.IMAGE_LOAD_ERROR)
                    .centerCrop();
            GlideOptions glideOptions = new GlideOptions(url, null, GlideOptions.NORMAL_FADE_IN, requestOptions);

            viewHolder.setText(R.id.tv_name, name);
            viewHolder.setImageByUrl(R.id.iv_img, glideOptions);
        }

        viewHolder.setOnClickListener(R.id.iv_delete, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                onViewClickListener.onViewClick(view, position);
            }
        });
    }

}
