package com.hg.hollowgoods.Util.XUtils;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.xutils.http.RequestParams;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 请求参数帮助类
 * <p>
 * Created by Hollow Goods on 2019-11-20.
 */
public class RequestParamsHelper {

    /**
     * 构建key-value方式的请求参数
     *
     * @param url      String 请求地址
     * @param header   Map<String, String> 请求头，可以为null
     * @param keyValue Map<String, Object>  请求参数，可以为null
     * @return RequestParams
     */
    public static RequestParams builderKeyValueRequestParam(@NotNull String url, @Nullable Map<String, String> header, @Nullable Map<String, Object> keyValue) {

        RequestParams params = new RequestParams(url);

        if (header != null) {
            Set<String> set = header.keySet();
            Iterator<String> keys = set.iterator();
            String key;

            while (keys.hasNext()) {
                key = keys.next();
                params.addHeader(key, header.get(key));
            }
        }

        if (keyValue != null) {
            Set<String> set = keyValue.keySet();
            Iterator<String> keys = set.iterator();
            String key;

            while (keys.hasNext()) {
                key = keys.next();
                params.addParameter(key, keyValue.get(key));
            }
        }

        return params;
    }

    /**
     * 构建JsonBody方式的请求参数
     *
     * @param url         String 请求地址
     * @param header      Map<String, String> 请求头，可以为null
     * @param bodyContent Object  请求参数，可以为null，会自动转为json string放入请求体中
     * @return RequestParams
     */
    public static RequestParams builderJsonBodyRequestParam(@NotNull String url, @Nullable Map<String, String> header, @Nullable Object bodyContent) {

        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);

        if (header != null) {
            Set<String> set = header.keySet();
            Iterator<String> keys = set.iterator();
            String key;

            while (keys.hasNext()) {
                key = keys.next();
                params.addHeader(key, header.get(key));
            }
        }

        if (bodyContent != null) {
            params.setBodyContent(new Gson().toJson(bodyContent));
        }

        return params;
    }

}
