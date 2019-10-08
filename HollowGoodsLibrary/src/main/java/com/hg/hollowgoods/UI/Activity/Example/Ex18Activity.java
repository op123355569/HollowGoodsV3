package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.Glide.GlideUtils;
import com.hg.hollowgoods.Util.SystemAppUtils;

/**
 * 裁剪图片示例界面
 * Created by Hollow Goods on unknown.
 */

public class Ex18Activity extends BaseActivity {

    private ImageView img;

    private SystemAppUtils systemAppUtils;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_18;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        img = findViewById(R.id.iv_img);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex18);

        systemAppUtils = new SystemAppUtils();

        return null;
    }

    @Override
    public void setListener() {
        img.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                systemAppUtils.openAlbum(Ex18Activity.this, 1111, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1111:// 相册选图
                    systemAppUtils.onActivityResultForOpenAlbum(baseUI.getBaseContext(), data, new SystemAppUtils.OnCompressListener() {
                        @Override
                        public void onCompressSuccess() {
                            systemAppUtils.cropImage(baseUI.getBaseContext(), systemAppUtils.getAlbumPhotoPath(), 1, 1, 600, 600, 2222, 100);
                        }

                        @Override
                        public void onCompressError() {
                            t.showShortToast(R.string.photo_error);
                        }

                        @Override
                        public void onCompressFinish() {

                        }
                    });
                    break;
                case 2222:// 裁剪图片
                    systemAppUtils.onActivityResultForCropImage(this);

                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(HGCommonResource.IMAGE_LOADING)
                            .error(HGCommonResource.IMAGE_LOAD_ERROR)
                            .centerCrop();
                    GlideOptions glideOptions = new GlideOptions(systemAppUtils.getCropImagePath(), img, 500, requestOptions);
                    glideOptions.setThumbnail(0.1f);
                    GlideUtils.loadImg(this, glideOptions);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
