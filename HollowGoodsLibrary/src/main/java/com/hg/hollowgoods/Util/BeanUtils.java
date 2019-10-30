package com.hg.hollowgoods.Util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 类工具类
 * Created by Hollow Goods on 2019-04-04.
 */
public class BeanUtils {

    /**
     * 复制
     * <p>
     * 改方法转为静态方法
     *
     * @param source 源类
     * @param type   源类类型
     * @param <T>    接收类型
     * @return 根据接收类型确定
     */
    public static <T> T copy(Object source, Type type) {

        if (source == null) {
            return null;
        }

        String str = new Gson().toJson(source);

        return new Gson().fromJson(str, type);
    }

}
