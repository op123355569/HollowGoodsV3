package com.hg.hollowgoods.Util.XUtils.CallBack;

import com.google.gson.Gson;
import com.hg.hollowgoods.Util.XUtils.GetHttpDataListener;

import java.lang.reflect.ParameterizedType;

/**
 * 基单一数据回调
 * <p>
 * 指的是返回的数据是{}型
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
 * 继承类有以下3个接口继承类必须实现：
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.CallBack.NetCallBackBaseHelper#onNetGetResult(P)}
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.GetHttpDataListener#onGetError(Throwable)}
 * <p>
 * {@link com.hg.hollowgoods.Util.XUtils.GetHttpDataListener#onGetFinish()}
 * <p>
 * Created by Hollow Goods on 2019-09-30.
 */
public abstract class BaseObjectDataCallBack<T, P> implements GetHttpDataListener, ObjectDataNetCallBackHelper<T, P> {

    @Override
    public void onGetSuccess(String result) {
        P responseInfo = new Gson().fromJson(result, getTypePClass());
        onNetGetResult(responseInfo);
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

}
