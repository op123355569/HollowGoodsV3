package com.hg.hollowgoods.Util;

import android.content.Context;

/**
 * 资源文件工具类
 * Created by Hollow Goods on 2019-07-04.
 */
public class ResUtils {

    /**
     * 获取资源文件的id
     *
     * @param context Context
     * @param name    String
     * @param type    drawable mipmap string...
     * @return Integer 可能为null
     */
    public static Integer getResourcesIdByName(Context context, String name, String type) {

        try {
            return context.getResources().getIdentifier(name, type, APPUtils.getPackageName(context));
        } catch (Exception ignored) {

        }

        return null;
    }

}
