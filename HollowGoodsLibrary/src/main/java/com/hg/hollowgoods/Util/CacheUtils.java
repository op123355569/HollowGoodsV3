package com.hg.hollowgoods.Util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.lang.reflect.Type;

/**
 * 缓存工具类
 * Created by Hollow Goods on 2018-12-21.
 */
public class CacheUtils {

    private static CacheUtils instance = null;
    private static String path = "";

    private CacheUtils() {

    }

    public static CacheUtils create() {

        if (instance == null) {
            instance = new CacheUtils();
        }

        if (TextUtils.isEmpty(path)) {
            path = HGSystemConfig.getDataCachePath();
        }

        return instance;
    }

    public void save(String path, String name, Object data) {
        try {
            String str = new Gson().toJson(data);
            FileUtils.checkFileExistAndCreate(path);
            FileUtils.saveToSD(path + name, str);
        } catch (Exception e) {

        }
    }

    public void save(String name, Object data) {
        save(path, name, data);
    }

    public <T> T load(String path, String name, Type type) {
        try {
            if (FileUtils.checkFileExistOnly(path + name)) {
                String str = FileUtils.loadFromSDCard(path + name);
                return new Gson().fromJson(str, type);
            }
        } catch (Exception e) {

        }

        return null;
    }

    public <T> T load(String name, Type type) {
        return load(path, name, type);
    }

}
