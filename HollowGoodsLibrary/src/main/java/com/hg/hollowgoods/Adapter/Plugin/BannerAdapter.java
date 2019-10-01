package com.hg.hollowgoods.Adapter.Plugin;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Bean.Plugin.Banner;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.FileChangeUtils.FileChangeUtils;
import com.hg.hollowgoods.Util.FileChangeUtils.HGWebFile;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;

import java.util.List;

/**
 * Banner适配器
 * Created by Hollow Goods on 2019-07-03.
 */
public class BannerAdapter extends CommonAdapter<Banner> {

    private InfiniteScrollAdapter infiniteAdapter;
    private OnRecyclerViewItemClickListener onBannerClickListener;
    private FileChangeUtils<HGWebFile> fileChangeUtils;

    public BannerAdapter(Context context, int layoutId, List<Banner> data) {
        super(context, layoutId, data);
        fileChangeUtils = new FileChangeUtils<HGWebFile>() {
        };
    }

    @Override
    protected void convert(ViewHolder viewHolder, Banner item, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(HGCommonResource.IMAGE_LOADING)
                .error(HGCommonResource.IMAGE_LOAD_ERROR)
                .centerCrop();

        GlideOptions glideOptions;

        if (item.getFileList() != null && item.getFileList().size() > 0) {
            glideOptions = new GlideOptions(fileChangeUtils.webFile2AppFile(item.getFileList().get(0)).getUrl(), null, GlideOptions.NO_FADE_IN, requestOptions);
        } else {
            if (item.getRes() != null) {
                glideOptions = new GlideOptions(item.getRes(), null, GlideOptions.NO_FADE_IN, requestOptions);
            } else {
                glideOptions = new GlideOptions(HGCommonResource.IMAGE_LOAD_ERROR, null, GlideOptions.NO_FADE_IN, requestOptions);
            }
        }

        viewHolder.setImageByUrl(R.id.banner_img, glideOptions);

        viewHolder.setOnClickListener(R.id.banner_all, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (onBannerClickListener != null) {
                    onBannerClickListener.onRecyclerViewItemClick(view, viewHolder, infiniteAdapter.getRealPosition(position));
                }
            }
        });
    }

    public void setInfiniteAdapter(InfiniteScrollAdapter infiniteAdapter) {
        this.infiniteAdapter = infiniteAdapter;
    }

    public void setOnBannerClickListener(OnRecyclerViewItemClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }
}
