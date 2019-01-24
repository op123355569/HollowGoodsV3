package com.hg.hollowgoods.Util;

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

import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Plugin.FileReadActivity;
import com.hg.hollowgoods.UI.Activity.Plugin.FileSelectorActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.PhotoPicter.Activity.BGAPhotoPickerActivity;
import com.hg.hollowgoods.Util.PhotoPicter.Activity.BGAPhotoPreviewActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
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
     * @param context
     * @param filepath
     * @return
     */
    public Uri getFileUri(Context context, String filepath) {
        return getFileUri(context, new File(filepath));
    }

    /**
     * 获取文件的URI
     *
     * @param context
     * @param file
     * @return
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

    private String cameraPhotoName = "";
    private File cameraPhotoFile = null;
    private int cameraPhotoQuality;

    /**
     * 获取照片名称
     *
     * @return
     */
    public String getCameraPhotoName() {
        return cameraPhotoName;
    }

    /**
     * 获取照片文件
     *
     * @return
     */
    public File getCameraPhotoFile() {
        return cameraPhotoFile;
    }

    /**
     * 获取照片压缩质量
     *
     * @return
     */
    public int getCameraPhotoQuality() {
        return cameraPhotoQuality;
    }

    /**
     * 调用系统相机拍摄照片
     *
     * @param activity
     * @param requestCode
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
     * @param activity
     * @return
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

        MediaScannerConnection.scanFile(context, new String[]{cameraPhotoFile.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
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
     * 打开相册
     *
     * @param activity
     * @param requestCode
     */
    public void openAlbum(Activity activity, int requestCode) {

        FileUtils.checkFileExist(photoSavePath);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开相册返回处理
     *
     * @param context
     * @param data
     * @return
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
     * @param context
     * @param uri
     * @return
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
     * @param context
     * @param path
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
     * @param activity
     * @param requestCode
     * @param quality
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

        File file = new File(videoSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }

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
     * @return
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
     * @param activity
     * @param requestCode
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
     * @param data
     * @return
     */
    public ArrayList<String> onActivityResultForCheckPhotos(Intent data) {
        return BGAPhotoPickerActivity.getSelectedPhotos(data);
    }

    /**
     * 图片预览
     *
     * @param context
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
     * @param context
     * @param photo
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
     * @param url
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
     * @param context
     * @param maxCount     选择个数上限
     * @param fileFilter   过滤格式，如只要jpg和png格式，传 .jpg,.png
     * @param checkedFiles 已选文件
     * @return 在onEvnetUI中返回，单文件是返回 File，多文件返回 HashMap<String, File>
     */
    public void checkFiles(Context context, int maxCount, String fileFilter, HashMap<String, File> checkedFiles) {

        Intent intent = new Intent(context, FileSelectorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(Constants.PARAM_KEY_1, maxCount);
        if (!TextUtils.isEmpty(fileFilter)) {
            intent.putExtra(Constants.PARAM_KEY_2, fileFilter);
        }
        if (checkedFiles != null) {
            intent.putExtra(Constants.PARAM_KEY_3, checkedFiles);
        }

        context.startActivity(intent);
    }

    public void readFile(Context context, String filepath, String title) {

        Intent intent = new Intent(context, FileReadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(Constants.PARAM_KEY_1, filepath);
        intent.putExtra(Constants.PARAM_KEY_2, title);
        context.startActivity(intent);
    }

    /**
     * 安装APK文件
     *
     * @param context
     * @param filePath
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
     * @param context
     * @param file
     */
    public void installAPK(Context context, File file) {
        installAPK(context, file.getAbsolutePath());
    }

    /**
     * 是否可以安装APK文件
     *
     * @param context
     * @return
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
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, requestCode);
    }

}
