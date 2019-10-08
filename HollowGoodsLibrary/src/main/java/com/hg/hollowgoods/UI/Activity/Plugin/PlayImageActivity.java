package com.hg.hollowgoods.UI.Activity.Plugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.XUtils.LoadImageOptions;
import com.hg.hollowgoods.Util.XUtils.XUtils;
import com.hg.hollowgoods.Widget.PhotoView.PhotoView;

/**
 * 图片播放界面
 * <p>
 * Created by Hollow Goods on unknown.
 */

public class PlayImageActivity extends BaseActivity {

    private PhotoView img;

    private String url;

    @Override
    public int bindLayout() {
        return R.layout.activity_play_image;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        img = findViewById(R.id.iv_img);

        url = getIntent().getStringExtra(HGParamKey.URL.getValue());
        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.watch_picture);

//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(NoImageRes.IMAGE_LOADING)
//                .error(NoImageRes.IMAGE_LOAD_ERROR);
//        GlideOptions glideOptions = new GlideOptions(url, img, 300, options);
//        glideOptions.setThumbnail(0.1f);
//        GlideUtils.loadImg(this, glideOptions);

        XUtils xUtils = new XUtils();
        LoadImageOptions loadImageOptions = new LoadImageOptions(img, url, HGCommonResource.IMAGE_LOADING, HGCommonResource.IMAGE_LOAD_ERROR, 0, 0, 0, false, ImageView.ScaleType.FIT_CENTER);
        xUtils.loadImageByUrl(loadImageOptions);

        return null;
    }

    @Override
    public void setListener() {

    }

}
