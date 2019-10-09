package com.hg.hollowgoods.Util.XUtils.CallBack;

import java.util.ArrayList;

/**
 * 列表数据网络回调助手
 * Created by Hollow Goods on 2019-09-30.
 */
public interface ListDataNetCallBackHelper<T, P> extends NetCallBackBaseHelper<P> {

    /**
     * 中间数据，给用户自行处理数据的机会，如果不需要自行处理数据，则无需在此方法内再写逻辑
     * <p>
     * Child Thread
     *
     * @param responseInfo P
     * @param tempData     ArrayList<T>
     */
    void onMiddleData(P responseInfo, ArrayList<T> tempData);

    /**
     * 请求成功
     * <p>
     * Main Thread
     *
     * @param responseInfo P
     * @param tempData     ArrayList<T>
     */
    void onNetGetSuccess(P responseInfo, ArrayList<T> tempData);

}
