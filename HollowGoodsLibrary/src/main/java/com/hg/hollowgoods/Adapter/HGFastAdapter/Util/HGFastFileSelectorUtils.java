package com.hg.hollowgoods.Adapter.HGFastAdapter.Util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.hg.hollowgoods.Bean.AppFile;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.FileMode;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyConfig;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyHelper;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.PopupWinHelper;
import com.hg.hollowgoods.Util.SystemAppUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 快速适配器文件选择器工具类
 * Created by Hollow Goods on 2019-05-09.
 */
public class HGFastFileSelectorUtils {

    public static final int REQUEST_CODE_OPEN_CAMERA = 10;
    public static final int REQUEST_CODE_OPEN_ALBUM = 12;

    private PopupWinHelper helper;
    public SystemAppUtils systemAppUtils = new SystemAppUtils();
    private Activity activity;
    private ArrayList<AppFile> medias;
    private int maxCount;
    private int quality;
    private String fileFilter;

    /**
     * 设置Activity
     * <p>
     * 当不调用{@link #showFileSelectorWindow(Activity, View, FileMode, ArrayList, int, int, String)}时，单独调用其他方法需要先调用该方法
     *
     * @param activity Activity {@link BaseActivity}
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示文件选择的选项的弹窗
     * Activity是{@link BaseActivity}的话相机权限自动代理
     * 否则请自行获取相机权限
     *
     * @param activity   都要用
     * @param view       都要用
     * @param fileMode   都要用
     * @param medias     打开相册和打开文件要用
     * @param maxCount   打开相册和打开文件要用
     * @param quality    都要用
     * @param fileFilter 打开文件要用
     */
    public void showFileSelectorWindow(
            Activity activity,
            View view,
            FileMode fileMode,
            ArrayList<AppFile> medias,
            int maxCount,
            int quality,
            String fileFilter
    ) {

        this.activity = activity;
        this.medias = medias;
        this.maxCount = maxCount;
        this.quality = quality;
        this.fileFilter = fileFilter;

        if (helper == null) {
            helper = new PopupWinHelper(view.getContext(), v -> {
                if (v.getId() == R.id.ll_openCamera) {
                    openCamera(HGFastFileSelectorUtils.this.activity,
                            HGFastFileSelectorUtils.this.quality
                    );
                } else if (v.getId() == R.id.ll_openAlbum) {
                    openAlbum(HGFastFileSelectorUtils.this.activity,
                            HGFastFileSelectorUtils.this.medias,
                            HGFastFileSelectorUtils.this.maxCount,
                            HGFastFileSelectorUtils.this.quality
                    );
                } else if (v.getId() == R.id.ll_openFile) {
                    openFile(HGFastFileSelectorUtils.this.activity,
                            HGFastFileSelectorUtils.this.medias,
                            HGFastFileSelectorUtils.this.maxCount,
                            HGFastFileSelectorUtils.this.fileFilter
                    );
                }

                helper.closePopupWin();
            });

            helper.init(
                    R.layout.popupwin_fast_file_choose,
                    Gravity.BOTTOM,
                    android.R.style.Animation_Toast,
                    "#AA000000",
                    new int[]{R.id.ll_openCamera, R.id.ll_openAlbum, R.id.ll_openFile, R.id.line2},
                    new int[]{PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_NO_LISTENER},
                    false
            );
        }

        switch (fileMode) {
            case OpenCamera:
                openCamera(HGFastFileSelectorUtils.this.activity,
                        HGFastFileSelectorUtils.this.quality
                );
                break;
            case OpenAlbum:
                openAlbum(HGFastFileSelectorUtils.this.activity,
                        HGFastFileSelectorUtils.this.medias,
                        HGFastFileSelectorUtils.this.maxCount,
                        HGFastFileSelectorUtils.this.quality
                );
                break;
            case OpenFile:
                openFile(HGFastFileSelectorUtils.this.activity,
                        HGFastFileSelectorUtils.this.medias,
                        HGFastFileSelectorUtils.this.maxCount,
                        HGFastFileSelectorUtils.this.fileFilter
                );
                break;
            case OpenCameraOrAlbum:
                View openFileView = helper.getView(R.id.ll_openFile);
                View line2 = helper.getView(R.id.line2);

                openFileView.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);

                helper.showPopupWin(view);
                break;
            case OpenCameraOrAlbumOrFile:
                helper.showPopupWin(view);
                break;
        }
    }

    public void openCamera(Activity activity, int quality) {
        // 代理相机权限
        if (activity instanceof BaseActivity) {
            ProxyHelper.create((BaseActivity) activity)
                    .requestProxy(
                            new ProxyConfig()
                                    .setPermissions(systemAppUtils.cameraPermissions)
                                    .setRequestCode(REQUEST_CODE_OPEN_CAMERA)
                                    .setOnProxyRequestPermissionsResult((isAgreeAll, requestCode1, permissions, isAgree) -> {
                                        if (requestCode1 == REQUEST_CODE_OPEN_CAMERA && isAgreeAll) {
                                            systemAppUtils.takePhoto(activity, REQUEST_CODE_OPEN_CAMERA, quality, false);
                                        }
                                    })
                    );
        } else {
            systemAppUtils.takePhoto(activity, REQUEST_CODE_OPEN_CAMERA, quality, false);
        }
    }

    public void openAlbum(Activity activity, ArrayList<AppFile> medias, int maxCount, int quality) {

        ArrayList<String> photos = new ArrayList<>();
        int passCount = 0;

        if (medias != null) {
            for (AppFile t : medias) {
                if (t.getFile() != null && FileUtils.isImageFile(t.getFile().getAbsolutePath())) {
                    photos.add(t.getFile().getAbsolutePath());
                } else {
                    passCount++;
                }
            }
        }

        systemAppUtils.checkPhotos(activity, REQUEST_CODE_OPEN_ALBUM, maxCount - passCount, photos);
    }

    public void openFile(Context context, ArrayList<AppFile> medias, int maxCount, String fileFilter) {

        HashMap<String, File> temp = new HashMap<>();
        int passCount = 0;

        if (medias != null) {
            for (AppFile t : medias) {
                if (t.getFile() != null) {
                    temp.put(t.getFile().getAbsolutePath(), t.getFile());
                } else {
                    passCount++;
                }
            }
        }

        systemAppUtils.checkFiles(context, maxCount - passCount, fileFilter, temp);
    }

}
