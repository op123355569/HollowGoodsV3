package com.hg.hollowgoods.Widget.BugView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Dialog.OnDialogClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.StringUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Bug查看界面
 * Created by HG on 2017-06-06.
 */

public class BugWatchActivity extends BaseActivity implements OnDialogClickListener {

    private final int BUG_VIEW_DIALOG_CODE = -9998;
    private final String IMG_PATH = HGSystemConfig.getBugPath() + "/img/";
    private final String DATA_PATH = HGSystemConfig.getBugPath() + "/txt/";
    private final String OUTPUT_PATH = HGSystemConfig.getBugPath();

    private ListView mWatchResult;
    private Button mWatchExport;
    private Button mWatchClear;

    private BugAdapter mWatchAdapter;
    private ArrayList<BugData> mBugData = new ArrayList<>();
    private int mWatchCheckedPosition = -1;
    private String mFileName;
    private String mOutputName;
    private boolean hasActivity = false;

    @Override
    public int bindLayout() {
        return R.layout.activity_bug_watch;
    }

    @Override
    public Activity addToExitGroup() {
        return null;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_bug_watch);

        mWatchResult = findViewById(R.id.lv_result);
        mWatchExport = findViewById(R.id.btn_export);
        mWatchClear = findViewById(R.id.btn_clear);

        mWatchAdapter = new BugAdapter(this, mBugData);
        mWatchResult.setAdapter(mWatchAdapter);

        getBugData();

