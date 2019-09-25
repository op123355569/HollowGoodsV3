package com.hg.hollowgoods.Util;

import android.content.Context;

/**
 * 资源文件工具类
 * Created by Hollow Goods on 2019-07-04.
 */
public class ResUtils {

    /**
     * 获取资源文件
     *
     * @param context Context
     * @param name    String
     * @param type    String
     * @return Integer 可能为null
     */
    public static Integer getImageResources(Context context, String name, String type) {

        try {
            return context.getResources().getIdentifier(name, type, APPUtils.getPackageName(context));
        } catch (Exception ignored) {

        }

        return null;
    }

}
