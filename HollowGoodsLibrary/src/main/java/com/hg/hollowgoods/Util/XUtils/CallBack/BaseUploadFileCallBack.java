package com.hg.hollowgoods.Util.XUtils.CallBack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hg.hollowgoods.Util.XUtils.UploadListener;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * 基上传文件回调
 * <p>
 * T 业务数据封装类
 * <p>
 * P 请求结果封装类
 * <p>
 * <p>
 * 继承类请声明为抽象类，
 * <p>
 * 继承类保留T，填写P
 * <p>
 * 继承类有以下4个接口继承类必须实现：
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.CallBack.UploadFileCallBackHelper#onUploadFileResult(P)}
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.UploadListener#onUploadError(Throwable)}
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.UploadListener#onUploadLoading(long, long)}
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.UploadListener#onUploadFinish()}
 * <p>
 * Created by Hollow Goods on 2019-09-30.
 */
public abstract class BaseUploadFileCallBack<T, P> implements UploadListener, UploadFileCallBackHelper<T, P> {

    @Override
    public void onUploadSuccess(String result) {
        P responseInfo = new Gson().fromJson(result, getTypePClass());
        onUploadFileResult(responseInfo);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getTypeTClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private Class<P> getTypePClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
        return (Class<P>) parameterizedType.getActualTypeArguments()[1];
    }

    protected ArrayList<T> stringToList(String str, Class<T> clazz) {

        ArrayList<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(str).getAsJsonArray();
        Gson gson = new Gson();

        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, clazz));
        }

        return list;
    }

}
