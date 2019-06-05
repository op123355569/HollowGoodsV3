package com.hg.hollowgoods.Util.UpdateAPP;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;

import com.hg.hollowgoods.Application.BaseApplication;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.UI.Fragment.Proxy.OnProxyActivityResult;
import com.hg.hollowgoods.Util.StringUtils;
import com.hg.hollowgoods.Util.SystemAppUtils;
import com.hg.hollowgoods.Util.XUtils.DownloadListener;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.io.File;

/**
 * HG APP版本更新工具类
 * Created by HG on 2016-11-28.
 */

public abstract class HGUpdateAPPUtils implements UpdateAPPController {

    private boolean isFromUser = false;
    private String URL = "";
    private BaseActivity baseActivity;
    private File apkFile;

    public HGUpdateAPPUtils(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    /**
     * 检查更新
     *
     * @param isFromUser 是否是用户手动点击
     */
    public void checkUpdate(boolean isFromUser) {

        this.isFromUser = isFromUser;

        if (this.isFromUser) {
            baseActivity.baseUI.baseDialog.showProgressDialog(R.string.update_app, HGConstants.UPDATE_APP_UTILS_CHECK_PROGRESS_DIALOG_CODE);
        } else {
            BaseApplication baseApplication = BaseApplication.create();

            String date = StringUtils.getDateTimeString(baseApplication.getNowTime(), StringUtils.DateFormatMode.LINE_YMD);
            if (TextUtils.equals(baseApplication.getAutoCheckUpdateAppDate(), date)) {
                return;
            }

            baseApplication.setAutoCheckUpdateAppDate(StringUtils.getDateTimeString(baseApplication.getNowTime(), StringUtils.DateFormatMode.LINE_YMD));
        }

        doCheck();
    }

    private void doCheck() {
        checkServerData();
//        RequestParams params = new RequestParams(InterfaceConfig.getNowIPConfig().getRequestUrl(HGInterfaceApi.UPDATE_APP));
//        params.addParameter("nowVersion", Build.VERSION.SDK_INT);
//
//        XUtils xUtils = new XUtils();
//        xUtils.setGetHttpDataListener(new GetHttpDataListener() {
//            @Override
//            public void onGetSuccess(String result) {
//
//                HGResponseInfo requestInfo = new Gson().fromJson(result, HGResponseInfo.class);
//
//                if ((TextUtils.equals(requestInfo.getStatus(), "true"))) {
//                    URL = InterfaceConfig.getNowIPConfig().getRequestUrl(requestInfo.getUrl());
//
//                    StringBuilder tip = new StringBuilder();
//                    tip.append("V");
//                    tip.append(APPUtils.getVersionName(baseActivity));
//                    tip.append(" → ");
//                    tip.append("V");
//                    char[] chars = String.valueOf(requestInfo.getData()).toCharArray();
//                    for (int i = 0; i < chars.length; i++) {
//                        tip.append(chars[i]);
//                        if (i != chars.length - 1) {
//                            tip.append(".");
//                        }
//                    }
//                    tip.append("\n\n");
//                    tip.append(requestInfo.getResult());
//
//                    showDialog(tip.toString());
//                } else {
//                    if (isFromUser) {
//                        t.showShortToast(R.string.update_app_already_new);
//                    }
//                }
//            }
//
//            @Override
//            public void onGetError(Throwable ex) {
//                if (isFromUser) {
//                    t.showShortToast(R.string.network_error);
//                }
//            }
//
//            @Override
//            public void onGetLoading(long total, long current) {
//
//            }
//
//            @Override
//            public void onGetFinish() {
//                if (isFromUser) {
//                    baseActivity.baseUI.baseDialog.closeDialog(HGConstants.UPDATE_APP_UTILS_CHECK_PROGRESS_DIALOG_CODE);
//                }
//            }
//
//            @Override
//            public void onGetCancel(Callback.CancelledException cex) {
//
//            }
//        });
//        xUtils.getHttpData(HttpMethod.GET, params);
    }

    public void showDialog(String tips) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle(R.string.update_app_find_new_version)
                .setMessage(StringUtils.isHtml(tips) ? Html.fromHtml(tips) : tips)
                .setPositiveButton(R.string.sure, (dialog, which) -> doDownloadAPK())
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .show();
    }

    private void doDownloadAPK() {

        baseActivity.baseUI.baseDialog.showProgressDialog(null, R.string.downloading, true, false, HGConstants.UPDATE_APP_UTILS_DOWNLOAD_PROGRESS_DIALOG_CODE);

        String path = HGSystemConfig.getDownloadFilePath();
        String name = System.currentTimeMillis() + ".apk";

        RequestParams params = new RequestParams(URL);

        XUtils xUtils = new XUtils();
        xUtils.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                baseActivity.baseUI.baseDialog.setProgress(100, HGConstants.UPDATE_APP_UTILS_DOWNLOAD_PROGRESS_DIALOG_CODE);
                apkFile = file;
                if (new SystemAppUtils().canInstallAPK(baseActivity)) {
                    new SystemAppUtils().installAPK(baseActivity, apkFile);
                } else {
                    new SystemAppUtils().requestInstallPermission(baseActivity, HGConstants.UPDATE_APP_UTILS_REQUEST_CODE_INSTALL, new OnProxyActivityResult() {
                        @Override
                        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                            onInstallRequestActivityResult(requestCode, resultCode);
                        }
                    });
                }
            }

            @Override
            public void onDownloadError(Throwable ex) {
                t.showShortToast(R.string.network_error);
            }

            @Override
            public void onDownloadLoading(long total, long current) {
                baseActivity.baseUI.baseDialog.setProgress((int) (current * 100 / total), HGConstants.UPDATE_APP_UTILS_DOWNLOAD_PROGRESS_DIALOG_CODE);
            }

            @Override
            public void onDownloadFinish() {
                baseActivity.baseUI.baseDialog.closeDialog(HGConstants.UPDATE_APP_UTILS_DOWNLOAD_PROGRESS_DIALOG_CODE);
            }

            @Override
            public void onDownloadCancel(Callback.CancelledException cex) {

            }
        });
        xUtils.downloadFile(HttpMethod.POST, params, path + name);
    }

    private void onInstallRequestActivityResult(int requestCode, int resultCode) {
        if (requestCode == HGConstants.UPDATE_APP_UTILS_REQUEST_CODE_INSTALL && resultCode == Activity.RESULT_OK) {
            new SystemAppUtils().installAPK(baseActivity, apkFile);
        }
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }
}
