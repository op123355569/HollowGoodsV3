package com.hg.hollowgoods.Util.Soap;

import android.app.Activity;

import com.google.gson.Gson;
import com.hg.hollowgoods.Bean.HGResponseInfo;
import com.hg.hollowgoods.Util.IP.InterfaceConfig;
import com.hg.hollowgoods.Util.LogUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * WebService网络请求工具类
 * Created by Hollow Goods on unknown.
 */

public class SoapUtils {

    /**
     * 命名空间
     */
    public static final String NAME_SPACE = "http://webservice.company.com/";

    private Activity activity = null;
    private RequestListener requestListener = null;
    private String res = "";

    public SoapUtils(Activity activity, RequestListener requestListener) {
        this.activity = activity;
        this.requestListener = requestListener;
    }

    public interface RequestListener {
        /**
         * 请求成功（子线程）
         *
         * @param methodName
         * @param result
         */
        void onRequestSuccess(String methodName, HGResponseInfo result);

        /**
         * 请求失败（主线程）
         *
         * @param methodName
         * @param result
         */
        void onRequestFail(String methodName, String result);

        /**
         * 请求结束（主线程）
         */
        void onRequestFinish();
    }

    public void request(final String serviceName, final String methodName, final List<SoapParam> params) {

        new Thread(() -> {

            SoapObject soapObject = new SoapObject(NAME_SPACE, methodName);
            res = "";
            boolean isSuccess = false;

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    soapObject.addProperty(params.get(i).getKey(), params.get(i).getValue());
                }
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = soapObject;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(soapObject);
            HttpTransportSE transport = new HttpTransportSE(InterfaceConfig.getNowIPConfig().getRequestUrl() + "/" + serviceName);

            try {
                transport.call(null, envelope);
                if (envelope.getResponse() != null) {
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    res = result.getProperty(0).toString();

                    isSuccess = true;
                } else {
                    isSuccess = false;
                    res = "";
                }
            } catch (IOException e) {
                // 请求失败
                isSuccess = false;
                res = e.getMessage();
            } catch (XmlPullParserException e) {
                // 请求失败
                isSuccess = false;
                res = e.getMessage();
            }

            LogUtils.LogRequest(InterfaceConfig.getNowIPConfig().getRequestUrl() + "/" + serviceName + "/" + methodName, res);

            if (isSuccess) {
                requestListener.onRequestSuccess(methodName, new Gson().fromJson(res, HGResponseInfo.class));
            } else {
                activity.runOnUiThread(() -> requestListener.onRequestFail(methodName, res));
            }

            // 请求完成
            activity.runOnUiThread(() -> requestListener.onRequestFinish());

        }).start();
    }

    public void request(final String head, final String serviceName, final String methodName, final List<SoapParam> params) {

        new Thread(() -> {

            SoapObject soapObject = new SoapObject(NAME_SPACE, methodName);
            res = "";
            boolean isSuccess;

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    soapObject.addProperty(params.get(i).getKey(), params.get(i).getValue());
                }
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = soapObject;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(soapObject);
            HttpTransportSE transport = new HttpTransportSE(head + "/" + serviceName);

            try {
                transport.call(null, envelope);
                if (envelope.getResponse() != null) {
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    res = result.getProperty(0).toString();
                    // 请求成功
                    isSuccess = true;
                } else {
                    isSuccess = false;
                    res = "";
                }
            } catch (IOException e) {
                // 请求失败，无返回
                isSuccess = false;
                res = e.getMessage();
            } catch (XmlPullParserException e) {
                // 请求失败，无返回
                isSuccess = false;
                res = e.getMessage();
            }

            LogUtils.LogRequest(InterfaceConfig.getNowIPConfig().getRequestUrl() + "/" + serviceName + "/" + methodName, res);

            if (isSuccess) {
                requestListener.onRequestSuccess(methodName, new Gson().fromJson(res, HGResponseInfo.class));
            } else {
                activity.runOnUiThread(() -> requestListener.onRequestFail(methodName, res));
            }

            // 请求完成
            activity.runOnUiThread(() -> requestListener.onRequestFinish());

        }).start();
    }

}
