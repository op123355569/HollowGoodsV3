package com.hg.hollowgoods.Util.Glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.FormatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Glide加载监听(ImageView专用)
 * Created by Hollow Goods on unknown.
 */

public class GlideDrawableTarget extends DrawableImageViewTarget {

    private String saveName;
    private String savePath = HGSystemConfig.getPhotoCachePath();
    private boolean isPng;
    private boolean isNeedCache;
    private GlideOptions glideOptions;

    public GlideDrawableTarget(GlideOptions glideOptions, boolean isNeedCache) {

        super((ImageView) glideOptions.getLoadView());
        this.glideOptions = glideOptions;
        this.isNeedCache = isNeedCache;

        saveName = GlideUtils.getSaveName(glideOptions);
        isPng = glideOptions.getLoadByUrl().toLowerCase().contains("png");
    }

    @Override
    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {

        view.setScaleType(ImageView.ScaleType.FIT_XY);
        super.onResourceReady(resource, transition);

        Drawable drawable;
        Bitmap bitmap;

        if (resource != null) {
            drawable = resource.getCurrent().getConstantState().newDrawable();
            bitmap = FormatUtils.drawable2Bitmap(drawable);

            if (isNeedCache) {
                new Thread(new CacheImgThread(bitmap)).start();
            }
        }

        if (glideOptions.getGlideLoadImgListener() != null) {
            glideOptions.getGlideLoadImgListener().onImgLoadSuccess(
                    glideOptions.getLoadView(),
                    resource.getCurrent().getConstantState().newDrawable()
            );
        }
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {

        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        super.onLoadFailed(errorDrawable);

        if (glideOptions.getGlideLoadImgListener() != null) {
            glideOptions.getGlideLoadImgListener().onImgLoadFail(glideOptions.getLoadView());
        }
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        super.onLoadCleared(placeholder);
    }

    private class CacheImgThread implements Runnable {

        private Bitmap b;

        public CacheImgThread(Bitmap b) {
            this.b = b;
        }

        @Override
        public void run() {
            savePhotoToSDCard(b, savePath, saveName, isPng);
        }
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param path
     * @param name
     */
    private void savePhotoToSDCard(Bitmap bitmap, String path, String name, boolean isPng) {

        FileOutputStream b = null;

        File file = new File(path);
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        String fileName = path + name;

        try {
            b = new FileOutputStream(fileName);

            if (isPng) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
