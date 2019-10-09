package com.hg.hollowgoods.Util.XUtils.CallBack;

import java.util.ArrayList;

/**
 * 上传文件回调助手
 * Created by Hollow Goods on 2019-09-30.
 */
public interface UploadFileCallBackHelper<T, P> {

    /**
     * 上传文件的结果
     *
     * @param responseInfo P
     */
    void onUploadFileResult(P responseInfo);

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
     * 上传成功
     * <p>
     * Main Thread
     *
     * @param responseInfo ResponseInfo
     * @param tempData     ArrayList<T>
     */
    void onUploadFileSuccess(P responseInfo, ArrayList<T> tempData);

    /**
     * 上传失败
     * <p>
     * Main Thread
     *
     * @param errorCode int
     * @param msg       Object
     */
    void onUploadFileError(int errorCode, Object msg);

    /**
     * 上传进度
     * <p>
     * Main Thread
     *
     * @param total   long
     * @param current long
     */
    void onUploadFileLoading(long total, long current);

    /**
     * 上传结束
     * <p>
     * Main Thread
     */
    void onUploadFileFinish();

}
