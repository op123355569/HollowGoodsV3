package com.hg.hollowgoods.Util;

import android.content.Context;

/**
 * Created by Hollow Goods on 2019-07-04.
 */
public class ResUtils {

    public static Integer getImageResources(Context context, String name, String type) {

        try {
            return context.getResources().getIdentifier(name, type, APPUtils.getPackageName(context));
        } catch (Exception ignored) {

        }

        return null;
    }

}