        return null;
    }

    @Override
    public void setListener() {

        if (hasActivity) {
            baseUI.baseDialog.setOnDialogClickListener(this);
            mWatchExport.setOnClickListener(v -> {

                if (mBugData.size() != 0) {
                    doExport();
                } else {
                    baseUI.baseDialog.showTipDialog(R.string.tips, "暂无Bug，无需导出", BUG_VIEW_DIALOG_CODE);
                }
            });
            mWatchClear.setOnClickListener(v -> {
                mWatchCheckedPosition = -9999;
                baseUI.baseDialog.showAlertDialog("删除Bug信息", "是否要删除\"所有Bug信息\"？", BUG_VIEW_DIALOG_CODE);
            });
            mWatchResult.setOnItemClickListener((parent, view, position, id) -> {

                mWatchCheckedPosition = position;
                baseUI.baseDialog.showAlertDialog("删除Bug信息", "是否要删除\"" + mBugData.get(position).getDescribe() + "\"？", BUG_VIEW_DIALOG_CODE);
            });
        }
    }

    private void getBugData() {

        IBaseApplication baseApplication = ApplicationBuilder.create();

        if (baseApplication.getAllActivity() != null && baseApplication.getAllActivity().size() > 0) {
            hasActivity = true;

            FileUtils.checkFileExist(IMG_PATH);
            FileUtils.checkFileExist(DATA_PATH);
            FileUtils.checkFileExist(OUTPUT_PATH);

            Activity activity = baseApplication.getAllActivity().get(baseApplication.getAllActivity().size() - 1);

            mOutputName = activity.getClass().getName();
            mFileName = EncryptUtils.md5Encrypt(mOutputName);

            if (FileUtils.checkFileExist2(DATA_PATH + mFileName)) {
                String temp = FileUtils.loadFromSDCard(DATA_PATH + mFileName);
                mBugData = new Gson().fromJson(temp, new TypeToken<ArrayList<BugData>>() {
                }.getType());
                if (mBugData == null) {
                    mBugData = new ArrayList<>();
                }
                mWatchAdapter.refreshData(mBugData);
            }

            baseUI.setCommonRightTitleText(mBugData.size() + "条");
        } else {
            baseUI.setCommonRightTitleText("暂无窗口");
        }
    }

    @Override
    public void onDialogClick(int code, boolean result, Bundle backData) {

        if (result) {
            deleteBug();
        }
    }

    private void deleteBug() {

        if (mWatchCheckedPosition != -9999) {
            mBugData.remove(mWatchCheckedPosition);
        } else {
            mBugData.clear();
        }
        mWatchAdapter.refreshData(mBugData);
        baseUI.setCommonRightTitleText(mBugData.size() + "条");

        saveBug();
    }

    private void saveBug() {

        String temp = new Gson().toJson(mBugData);
        FileUtils.saveToSD(DATA_PATH + mFileName, temp);

        t.showShortToast("删除成功");
    }

    private void doExport() {

        BugOtherHtml.checkFile(this, HGSystemConfig.getBugPath() + "/css/", "YLlightbox.css");
        BugOtherHtml.checkFile(this, HGSystemConfig.getBugPath() + "/js/", "jquery-1.11.2.min.js");
        BugOtherHtml.checkFile(this, HGSystemConfig.getBugPath() + "/js/", "YLlightbox.js");
        BugOtherHtml.checkFile(this, HGSystemConfig.getBugPath() + "/img/", "ico_direction.png");
        BugOtherHtml.checkFile(this, HGSystemConfig.getBugPath() + "/img/", "imgzoom_tb.gif");

        StringBuilder str = new StringBuilder("");
        str.append("<html>");
        str.append("<head>");
        str.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        str.append(BugOtherHtml.getCSS());
        str.append(BugOtherHtml.getScript(EncryptUtils.md5Encrypt(mFileName + System.currentTimeMillis())));
        str.append("</head>");

        str.append("<body>");

        str.append("<h2>");
        str.append("共" + mBugData.size() + "条Bug");
        str.append("</h2>");
        str.append("<h3>");
        str.append(StringUtils.getDateTimeString(System.currentTimeMillis(), StringUtils.DateFormatMode.Chinese_YMDHMS));
        str.append("</h3>");

        str.append("<button type=\"button\" class=\"button gray\" onclick=\"clearLocalData()\">Reset</button>");

        str.append("<div class=\"YLlightbox\"\n" +
                "     style=\"width:1000px; height:50%; text-align:center; \">");

        str.append("<table class=\"bordered\"  id=\"myTable\">");

        str.append("<thead>");
        str.append("<tr>");

        str.append("<th>");
        str.append("序号");
        str.append("</th>");

        str.append("<th>");
        str.append("模块名称");
        str.append("</th>");

        str.append("<th>");
        str.append("Bug描述");
        str.append("</th>");

        str.append("<th>");
        str.append("截图");
        str.append("</th>");

        str.append("<th>");
        str.append("修复");
        str.append("</th>");

        str.append("</tr>");
        str.append("</thead>");

        for (int i = 0; i < mBugData.size(); i++) {
            str.append("<tr>");

            str.append("<td>");
            str.append(i + 1);
            str.append("</td>");

            str.append("<td>");
            str.append(mBugData.get(i).getModuleName());
            str.append("</td>");

            str.append("<td>");
            str.append(mBugData.get(i).getDescribe());
            str.append("</td>");

            str.append("<td>");
            str.append("<a href=\"img/" + new File(mBugData.get(i).getImgPath()).getName() + "\" rel=\"YLlightbox\">\n" +
                    "                    <img style=\"background: #FCF;width: 360px;height: 640px\" src=\"img/" + new File(mBugData.get(i).getImgPath()).getName() + "\"/>\n" +
                    "                </a>");
            str.append("</td>");

            str.append("<td>");
            str.append("<input class='tgl tgl-light' id='repair" + i + "' name=\"repair\" type=\"checkbox\" value=\"\" onclick=\"alterStatus(" + i + ")\"/>");
            str.append("<label class='tgl-btn' for='repair" + i + "'/>");
            str.append("</td>");

            str.append("</tr>");
        }

        str.append("</table>");

        str.append("</div>");

        str.append("</body>");
        str.append("</html>");

        FileUtils.saveToSD(OUTPUT_PATH + mOutputName + ".html", str.toString());
        t.showShortToast("导出成功");
        finishMyActivity();
    }

}
