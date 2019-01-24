package com.hg.hollowgoods.Util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.lang.reflect.Type;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2018年12月21日
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
            FileUtils.checkFileExist(path);
            FileUtils.saveToSD(path + name, str);
        } catch (Exception e) {

        }
    }

    public void save(String name, Object data) {
        save(path, name, data);
    }

    public <T> T load(String path, String name, Type type) {
        try {
            if (FileUtils.checkFileExist2(path + name)) {
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
