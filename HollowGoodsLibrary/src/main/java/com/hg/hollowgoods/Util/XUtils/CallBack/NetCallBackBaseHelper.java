package com.hg.hollowgoods.Util.XUtils.CallBack;

/**
 * 网络回调助手基类
 * Created by Hollow Goods on 2019-09-30.
 */
public interface NetCallBackBaseHelper<P> {

    /**
     * 网络请求的结果
     *
     * @param responseInfo P
     */
    void onNetGetResult(P responseInfo);

    /**
     * 请求失败
     * <p>
     * Main Thread
     *
     * @param errorCode int
     * @param msg       Object
     */
    void onNetGetError(int errorCode, Object msg);

    /**
     * 请求结束
     * <p>
     * 不管成功或结束后，必定调用该方法
     * <p>
     * Main Thread
     */
    void onNetGetFinish();

}
