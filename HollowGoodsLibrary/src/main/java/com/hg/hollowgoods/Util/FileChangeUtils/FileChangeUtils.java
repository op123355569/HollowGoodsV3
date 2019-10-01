package com.hg.hollowgoods.Util.FileChangeUtils;

import android.text.TextUtils;

import com.hg.hollowgoods.Bean.AppFile;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * 文件类型转换工具类
 * <p>
 * 实例化示例：FileChangeUtils<HGWebFile> fileChangeUtils = new FileChangeUtils<HGWebFile>() {};
 * <p>
 * {}千万不能少，否则编译会报错
 * <p>
 * Created by Hollow Goods on 2019-04-03.
 * Update by Hollow Goods on 2019-10-01.
 */
public class FileChangeUtils<T extends HGWebFile> {

    private static String SHOW_FILE_URL = "http://localhost:8080/show/%1$s";
    private static String DOWNLOAD_FILE_URL = "http://localhost:8080/download/%1$s";

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

    public ArrayList<AppFile> webFiles2AppFiles(ArrayList<T> webFiles) {

        ArrayList<AppFile> result = new ArrayList<>();

        if (webFiles != null) {
            AppFile appFile;

            for (T t : webFiles) {
                appFile = webFile2AppFile(t);
                if (appFile != null) {
                    result.add(appFile);
                }
            }
        }

        return result;
    }

    public AppFile webFile2AppFile(T webFile) {

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

    public ArrayList<T> appFiles2WebFiles(ArrayList<AppFile> appFiles) {

        ArrayList<T> result = new ArrayList<>();

        if (appFiles != null) {
            T webFile;

            for (AppFile t : appFiles) {
                webFile = appFile2WebFile(t);

                if (webFile != null) {
                    result.add(webFile);
                }
            }
        }

        return result;
    }

    public T appFile2WebFile(AppFile appFile) {

        T result = null;

        if (appFile != null && !TextUtils.isEmpty(appFile.getUrl())) {
            try {
                result = getTypeClass().newInstance();
                result.setFileLocalName(appFile.getOldName());
                result.setFileUrlName(appFile.getOldUrl());
            } catch (Exception e) {
                LogUtils.Log(e.getMessage());
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTypeClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

}
