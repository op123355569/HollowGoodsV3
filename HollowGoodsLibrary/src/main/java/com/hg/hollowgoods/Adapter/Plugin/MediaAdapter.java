package com.hg.hollowgoods.Adapter.Plugin;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.FileSelectorUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.Glide.GlideOptions;

import java.io.File;
import java.util.List;

/**
 * 多媒体预览适配器
 * Created by HG on 2018-06-15.
 */
public class MediaAdapter extends CommonAdapter<Media> {

    private OnViewClickListener onViewClickListener;
    private FileSelectorUtils fileSelectorUtils;

    public MediaAdapter(Context context, int layoutId, List<Media> data, OnViewClickListener onViewClickListener) {
        super(context, layoutId, data);
        this.onViewClickListener = onViewClickListener;
        fileSelectorUtils = new FileSelectorUtils();
    }

    @Override
    protected void convert(ViewHolder viewHolder, Media item, final int position) {


        if (item.getFile() == null && TextUtils.isEmpty(item.getUrl())) {
            viewHolder.setText(R.id.tv_name, R.string.load_error);
            viewHolder.setImageResource(R.id.iv_img, HGCommonResource.IMAGE_LOAD_ERROR);
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

            viewHolder.setText(R.id.tv_name, name);

            if (FileUtils.isImageFile(url)) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(HGCommonResource.IMAGE_LOADING)
                        .error(HGCommonResource.IMAGE_LOAD_ERROR)
                        .centerCrop();
                GlideOptions glideOptions = new GlideOptions(url, null, GlideOptions.NORMAL_FADE_IN, requestOptions);
                glideOptions.setLoadType(HGConstants.IMG_LOAD_TYPE_IMAGE_PRE_ACTIVITY_SMALL);

                viewHolder.setImageByUrl(R.id.iv_img, glideOptions);
            } else if (FileUtils.isOfficeFile(url)) {
                viewHolder.setImageResource(R.id.iv_img, fileSelectorUtils.getFileIcon(new File(url)));
            } else {
                viewHolder.setImageResource(R.id.iv_img, R.drawable.ic_file_ex_other);
            }
        }

        viewHolder.setVisible(R.id.iv_delete, item.isCanRemove());
        viewHolder.setOnClickListener(R.id.iv_delete, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                onViewClickListener.onViewClick(view, position);
            }
        });
    }

}
