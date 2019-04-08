package com.hg.hollowgoods.Util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 类工具类
 * Created by Hollow Goods on 2019-04-04.
 */
public class BeanUtils {

    public <T> T copy(Object source, Type type) {

        if (source == null) {
            return null;
        }

        String str = new Gson().toJson(source);

        return new Gson().fromJson(str, type);
    }

}
