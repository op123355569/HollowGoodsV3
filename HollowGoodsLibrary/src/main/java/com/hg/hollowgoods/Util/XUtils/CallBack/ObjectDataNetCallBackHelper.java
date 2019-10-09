package com.hg.hollowgoods.Util.XUtils.CallBack;

/**
 * 单一数据网络回调助手
 * Created by Hollow Goods on 2019-09-30.
 */
public interface ObjectDataNetCallBackHelper<T, P> extends NetCallBackBaseHelper<P> {

    /**
     * 请求成功
     * <p>
     * Main Thread
     *
     * @param responseInfo P
     * @param tempData     T
     */
    void onNetGetSuccess(P responseInfo, T tempData);

}
