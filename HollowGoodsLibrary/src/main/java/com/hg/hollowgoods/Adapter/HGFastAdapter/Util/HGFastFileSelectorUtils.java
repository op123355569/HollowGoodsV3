package com.hg.hollowgoods.Adapter.HGFastAdapter.Util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.FileMode;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.PopupWinHelper;
import com.hg.hollowgoods.Util.SystemAppUtils.SystemAppUtils;

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
    private ArrayList<Media> medias;

    /**
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
            ArrayList<Media> medias,
            int maxCount,
            int quality,
            String fileFilter
    ) {

        this.medias = medias;

        if (helper == null) {
            helper = new PopupWinHelper(view.getContext(), v -> {
                if (v.getId() == R.id.ll_openCamera) {
                    openCamera(activity, quality);
                } else if (v.getId() == R.id.ll_openAlbum) {
                    openAlbum(activity, this.medias, maxCount, quality);
                } else if (v.getId() == R.id.ll_openFile) {
                    openFile(activity, this.medias, maxCount, fileFilter);
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
                openCamera(activity, quality);
                break;
            case OpenAlbum:
                openAlbum(activity, medias, maxCount, quality);
                break;
            case OpenFile:
                openFile(activity, medias, maxCount, fileFilter);
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
        systemAppUtils.takePhoto(activity, REQUEST_CODE_OPEN_CAMERA, quality, false);
    }

    public void openAlbum(Activity activity, ArrayList<Media> medias, int maxCount, int quality) {

        ArrayList<String> photos = new ArrayList<>();
        int passCount = 0;

        if (medias != null) {
            for (Media t : medias) {
                if (t.getFile() != null && FileUtils.isImageFile(t.getFile().getAbsolutePath())) {
                    photos.add(t.getFile().getAbsolutePath());
                } else {
                    passCount++;
                }
            }
        }

        systemAppUtils.checkPhotos(activity, REQUEST_CODE_OPEN_ALBUM, maxCount - passCount, photos);
    }

    public void openFile(Context context, ArrayList<Media> medias, int maxCount, String fileFilter) {

        HashMap<String, File> temp = new HashMap<>();
        int passCount = 0;

        if (medias != null) {
            for (Media t : medias) {
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
