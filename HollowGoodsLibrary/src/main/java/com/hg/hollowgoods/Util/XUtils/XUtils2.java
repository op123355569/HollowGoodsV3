package com.hg.hollowgoods.Util.XUtils;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Bean.CommonBean.KeyValue;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Util.LogUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

/**
 * XUtils工具类第二代
 * <p>
 * 改动点：改为链式调用
 * <p>
 * Created by Hollow Goods on unknown.
 */
public class XUtils2 {

    /**
     * 初始化，XUtils和XUtils2的初始化方法随便调哪个
     *
     * @param application application
     */
    public static void init(Application application) {
        XUtils.init(application);
    }

    public static class BuilderLoadImage {

        /**
         * 获取图片监听
         **/
        private LoadImageListener loadImageListener = null;

        /**
         * 设置获取图片监听
         *
         * @param loadImageListener loadImageListener
         */
        public BuilderLoadImage setLoadImageListener(LoadImageListener loadImageListener) {
            this.loadImageListener = loadImageListener;
            return this;
        }

        /**
         * 获取图片
         *
         * @param loadImageOptions 获取图片配置
         */
        public void loadImageByUrl(LoadImageOptions loadImageOptions) {

            ImageOptions imageOptions = new ImageOptions.Builder()//
                    .setSize(DensityUtil.dip2px(loadImageOptions.widthDp),
                            DensityUtil.dip2px(loadImageOptions.heightDp))// 图片大小
                    .setRadius(DensityUtil.dip2px(loadImageOptions.radiusDp))// ImageView圆角半径
                    .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setImageScaleType(loadImageOptions.scaleType)//
                    .setLoadingDrawableId(loadImageOptions.loadIconRes)// 加载中默认显示图片
                    .setFailureDrawableId(loadImageOptions.failIconRes)// 加载失败后默认显示图片
                    .setFadeIn(loadImageOptions.isFadeIn)// 加载图片完成后，显示图片时是否渐变显示
                    .setCircular(loadImageOptions.isCircular)// 是否显示圆形图片
                    .build();

            // TODO 缓存地址
            String cachePath = HGSystemConfig.getPhotoCachePath();
            // 缓存名
            String cacheName = EncryptUtils.md5Encrypt(loadImageOptions.url + loadImageOptions.widthDp + loadImageOptions.heightDp);
            // 是否从网络加载
            boolean isFromNet = true;

            // 判断图片是否有缓存
            // 有缓存则从本地加载
            // 否则从网络加载
            File file = new File(cachePath, cacheName);
            if (file.exists()) {
                loadImageOptions.url = cachePath + cacheName;
                isFromNet = false;
            }

            if (!loadImageOptions.url.startsWith("http:")) {
                isFromNet = false;
            }

            boolean isPng;
            if (loadImageOptions.url.contains("png") || loadImageOptions.url.contains("PNG")) {
                isPng = true;
            } else {
                isPng = false;
            }

            // 判断要加载的控件是否为ImageView
            if (loadImageOptions.view == null || !(loadImageOptions.view instanceof ImageView)) {
                x.image().loadDrawable(loadImageOptions.url, imageOptions,
                        new LoadImageCallBack(loadImageOptions.url, isFromNet, cachePath, cacheName, loadImageOptions.view, isPng));
            } else {
                x.image().bind((ImageView) (loadImageOptions.view), loadImageOptions.url, imageOptions,
                        new LoadImageCallBack(loadImageOptions.url, isFromNet, cachePath, cacheName, loadImageOptions.view, isPng));
            }
        }


        /**
         * 获取图片返回结果
         * <p>
         * Created by Hollow Goods on unknown.
         */
        private class LoadImageCallBack implements Callback.ProgressCallback<Drawable> {

            private String url;
            private boolean isFromNet;
            private String cachePath;
            private String cacheName;
            private View view;
            private boolean isPng;

