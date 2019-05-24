package com.hg.hollowgoods.Util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;

import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Plugin.FileReadActivity;
import com.hg.hollowgoods.UI.Activity.Plugin.FileSelectorActivity;
import com.hg.hollowgoods.UI.Activity.Plugin.ImagePreActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.PhotoPicter.Activity.BGAPhotoPickerActivity;
import com.hg.hollowgoods.Util.PhotoPicter.Activity.BGAPhotoPreviewActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;

/**
 * Android系统界面调用工具类
 * Created by HG on 2018-01-23.
 */

public class SystemAppUtils {

    /**
     * 获取文件的URI
     *
     * @param context  context
     * @param filepath filepath
     * @return Uri
     */
    public Uri getFileUri(Context context, String filepath) {
        return getFileUri(context, new File(filepath));
    }

    /**
     * 获取文件的URI
     *
     * @param context context
     * @param file    file
     * @return Uri
     */
    public Uri getFileUri(Context context, File file) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());

            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }

        return Uri.fromFile(file);
    }

    private final String photoSavePath = HGSystemConfig.getPhotoCachePath();
    public final String[] cameraPermissions = {
            Manifest.permission.CAMERA,
    };

    private String cameraPhotoName = "";
    private File cameraPhotoFile = null;
    private int cameraPhotoQuality;

    /**
     * 获取照片名称
     *
     * @return String
     */
    public String getCameraPhotoName() {
        return cameraPhotoName;
    }

    /**
     * 获取照片文件
     *
     * @return File
     */
    public File getCameraPhotoFile() {
        return cameraPhotoFile;
    }

    /**
     * 获取照片压缩质量
     *
     * @return int
     */
    public int getCameraPhotoQuality() {
        return cameraPhotoQuality;
    }

    /**
     * 调用系统相机拍摄照片
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param quality     压缩质量 0-100
     * @param isFront     是否默认打开前置摄像头
     */
    public void takePhoto(Activity activity, int requestCode, int quality, boolean isFront) {

        cameraPhotoQuality = quality;
        if (cameraPhotoQuality < 0) {
            cameraPhotoQuality = 0;
        }
        if (cameraPhotoQuality > 100) {
            cameraPhotoQuality = 100;
        }
        cameraPhotoName = System.currentTimeMillis() + ".jpg";
        cameraPhotoFile = new File(photoSavePath + cameraPhotoName);

        FileUtils.checkFileExist(photoSavePath);

        // 启动拍照,并保存到临时文件
        Intent takePhotoIntent = new Intent();
        takePhotoIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(activity, cameraPhotoFile));
        takePhotoIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        if (isFront) {
            // 调用前置摄像头
            takePhotoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        }
        activity.startActivityForResult(takePhotoIntent, requestCode);
    }

    /**
     * 调用系统相机拍摄照片返回处理
     *
     * @param activity activity
     * @return boolean
     */
    public boolean onActivityResultForTakePhoto(Activity activity) {

        // 拍照
        if (cameraPhotoFile == null) {
            return false;
        }

        String fileSrc = cameraPhotoFile.getAbsolutePath();
        updateGallery(activity);

        // 获取图片的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap img = BitmapFactory.decodeFile(fileSrc, options);

        // 压缩图片
        options.inSampleSize = Math.max(1,
                (int) Math.ceil(Math.max((double) options.outWidth / 1024f, (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        img = BitmapFactory.decodeFile(fileSrc, options);

        compressPhoto(img);

        return true;
    }

    /**
     * 扫描照片
     */
    private void updateGallery(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{cameraPhotoFile.getAbsolutePath()}, null, (path, uri) -> {
        });
    }

    /**
     * 压缩图片
     */
    private void compressPhoto(Bitmap b) {
        FormatUtils.savePhoto(b, photoSavePath, cameraPhotoName, cameraPhotoQuality, false);
    }

    private Uri albumPhotoUri = null;
    private String albumPhotoPath = "";

    public Uri getAlbumPhotoUri() {
        return albumPhotoUri;
    }

    public String getAlbumPhotoPath() {
        return albumPhotoPath;
    }

    /**
     * 打开系统相册
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public void openAlbum(Activity activity, int requestCode) {

        FileUtils.checkFileExist(photoSavePath);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开相册返回处理
     *
     * @param context context
     * @param data    data
     * @return boolean
     */
    public boolean onActivityResultForOpenAlbum(Context context, Intent data) {

        albumPhotoUri = data.getData();
        albumPhotoPath = getRealFilePath(context, albumPhotoUri);

        if (albumPhotoUri == null || albumPhotoPath == null) {
            return false;
        }

        return true;
    }

    /**
     * 从Uri中解析物理路径
     *
     * @param context context
     * @param uri     uri
     * @return String
     */
    public String getRealFilePath(Context context, Uri uri) {

        if (null == uri) {
            return null;
        }

        String scheme = uri.getScheme();
        String filepath = null;

        if (scheme == null) {
            filepath = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            filepath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        filepath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }

        return filepath;
    }

    private Uri cropImageUri = null;
    private String cropImagePath = "";

    public Uri getCropImageUri() {
        return cropImageUri;
    }

    public String getCropImagePath() {
        return cropImagePath;
    }

    /**
     * 调取裁剪图片界面
     *
     * @param activity        当前activity
     * @param sourceImagePath 剪裁原图的路径
     * @param aspectX         X方向的比例
     * @param aspectY         Y方向的比例
     * @param width           剪裁图片的宽度
     * @param height          剪裁图片高度
     * @param requestCode     剪裁图片的请求码
     * @param quality         图片压缩质量 0-100
     */
    public void cropImage(Activity activity, String sourceImagePath, int aspectX, int aspectY, int width, int height, int requestCode, int quality) {

        Uri sourceImageUri = Uri.fromFile(new File(sourceImagePath));
        boolean isPng = sourceImagePath.toLowerCase(Locale.getDefault()).endsWith(".png");

        FileUtils.checkFileExist(photoSavePath);

        cropImagePath = photoSavePath + System.currentTimeMillis() + (isPng ? ".png" : ".jpg");
        cropImageUri = Uri.fromFile(new File(cropImagePath));

        int background = activity.getResources().getColor(R.color.photo_manager_background);
        int colorAccent = activity.getResources().getColor(R.color.colorAccent);

        UCrop.Options options = new UCrop.Options();
        // 标题
        options.setToolbarTitle(activity.getString(R.string.cut_picture));
        // 状态栏颜色
        options.setStatusBarColor(background);
        // 标题栏颜色
        options.setToolbarColor(background);
        // 下方按钮颜色
        options.setActiveWidgetColor(colorAccent);
        // 主体背景颜色
        options.setDimmedLayerColor(background);
        // 压缩质量
        options.setCompressionQuality(quality);
        // 压缩格式
        options.setCompressionFormat(isPng ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG);

        UCrop.of(sourceImageUri, cropImageUri)
                .withAspectRatio(aspectX, aspectY)
                .withMaxResultSize(width, height)
                .withOptions(options)
                .start(activity, requestCode);
    }

    /**
     * 调取裁剪图片界面
     *
     * @param activity       当前activity
     * @param sourceImageUri 剪裁原图的Uri
     * @param aspectX        X方向的比例
     * @param aspectY        Y方向的比例
     * @param width          剪裁图片的宽度
     * @param height         剪裁图片高度
     * @param requestCode    剪裁图片的请求码
     * @param quality        图片压缩质量 0-100
     */
    public void cropImage(Activity activity, Uri sourceImageUri, int aspectX, int aspectY, int width, int height, int requestCode, int quality) {
        cropImage(activity, getRealFilePath(activity, sourceImageUri), aspectX, aspectY, width, height, requestCode, quality);
    }

    /**
     * 调取裁剪图片界面返回处理
     */
    public void onActivityResultForCropImage(Context context) {
        refreshGallery(context, cropImagePath);
    }

    /**
     * 刷新图库
     *
     * @param context context
     * @param path    path
     */
    private void refreshGallery(Context context, String path) {
        if (!TextUtils.isEmpty(path)) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(getFileUri(context, path));
            BGABaseAdapterUtil.getApp().sendBroadcast(mediaScanIntent);
        }
    }

    private final String videoSavePath = HGSystemConfig.getVideoCachePath();
    private String videoName = "";
    private File videoFile = null;

    public String getVideoName() {
        return videoName;
    }

    public File getVideoFile() {
        return videoFile;
    }

    /**
     * 调用系统相机拍摄视频
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param quality     质量0f-1f
     */
    public void takeVideo(Activity activity, int requestCode, float quality) {

        if (quality < 0f) {
            quality = 0f;
        }
        if (quality > 1f) {
            quality = 1f;
        }

        videoName = System.currentTimeMillis() + ".mp4";
        videoFile = new File(videoSavePath + videoName);

        FileUtils.checkFileExist(videoSavePath);

        // 启动视频,并保存到临时文件
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(activity, videoFile));
        mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality);

        activity.startActivityForResult(mIntent, requestCode);
    }

    /**
     * 调用系统相机拍摄视频返回处理
     *
     * @return boolean
     */
    public boolean onActivityResultForTakeVideo() {

        if (videoFile == null) {
            return false;
        }

        return true;
    }

    /**
     * 选择多张照片
     *
     * @param activity       activity
     * @param requestCode    requestCode
     * @param maxCount       最多选择的照片张数
     * @param selectedPhotos 已选择的照片
     */
    public void checkPhotos(Activity activity, int requestCode, int maxCount, ArrayList<String> selectedPhotos) {

        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(activity)
                .cameraFileDir(null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(maxCount) // 图片选择张数的最大值
                .selectedPhotos(selectedPhotos) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();

        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    /**
     * 选择多张照片返回处理
     *
     * @param data data
     * @return ArrayList<String>
     */
    public ArrayList<String> onActivityResultForCheckPhotos(Intent data) {
        return BGAPhotoPickerActivity.getSelectedPhotos(data);
    }

    /**
     * 图片预览
     *
     * @param context  context
     * @param photos   需要预览的图片的集合
     * @param position 预览图片的索引当前
     */
    public void previewPhotos(Context context, ArrayList<String> photos, int position) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(context)
                .saveImgDir(null); // 保存图片的目录，如果传 null，则没有保存图片功能

        if (photos.size() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(photos.get(0));
        } else if (photos.size() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(photos)
                    .currentPosition(position); // 当前预览图片的索引
        }

        context.startActivity(photoPreviewIntentBuilder.build());
    }

    /**
     * 图片预览
     *
     * @param context context
     * @param photo   photo
     */
    public void previewPhotos(Context context, final String photo) {
        previewPhotos(context, new ArrayList<String>() {
            {
                add(photo);
            }
        }, 0);
    }

    /**
     * 打开系统浏览器
     *
     * @param context context
     * @param url     url
     */
    public void openExplorer(Context context, String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        List<ResolveInfo> explorerList = context.getPackageManager().queryIntentActivities(intent, 0);

        if (explorerList != null && explorerList.size() > 0) {
            context.startActivity(intent);
        } else {
            t.error(R.string.please_setup_explorer_first);
        }
    }

    /**
     * 文件选择器
     *
     * @param context      context
     * @param maxCount     选择个数上限
     * @param fileFilter   过滤格式，如只要jpg和png格式，传 .jpg,.png
     * @param checkedFiles 已选文件</br>
     *                     返回 在onEventUI中返回，单文件是返回 File，多文件返回 HashMap<String, File>
     */
    public void checkFiles(Context context, int maxCount, String fileFilter, HashMap<String, File> checkedFiles) {

        Intent intent = new Intent(context, FileSelectorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(HGParamKey.MaxCount.getValue(), maxCount);
        if (!TextUtils.isEmpty(fileFilter)) {
            intent.putExtra(HGParamKey.FileFilter.getValue(), fileFilter);
        }
        if (checkedFiles != null) {
            intent.putExtra(HGParamKey.SelectedFile.getValue(), checkedFiles);
        }

        context.startActivity(intent);
    }

    /**
     * 读取Office文件
     *
     * @param context  context
     * @param filepath filepath
     * @param title    title
     */
    public void readFile(Context context, String filepath, String title) {

        Intent intent = new Intent(context, FileReadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(HGParamKey.URL.getValue(), filepath);
        intent.putExtra(HGParamKey.Title.getValue(), title);
        context.startActivity(intent);
    }

    /**
     * 文件预览 支持 图片+Office文件
     *
     * @param context context
     * @param img     img
     */
    public void imagePre(Context context, ArrayList<Media> img) {
        imagePre(context, img, null);
    }

    /**
     * 文件预览 支持 图片+Office文件
     *
     * @param context context
     * @param img     img
     * @param title   title
     */
    public void imagePre(Context context, ArrayList<Media> img, String title) {

        Intent intent = new Intent(context, ImagePreActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (img != null) {
            intent.putExtra(HGParamKey.ListData.getValue(), img);
        }
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(HGParamKey.Title.getValue(), title);
        }
        context.startActivity(intent);
    }

    /**
     * 安装APK文件
     *
     * @param context  context
     * @param filePath filePath
     */
    public void installAPK(Context context, String filePath) {

        Intent installAPKIntent = new Intent();
        installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installAPKIntent.setAction(Intent.ACTION_VIEW);
        installAPKIntent.setDataAndType(getFileUri(context, filePath), "application/vnd.android.package-archive");
        context.startActivity(installAPKIntent);
    }

    /**
     * 安装APK文件
     *
     * @param context context
     * @param file    file
     */
    public void installAPK(Context context, File file) {
        installAPK(context, file.getAbsolutePath());
    }

    /**
     * 是否可以安装APK文件
     *
     * @param context context
     * @return boolean
     */
    public boolean canInstallAPK(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }

        return true;
    }

    /**
     * 请求安装权限</br>
     * 8.0以上需要使用
     */
    public void requestInstallPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 打开系统无线网络设置界面
     *
     * @param context context
     */
    public void openNetworkSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private final String audioSavePath = HGSystemConfig.getAudioCachePath();
    private String audioName = "";
    public final String[] recordAudioPermissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     * 打开录音界面
     *
     * @param activity    activity
     * @param bgColor     录音界面背景颜色
     * @param requestCode requestCode
     * @param sampleRate  采样频率 请使用枚举 AudioSampleRate
     * @param isAutoStart 是否自动开始录音
     */
    public void recordAudio(Activity activity,
                            int bgColor,
                            int requestCode,
                            Object sampleRate,
                            boolean isAutoStart
    ) {

        Class<?> androidAudioRecorderClass = ReflectUtils.getClassByPackageName("com.hg.hollowgoods.recorder.AndroidAudioRecorder");
        if (androidAudioRecorderClass == null) {
            t.error(R.string.not_implementation_recorder_module);
            return;
        }

        audioName = System.currentTimeMillis() + ".wav";

        Method withActivity = ReflectUtils.getMethodByName(
                androidAudioRecorderClass,
                "with",
                Activity.class
        );

        if (withActivity != null) {
            Object androidAudioRecorder = ReflectUtils.invokeStaticMethod(withActivity, activity);

            if (androidAudioRecorder != null) {
                // Required
                Method setFilePath = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setFilePath",
                        String.class
                );
                if (setFilePath != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, setFilePath, audioSavePath + audioName);
                }

                Method setColor = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setColor",
                        int.class
                );
                if (setColor != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, setColor, bgColor);
                }

                Method setRequestCode = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setRequestCode",
                        int.class
                );
                if (setRequestCode != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, setRequestCode, requestCode);
                }

                // Optional
                Class<?> audioSourceClass = ReflectUtils.getClassByPackageName("com.hg.hollowgoods.recorder.model.AudioSource");
                Method setSource = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setSource",
                        audioSourceClass
                );
                if (setSource != null) {
                    Object value = ReflectUtils.getStaticObjValue(audioSourceClass, "MIC");
                    ReflectUtils.invokeMethod(androidAudioRecorder, setSource, value);
                }

                Class<?> audioChannelClass = ReflectUtils.getClassByPackageName("com.hg.hollowgoods.recorder.model.AudioChannel");
                Method setChannel = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setChannel",
                        audioChannelClass
                );
                if (setChannel != null) {
                    Object value = ReflectUtils.getStaticObjValue(audioChannelClass, "STEREO");
                    ReflectUtils.invokeMethod(androidAudioRecorder, setChannel, value);
                }

                Class<?> audioSampleRateClass = ReflectUtils.getClassByPackageName("com.hg.hollowgoods.recorder.model.AudioSampleRate");
                Method setSampleRate = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setSampleRate",
                        audioSampleRateClass
                );
                if (setSampleRate != null) {
                    Object value;
                    if (sampleRate.getClass().getName().startsWith("com.hg.hollowgoods.recorder.model.AudioSampleRate")) {
                        value = sampleRate;
                    } else {
                        value = ReflectUtils.getStaticObjValue(audioSampleRateClass, "HZ_48000");
                    }
                    ReflectUtils.invokeMethod(androidAudioRecorder, setSampleRate, value);
                }

                Method setAutoStart = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setAutoStart",
                        boolean.class
                );
                if (setAutoStart != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, setAutoStart, isAutoStart);
                }

                Method setKeepDisplayOn = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "setKeepDisplayOn",
                        boolean.class
                );
                if (setKeepDisplayOn != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, setKeepDisplayOn, true);
                }

                // Start recording
                Method record = ReflectUtils.getMethodByName(
                        androidAudioRecorderClass,
                        "record"
                );
                if (record != null) {
                    ReflectUtils.invokeMethod(androidAudioRecorder, record);
                }
            }
        }
    }

    public String getAudioPath() {
        return audioSavePath + audioName;
    }

    public boolean onActivityResultForRecordAudio(int resultCode) {

        if (resultCode == Activity.RESULT_OK) {
            return true;
        } else if (resultCode == Activity.RESULT_CANCELED) {
            return false;
        }

        return false;
    }

}
