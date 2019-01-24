package com.hg.hollowgoods.Util.Glide;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FileUtils;

import java.io.File;

/**
 * Glide库工具类<br>
 * 加载圆角图片时不要对ImageView设置ScaleType
 * Created by HG
 */

public class GlideUtils {

    /**
     * 加载图片
     *
     * @param context      Activity/Fragment/Context
     * @param glideOptions
     */
    public static void loadImg(Object context, GlideOptions glideOptions) {

        RequestBuilder requestBuilder = null;
        boolean isNeedCache = false;

        if (glideOptions.isGif()) {
            if (context instanceof Activity) {
                requestBuilder = Glide.with((Activity) context).asGif();
            } else if (context instanceof Fragment) {
                requestBuilder = Glide.with((Fragment) context).asGif();
            } else if (context instanceof Context) {
                requestBuilder = Glide.with((Context) context).asGif();
            }
        } else {
            if (context instanceof Activity) {
                requestBuilder = Glide.with((Activity) context).asDrawable();
            } else if (context instanceof Fragment) {
                requestBuilder = Glide.with((Fragment) context).asDrawable();
            } else if (context instanceof Context) {
                requestBuilder = Glide.with((Context) context).asDrawable();
            }
        }

        if (requestBuilder != null) {
            if (!GlideOptions.NO_SETTING_STRING.equals(glideOptions.getLoadByUrl()) && glideOptions.getLoadByUrl() != null) {
                String savePath = HGSystemConfig.getPhotoCachePath();
                String saveName = getSaveName(glideOptions);
                String loadPath = "";

                if (!glideOptions.isNeverCache() && (FileUtils.checkFileExist2(loadPath = glideOptions.getLoadByUrl()) || FileUtils.checkFileExist2(loadPath = (savePath + saveName)))) {
                    glideOptions.setLoadByFile(new File(loadPath));
                    requestBuilder.load(glideOptions.getLoadByFile());
                    isNeedCache = false;
                } else {
                    requestBuilder.load(glideOptions.getLoadByUrl());
                    isNeedCache = true;
                }
            } else if (glideOptions.getLoadByRes() != GlideOptions.NO_SETTING_RES) {
                requestBuilder.load(glideOptions.getLoadByRes());
                isNeedCache = false;
            } else if (glideOptions.getLoadByFile() != null) {
                requestBuilder.load(glideOptions.getLoadByFile());
                isNeedCache = false;
            } else {
                requestBuilder.load(GlideOptions.NO_SETTING_STRING);
                isNeedCache = false;
            }

            if (glideOptions.getRequestOptions() != null) {
                requestBuilder.apply(glideOptions.getRequestOptions());
            }

            if (glideOptions.getFadeInDuration() > 0) {
                requestBuilder.transition(new DrawableTransitionOptions().crossFade(glideOptions.getFadeInDuration()));
            }

            if (glideOptions.getThumbnail() > 0f && glideOptions.getThumbnail() < 1f) {
                requestBuilder.thumbnail(glideOptions.getThumbnail());
            }

            requestBuilder.into(
                    (glideOptions.getLoadView() != null && glideOptions.getLoadView() instanceof ImageView)
                            ? (new GlideDrawableTarget(glideOptions, glideOptions.isNeverCache() ? false : isNeedCache))
                            : (new GlideSimpleTarget(glideOptions, glideOptions.isNeverCache() ? false : isNeedCache))
            );
        }
    }

    /**
     * 暂停加载图片(用于列表滑动进行时)
     *
     * @param context
     */
    public static void pauseLoadImg(Object context) {

        if (context instanceof Activity) {
            Glide.with((Activity) context).pauseRequests();
        } else if (context instanceof Fragment) {
            Glide.with((Fragment) context).pauseRequests();
        } else if (context instanceof Context) {
            Glide.with((Context) context).pauseRequests();
        }
    }

    /**
     * 恢复加载图片(用于列表滑动完成后)
     *
     * @param context
     */
    public static void resumeLoadImg(Object context) {

        if (context instanceof Activity) {
            Glide.with((Activity) context).resumeRequests();
        } else if (context instanceof Fragment) {
            Glide.with((Fragment) context).resumeRequests();
        } else if (context instanceof Context) {
            Glide.with((Context) context).resumeRequests();
        }
    }

    /**
     * 获取缓存图片的文件名
     *
     * @param glideOptions
     * @return
     */
    public static String getSaveName(GlideOptions glideOptions) {

        StringBuilder builder = new StringBuilder();

        builder.append(glideOptions.getLoadByUrl());
        if (glideOptions.getRequestOptions() != null) {
            builder.append(glideOptions.getRequestOptions().getOverrideWidth());
            builder.append(glideOptions.getRequestOptions().getOverrideHeight());
        }
        builder.append(glideOptions.getLoadType());

        return EncryptUtils.md5Encrypt(builder.toString());
    }

}