            LoadImageCallBack(String url, boolean isFromNet, String cachePath, String cacheName, View view, boolean isPng) {
                this.url = url;
                this.isFromNet = isFromNet;
                this.cachePath = cachePath;
                this.cacheName = cacheName;
                this.view = view;
                this.isPng = isPng;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.LogRequest(
                        url,
                        cex == null ? "" : cex.getMessage()
                );
                if (loadImageListener != null) {
                    loadImageListener.onLoadCancel(view, cex);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.LogRequest(
                        url,
                        ex == null ? "" : ex.getMessage()
                );
                if (loadImageListener != null) {
                    loadImageListener.onLoadError(view, ex);
                }
            }

            @Override
            public void onFinished() {
                if (loadImageListener != null) {
                    loadImageListener.onLoadFinish(view);
                }
            }

            @Override
            public void onSuccess(Drawable drawable) {
                if (isFromNet && drawable != null) {// 从网络获取图片
                    BitmapDrawable bd = (BitmapDrawable) drawable;
                    Bitmap bitmap = bd.getBitmap();

                    if (loadImageListener != null) {
                        loadImageListener.onLoadSuccess(view, bitmap);
                    }

                    new Thread(() -> FormatUtils.savePhoto(bitmap, cachePath, cacheName, 100, isPng)).start();
                } else {// 从本地获取图片
                    if (loadImageListener != null) {
                        BitmapDrawable bd = (BitmapDrawable) drawable;
                        Bitmap bitmap = bd.getBitmap();

                        loadImageListener.onLoadSuccess(view, bitmap);
                    }
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isLoading) {
                if (loadImageListener != null) {
                    loadImageListener.onLoadLoading(view, total, current);
                }
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {

            }
        }
    }

    public static class BuilderGetHttpData {

        /**
         * 获取网络数据监听
         **/
        private GetHttpDataListener getHttpDataListener = null;
        private Callback.Cancelable getHttpCancelable = null;

        /**
         * 设置获取网络数据监听
         *
         * @param getHttpDataListener getHttpDataListener
         */
        public BuilderGetHttpData setGetHttpDataListener(GetHttpDataListener getHttpDataListener) {
            this.getHttpDataListener = getHttpDataListener;
            return this;
        }

        /**
         * 获取网络数据
         *
         * @param httpMethod 请求方式
         * @param params     请求参数
         */
        public void getHttpData(HttpMethod httpMethod, RequestParams params) {

            String url = params.toString();
            LogUtils.LogRequest(url, "开始请求……");

            if (TextUtils.isEmpty(url)) {
                if (getHttpDataListener != null) {
                    Application application = ApplicationBuilder.create();
                    getHttpDataListener.onGetError(new Throwable(application.getString(R.string.no_set_url)));
                }
            } else {
                cancelGetHttpData();
                // TODO 设置连接超时时间
                params.setConnectTimeout(HGSystemConfig.TIME_OUT);
                getHttpCancelable = x.http().request(httpMethod, params, new GetHttpCallBack(params));
            }
        }

        /**
         * 取消获取网络数据
         */
        public void cancelGetHttpData() {
            if (getHttpCancelable != null) {
                getHttpCancelable.cancel();
                getHttpCancelable = null;
            }
        }

        /**
         * 获取网络数据返回结果
         * <p>
         * Created by Hollow Goods on unknown.
         */
        private class GetHttpCallBack implements Callback.ProgressCallback<String> {

            private RequestParams params;

            GetHttpCallBack(RequestParams params) {
                this.params = params;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.LogRequest(
                        params.toString(),
                        cex == null ? "" : cex.getMessage()
                );
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetCancel(cex);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.LogRequest(
                        params.toString(),
                        ex == null ? "" : ex.getMessage()
                );
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetError(ex);
                }
            }

            @Override
            public void onFinished() {
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetFinish();
                }
            }

            @Override
            public void onSuccess(String result) {
                LogUtils.LogRequest(
                        params.toString(),
                        result
                );
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetSuccess(result);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isLoading) {
                if (getHttpDataListener != null) {
                    getHttpDataListener.onGetLoading(total, current);
                }
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {

            }

        }
    }

    public static class BuilderDownloadFile {

        /**
         * 下载文件监听
         **/
        private DownloadListener downloadListener = null;
        private Callback.Cancelable downloadCancelable = null;

        /**
         * 设置下载文件监听
         *
         * @param downloadListener downloadListener
         */
        public BuilderDownloadFile setDownloadListener(DownloadListener downloadListener) {
            this.downloadListener = downloadListener;
            return this;
        }

        /**
         * 下载文件
         *
         * @param httpMethod 请求方式
         * @param params     请求参数
         * @param path       保存路径
         */
        public void downloadFile(HttpMethod httpMethod, RequestParams params, String path) {

            String url = params.toString();
            LogUtils.LogRequest(url, "开始请求……");

            if (TextUtils.isEmpty(url)) {
                if (downloadListener != null) {
                    Application application = ApplicationBuilder.create();
                    downloadListener.onDownloadError(new Throwable(application.getString(R.string.no_set_url)));
                }
            } else {
                cancelDownloadFile();
                // TODO 设置连接超时时间
                params.setConnectTimeout(HGSystemConfig.TIME_OUT);

                if (TextUtils.isEmpty(path)) {
                    // 设置下载保存路径
                    params.setSaveFilePath(HGSystemConfig.getDownloadFilePath());
                } else {
                    // 设置下载保存路径
                    params.setSaveFilePath(path);
                }
                // 自动命名
                params.setAutoRename(true);
                // 自动断点下载
                params.setAutoResume(true);

                downloadCancelable = x.http().request(httpMethod, params, new DownloadCallBack(params));
            }
        }

        /**
         * 下载文件
         *
         * @param httpMethod 请求方式
         * @param params     请求参数
         */
        public void downloadFile(HttpMethod httpMethod, RequestParams params) {
            downloadFile(httpMethod, params, "");
        }

