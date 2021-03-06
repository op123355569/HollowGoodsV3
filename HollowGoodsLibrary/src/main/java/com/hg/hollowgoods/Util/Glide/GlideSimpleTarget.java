package com.hg.hollowgoods.Util.Glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Util.ThreadPoolUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Glide加载监听(无控件或非ImageView)
 * Created by Hollow Goods on unknown.
 */

public class GlideSimpleTarget extends CustomTarget {

    private String saveName;
    private String savePath = HGSystemConfig.getPhotoCachePath();
    private boolean isPng;
    private GlideOptions glideOptions;
    private boolean isNeedCache;

    public GlideSimpleTarget(GlideOptions glideOptions, boolean isNeedCache) {

        this.glideOptions = glideOptions;
        this.isNeedCache = isNeedCache;

        saveName = GlideUtils.getSaveName(glideOptions);
        isPng = glideOptions.getLoadByUrl().toLowerCase().contains("png");
    }

    @Override
    public void onResourceReady(final Object resource, Transition transition) {

        ThreadPoolUtils.getService().execute(() -> {
            Drawable drawable;
            Bitmap bitmap;

            if (resource != null) {
                drawable = ((Drawable) resource).getCurrent().getConstantState().newDrawable();
                bitmap = FormatUtils.drawable2Bitmap(drawable);

                if (isNeedCache) {
                    ThreadPoolUtils.getService().execute(() -> new CacheImgThread(bitmap));
                }
            }

            if (glideOptions.getGlideLoadImgListener() != null) {
                glideOptions.getGlideLoadImgListener().onImgLoadSuccess(
                        glideOptions.getLoadView(),
                        ((Drawable) resource).getCurrent().getConstantState().newDrawable()
                );
            }
        });
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);

        if (glideOptions.getGlideLoadImgListener() != null) {
            glideOptions.getGlideLoadImgListener().onImgLoadFail(glideOptions.getLoadView());
        }
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

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
