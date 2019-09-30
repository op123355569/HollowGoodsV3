package com.hg.hollowgoods.Util.FileChangeUtils;

import android.text.TextUtils;

import com.hg.hollowgoods.Bean.AppFile;
import com.hg.hollowgoods.Util.FileUtils;

import java.util.ArrayList;

/**
 * 文件类型转换工具类
 * Created by Hollow Goods on 2019-04-03.
 */
public class FileChangeUtils {

    private static String SHOW_FILE_URL = "";
    private static String DOWNLOAD_FILE_URL = "";

    /**
     * 初始化
     *
     * @param showFileUrl     显示文件的接口 例如：http://localhost:8080/show/%1$s (%1$s用于替换文件链接)
     * @param downloadFileUrl 下载文件的接口 例如：http://localhost:8080/download/%1$s (%1$s用于替换文件链接)
     */
    public static void init(String showFileUrl, String downloadFileUrl) {
        SHOW_FILE_URL = showFileUrl;
        DOWNLOAD_FILE_URL = downloadFileUrl;
    }

    public static ArrayList<AppFile> webFiles2AppFiles(ArrayList<HGWebFile> webFiles) {

        ArrayList<AppFile> result = new ArrayList<>();

        if (webFiles != null) {
            AppFile appFile;

            for (HGWebFile t : webFiles) {
                appFile = webFile2AppFile(t);
                if (appFile != null) {
                    result.add(appFile);
                }
            }
        }

        return result;
    }

    public static AppFile webFile2AppFile(HGWebFile webFile) {

        AppFile result = null;

        if (webFile != null) {
            result = new AppFile();
            result.setOldName(webFile.getFileLocalName());
            result.setOldUrl(webFile.getFileUrlName());

            if (FileUtils.isImageFile(webFile.getFileLocalName())) {
                result.setUrl(String.format(SHOW_FILE_URL, webFile.getFileUrlName()));
            } else {
                result.setUrl(String.format(DOWNLOAD_FILE_URL, webFile.getFileUrlName()));
            }
        }

        return result;
    }

    public static ArrayList<HGWebFile> appFiles2WebFiles(ArrayList<AppFile> appFiles) {

        ArrayList<HGWebFile> result = new ArrayList<>();

        if (appFiles != null) {
            HGWebFile webFile;

            for (AppFile t : appFiles) {
                webFile = appFile2WebFile(t);

                if (webFile != null) {
                    result.add(webFile);
                }
            }
        }

        return result;
    }

    public static HGWebFile appFile2WebFile(AppFile appFile) {

        HGWebFile result = null;

        if (appFile != null && !TextUtils.isEmpty(appFile.getUrl())) {
            result = new HGWebFile();
            result.setFileLocalName(appFile.getOldName());
            result.setFileUrlName(appFile.getOldUrl());
        }

        return result;
    }

}
