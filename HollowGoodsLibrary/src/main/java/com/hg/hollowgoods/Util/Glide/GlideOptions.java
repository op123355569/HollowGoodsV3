package com.hg.hollowgoods.Util.Glide;

import android.view.View;

import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Glide加载图片配置<p>
 * RequestOptions options = new RequestOptions()<br>
 * .centerCrop()<br>
 * .placeholder(R.mipmap.ic_launcher)<br>
 * .error(R.mipmap.ic_launcher)<br>
 * .priority(Priority.HIGH)<br>
 * .transform(new GlideRoundTransform())<br>
 * .override(20, 20);<p>
 * Created by Hollow Goods on unknown.
 */
public class GlideOptions {

    /**
     * 未设置资源
     */
    public static final int NO_SETTING_RES = -1;
    /**
     * 未设置链接或文字
     */
    public static final String NO_SETTING_STRING = "";
    /**
     * 不需要渐变加载
     */
    public static final int NO_FADE_IN = -1;
    /**
     * 最常用的渐变加载时间
     */
    public static final int NORMAL_FADE_IN = 500;
    /**
     * 直接加载原图
     */
    public static final float NO_THUMBNAIL = 1f;

    /*****************************************************************/

    /**
     * 加载图片监听
     */
    private GlideLoadImgListener glideLoadImgListener = null;
    /**
     * 通过链接加载图片
     */
    private String loadByUrl = NO_SETTING_STRING;
    /**
     * 通过资源加载图片
     */
    private int loadByRes = NO_SETTING_RES;
    /**
     * 通过文件加载图片
     */
    private File loadByFile = null;
    /**
     * 填充图片的控件
     */
    private View loadView = null;
    /**
     * 显示图片是否需要过渡动画
     */
    private int fadeInDuration = NO_FADE_IN;
    /**
     * 请求配置
     */
    private RequestOptions requestOptions = null;
    /**
     * 加载时先加载缩略图（原图的百分比）
     */
    private float thumbnail = NO_THUMBNAIL;
    /**
     * 是否永远不缓存
     */
    private boolean isNeverCache = false;
    /**
     * 加载类型 用于区分正常加载图、圆角图、圆形图的缓存
     */
    private int loadType = 1;
    /**
     * 是否是Gif图
     */
    private boolean isGif = false;

    /******************************************************************************/
    /**** 构造方法 ****/

    public GlideOptions() {

    }

    public GlideOptions(Object loadSource, View loadView, int fadeInDuration, RequestOptions requestOptions) {

        if (loadSource instanceof String) {
            this.loadByUrl = (String) loadSource;
        } else if (loadSource instanceof File) {
            this.loadByFile = (File) loadSource;
        } else if (loadSource instanceof Integer) {
            this.loadByRes = (int) loadSource;
        }

        this.loadView = loadView;
        this.fadeInDuration = fadeInDuration;
        this.requestOptions = requestOptions;
    }

    public GlideOptions(Object loadSource, View loadView, RequestOptions requestOptions) {
        this(loadSource, loadView, NO_FADE_IN, requestOptions);
    }

    public GlideOptions(Object loadSource, RequestOptions requestOptions) {
        this(loadSource, null, NO_FADE_IN, requestOptions);
    }

    public GlideOptions(Object loadSource) {
        this(loadSource, null, NO_FADE_IN, null);
    }

    public void setGlideLoadImgListener(GlideLoadImgListener glideLoadImgListener) {
        this.glideLoadImgListener = glideLoadImgListener;
    }

    /**** Set方法 ****/

    public GlideOptions setLoadByUrl(String loadByUrl) {
        this.loadByUrl = loadByUrl;
        return this;
    }

    public GlideOptions setLoadByRes(int loadByRes) {
        this.loadByRes = loadByRes;
        return this;
    }

    public GlideOptions setLoadByFile(File loadByFile) {
        this.loadByFile = loadByFile;
        return this;
    }

    public GlideOptions setLoadView(View loadView) {
        this.loadView = loadView;
        return this;
    }

    public GlideOptions setFadeInDuration(int fadeInDuration) {
        this.fadeInDuration = fadeInDuration;
        return this;
    }

    public GlideOptions setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        return this;
    }

    public GlideOptions setNeverCache(boolean neverCache) {
        isNeverCache = neverCache;
        return this;
    }

    public GlideOptions setThumbnail(float thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public GlideOptions setLoadType(int loadType) {
        this.loadType = loadType;
        return this;
    }

    public GlideOptions setGif(boolean gif) {
        isGif = gif;
        return this;
    }

    /**** Get方法 ****/

    public GlideLoadImgListener getGlideLoadImgListener() {
        return glideLoadImgListener;
    }

    public String getLoadByUrl() {
        return loadByUrl;
    }

    public int getLoadByRes() {
        return loadByRes;
    }

    public File getLoadByFile() {
        return loadByFile;
    }

    public View getLoadView() {
        return loadView;
    }

    public int getFadeInDuration() {
        return fadeInDuration;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public boolean isNeverCache() {
        return isNeverCache;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public int getLoadType() {
        return loadType;
    }

    public boolean isGif() {
        return isGif;
    }

}
