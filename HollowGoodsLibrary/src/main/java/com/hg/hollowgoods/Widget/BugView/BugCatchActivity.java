package com.hg.hollowgoods.Widget.BugView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.Glide.GlideUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Bug捕捉界面
 * Created by HG on 2017-06-06.
 */

public class BugCatchActivity extends BaseActivity {

    private final String IMG_PATH = HGSystemConfig.getBugPath() + "/img/";
    private final String DATA_PATH = HGSystemConfig.getBugPath() + "/txt/";
    private final String OUTPUT_PATH = HGSystemConfig.getBugPath();

    private ImageView mCatchDialogImg;
    private Spinner mCatchDialogModuleName;
    private EditText mCatchDialogInput;

    private ArrayList<BugData> mBugData = new ArrayList<>();
    private String mFileName;
    private String mOutputName;
    private String mImgName;
    private boolean hasActivity = false;
    private static int mCatchDialogCheckedPosition = 0;

    @Override
    public int bindLayout() {
        return R.layout.activity_bug_catch;
    }

    @Override
    public Activity addToExitGroup() {
        return null;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        mImgName = getIntent().getStringExtra("data");
        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_bug_catch);

        mCatchDialogImg = findViewById(R.id.iv_img);
        mCatchDialogModuleName = findViewById(R.id.sp_module_name);
        mCatchDialogInput = findViewById(R.id.et_input);

        RequestOptions options = new RequestOptions()
                .placeholder(HGCommonResource.IMAGE_LOADING)
                .error(HGCommonResource.IMAGE_LOAD_ERROR);
        GlideOptions glideOptions = new GlideOptions(new File(IMG_PATH + mImgName), mCatchDialogImg, GlideOptions.NORMAL_FADE_IN, options);
        glideOptions.setThumbnail(0.1f);
        GlideUtils.loadImg(this, glideOptions);

        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        ArrayList<String> modules = Bug.getModuleNames();
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modules);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mCatchDialogModuleName.setAdapter(adapter);
        mCatchDialogModuleName.setSelection(mCatchDialogCheckedPosition);

        getBugData();

        return null;
    }

    @Override
    public void setListener() {

        if (hasActivity) {
            mCatchDialogModuleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mCatchDialogCheckedPosition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mCatchDialogImg.setOnClickListener(v -> {

            });
        }
    }


    @Override
    public void onRightTitleClick(View view, int id) {
        if (hasActivity) {
            mBugData.add(new BugData(
                    Bug.getModuleNames().get(mCatchDialogCheckedPosition),
                    IMG_PATH + mImgName,
                    mCatchDialogInput.getText().toString().trim()
            ));
            saveBug();
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
            }
        } else {
            baseUI.setCommonRightTitleText("暂无窗口");
        }
    }

    private void saveBug() {

        String temp = new Gson().toJson(mBugData);
        FileUtils.saveToSD(DATA_PATH + mFileName, temp);

        t.showShortToast("保存成功");
        finishMyActivity();
    }

}
