package com.hg.hollowgoods.Util.SearchHistory;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FileUtils;

import java.util.ArrayList;

/**
 * 搜索历史记录工具
 * Created by Hollow Goods on unknown.
 */

public class SearchHistoryUtils {

    public static final int MAX_KEY_COUNT = 3;

    public static ArrayList<SearchKeys> getKeys(Class clazz, int... historyCode) {

        ArrayList<SearchKeys> result = null;
        FileUtils.checkFileExistAndCreate(HGSystemConfig.getSearchHistoryPath());
        String name = HGSystemConfig.getSearchHistoryPath() + EncryptUtils.md5Encrypt(clazz.getName());

        if (historyCode != null && historyCode.length > 0) {
            name = name + historyCode[0];
        }

        if (FileUtils.checkFileExistOnly(name)) {
            String str = FileUtils.loadFromSDCard(name);

            result = new Gson().fromJson(str, new TypeToken<ArrayList<SearchKeys>>() {
            }.getType());
        }

        if (result == null) {
            result = new ArrayList<>();
        }

        return result;
    }

    public static ArrayList<SearchKeys> addKey(ArrayList<SearchKeys> keys, String key) {

        for (int i = 0; i < keys.size(); ) {
            if (TextUtils.equals(keys.get(i).getBody(), key)) {
                keys.remove(i);
            } else {
                i++;
            }
        }

        if (keys.size() >= MAX_KEY_COUNT) {
            keys.remove(MAX_KEY_COUNT - 1);
        }
        keys.add(0, new SearchKeys(key));

        return keys;
    }

    public static void saveKeys(Class clazz, ArrayList<SearchKeys> keys, int... historyCode) {

        if (keys != null && keys.size() > 0) {

            if (keys.size() > MAX_KEY_COUNT) {
                for (int i = MAX_KEY_COUNT; i < keys.size(); ) {
                    keys.remove(i);
                }
            }

            FileUtils.checkFileExistAndCreate(HGSystemConfig.getSearchHistoryPath());
            String str = new Gson().toJson(keys);
            String name = HGSystemConfig.getSearchHistoryPath() + EncryptUtils.md5Encrypt(clazz.getName());

            if (historyCode != null && historyCode.length > 0) {
                name = name + historyCode[0];
            }

            FileUtils.saveToSD(name, str);
        }
    }

}