        /**
         * 取消下载文件
         */
        public void cancelDownloadFile() {
            if (downloadCancelable != null) {
                downloadCancelable.cancel();
                downloadCancelable = null;
            }
        }

        /**
         * 下载文件返回结果
         * <p>
         * Created by Hollow Goods on unknown.
         */
        private class DownloadCallBack implements Callback.ProgressCallback<File> {

            private RequestParams params;

            DownloadCallBack(RequestParams params) {
                this.params = params;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.LogRequest(
                        params.toString(),
                        cex == null ? "" : cex.getMessage()
                );
                if (downloadListener != null) {
                    downloadListener.onDownloadCancel(cex);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.LogRequest(
                        params.toString(),
                        ex == null ? "" : ex.getMessage()
                );
                if (downloadListener != null) {
                    downloadListener.onDownloadError(ex);
                }
            }

            @Override
            public void onFinished() {
                if (downloadListener != null) {
                    downloadListener.onDownloadFinish();
                }
            }

            @Override
            public void onSuccess(File file) {
                LogUtils.LogRequest(
                        params.toString(),
                        file == null ? "" : file.getAbsolutePath()
                );
                if (downloadListener != null) {
                    downloadListener.onDownloadSuccess(file);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isLoading) {
                LogUtils.LogRequest(
                        params.toString(),
                        current + "/" + total
                );
                if (downloadListener != null) {
                    downloadListener.onDownloadLoading(total, current);
                }
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {

            }
        }
    }

    public static class BuilderUploadFile {

        /**
         * 上传文件监听
         **/
        private UploadListener uploadListener = null;
        private Callback.Cancelable uploadCancelable = null;

        /**
         * 设置上传文件监听
         *
         * @param uploadListener uploadListener
         */
        public BuilderUploadFile setUploadListener(UploadListener uploadListener) {
            this.uploadListener = uploadListener;
            return this;
        }

        /**
         * 上传文件
         *
         * @param params    params
         * @param filePaths filePaths
         */
        public void uploadFile(RequestParams params, ArrayList<KeyValue> filePaths) {

            String url = params.toString();
            LogUtils.LogRequest(url, "开始请求……");

            if (TextUtils.isEmpty(url)) {
                if (uploadListener != null) {
                    Application application = ApplicationBuilder.create();
                    uploadListener.onUploadError(new Throwable(application.getString(R.string.no_set_url)));
                }
            } else {
                cancelUploadFile();
                // TODO 设置连接超时时间
                params.setConnectTimeout(HGSystemConfig.TIME_OUT);
                params.setMultipart(true);

                if (filePaths != null) {
                    for (KeyValue t : filePaths) {
                        if (t.getValue() instanceof String) {
                            params.addBodyParameter(t.getKey(), new File((String) t.getValue()));
                        } else if (t.getValue() instanceof File) {
                            params.addBodyParameter(t.getKey(), (File) t.getValue());
                        }
                    }
                }

                uploadCancelable = x.http().post(params, new UploadCallBack(params));
            }
        }

        /**
         * 上传文件，默认key(file)
         *
         * @param params    params
         * @param filePaths filePaths
         */
        public void uploadFileDefault(RequestParams params, ArrayList<Object> filePaths) {

            ArrayList<KeyValue> paths = new ArrayList<>();
            // 文件上传默认的key
            String DEFAULT_UPLOAD_FILE_KEY = "file";

            if (filePaths != null) {
                for (Object t : filePaths) {
                    paths.add(new KeyValue(DEFAULT_UPLOAD_FILE_KEY, t));
                }
            }

            uploadFile(params, paths);
        }

        /**
         * 取消下载文件
         */
        public void cancelUploadFile() {
            if (uploadCancelable != null) {
                uploadCancelable.cancel();
                uploadCancelable = null;
            }
        }

        /**
         * 上传文件返回结果
         * <p>
         * Created by Hollow Goods on unknown.
         */
        private class UploadCallBack implements Callback.ProgressCallback<String> {

            private RequestParams params;

            UploadCallBack(RequestParams params) {
                this.params = params;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.LogRequest(
                        params.toString(),
                        cex == null ? "" : cex.getMessage()
                );
                if (uploadListener != null) {
                    uploadListener.onUploadCancel(cex);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.LogRequest(
                        params.toString(),
                        ex == null ? "" : ex.getMessage()
                );
                if (uploadListener != null) {
                    uploadListener.onUploadError(ex);
                }
            }

            @Override
            public void onFinished() {
                if (uploadListener != null) {
                    uploadListener.onUploadFinish();
                }
            }

            @Override
            public void onSuccess(String result) {
                LogUtils.LogRequest(
                        params.toString(),
                        result
                );
                if (uploadListener != null) {
                    uploadListener.onUploadSuccess(result);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isLoading) {
                LogUtils.LogRequest(
                        params.toString(),
                        current + "/" + total
                );
                if (uploadListener != null) {
                    uploadListener.onUploadLoading(total, current);
                }
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {

            }

        }
    }

}
