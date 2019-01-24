package com.hg.hollowgoods.UI.Activity.Plugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Adapter.Plugin.FileAdapter;
import com.hg.hollowgoods.Adapter.Plugin.FileLabelAdapter;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.EventAction;
import com.hg.hollowgoods.Bean.Plugin.FileSelectorLabel;
import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.CacheUtils;
import com.hg.hollowgoods.Util.EncryptUtils;
import com.hg.hollowgoods.Util.FileSelectorUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName:文件选择器
 * @Description:
 * @author: 马禛
 * @date: 2018年09月28日
 */
public class FileSelectorActivity extends BaseActivity {

    private final String DEFAULT_FILE_PATH = FileUtils.getSDCardPath();

    private RecyclerView labels;
    private RecyclerView files;
    private ProgressBar progressBar;

    /**** 最多选择个数 ****/
    private int maxSelectorCount = 1;
    /**** 筛选格式 ****/
    private String tempFileFilter = "";
    private ArrayList<String> fileFilter = new ArrayList<>();
    private FileLabelAdapter labelAdapter;
    private ArrayList<FileSelectorLabel> labelData = new ArrayList<>();
    private FileAdapter fileAdapter;
    private ArrayList<File> fileData = new ArrayList<>();
    private FileSelectorUtils fileSelectorUtils = new FileSelectorUtils();
    private HashMap<String, File> selectedFiles = null;
    private int clickPosition;
    private int nowStep = 1;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_file_selector;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        labels = findViewById(R.id.rv_labels);
        files = findViewById(R.id.rv_files);
        progressBar = findViewById(R.id.progressBar);

        maxSelectorCount = getIntent().getIntExtra(Constants.PARAM_KEY_1, 1);
        tempFileFilter = getIntent().getStringExtra(Constants.PARAM_KEY_2);
        selectedFiles = (HashMap<String, File>) getIntent().getSerializableExtra(Constants.PARAM_KEY_3);

        if (maxSelectorCount != 1) {
            if (selectedFiles == null) {
                selectedFiles = new HashMap<>();
            }

            refreshCount();
        } else {
            selectedFiles = null;
        }

        if (!TextUtils.isEmpty(tempFileFilter)) {
            fileFilter = StringUtils.getStringArray(tempFileFilter, ",");
        }

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_file_selector);

        labels.setHasFixedSize(true);
        labels.setItemAnimator(new DefaultItemAnimator());
        labels.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        labelAdapter = new FileLabelAdapter(this, R.layout.item_file_label, labelData);
        labels.setAdapter(labelAdapter);

        files.setHasFixedSize(true);
        files.setItemAnimator(new DefaultItemAnimator());
        files.setLayoutManager(new LinearLayoutManager(this));

        fileAdapter = new FileAdapter(this, R.layout.item_file, fileData);
        fileAdapter.setSelectedFiles(selectedFiles);
        files.setAdapter(fileAdapter);

        return null;
    }

    @Override
    public void setListener() {

        fileSelectorUtils.setOnGetFileFinishListener(result -> {
            runOnUiThread(() -> {

                if (result != null) {
                    fileData.addAll(result);
                }

                fileAdapter.addDatas(fileData, 0, fileData.size());

                if (nowStep == 1) {
                    scrollToPosition(0, 0);
                } else {
                    scrollToPosition(labelData.get(labelData.size() - 1).getLastOffset(), labelData.get(labelData.size() - 1).getLastPosition());
                }

                progressBar.setVisibility(View.GONE);
            });
        });

        baseUI.setOnPermissionsListener((isAgreeAll, requestCode, permissions, isAgree) -> {
            if (isAgreeAll) {
                if (requestCode == baseUI.PERMISSION_CODE_IO) {
                    initData();
                }
            }
        });

        if (baseUI.requestIOPermission()) {
            initData();
        }

        labelAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false, true, false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                beforeDirectory(labelData.get(position));
            }
        });

        fileAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false, true, false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

                File item = fileData.get(position);
                if (item.isDirectory()) {
                    nextDirectory(new FileSelectorLabel(item, 0, 0));
                } else {
                    clickPosition = position;

                    if (maxSelectorCount == 1) {
                        backData();
                    } else {
                        checkFile();
                    }
                }
            }
        });
    }

    private void initData() {

        loadData();

        if (labelData.size() == 0) {
            nextDirectory(new FileSelectorLabel(new File(DEFAULT_FILE_PATH), 0, 0));
        } else {
            beforeDirectory(labelData.get(labelData.size() - 1));
        }
    }

    private void nextDirectory(FileSelectorLabel label) {

        nowStep = 1;

        if (labelData.size() > 0) {
            int[] pao = getPositionAndOffset();
            FileSelectorLabel item = labelData.get(labelData.size() - 1);
            item.setLastOffset(pao[0]);
            item.setLastPosition(pao[1]);
        }

        labelData.add(label);
        labelAdapter.addDatas(labelData, labelData.size() - 1, 1);
        labels.smoothScrollToPosition(labelData.size() - 1);

        fileData.clear();
        fileAdapter.refreshData(fileData);

        progressBar.setVisibility(View.VISIBLE);
        fileSelectorUtils.getFiles(label.getFile().getAbsolutePath(), false, true, fileFilter);

        if (baseUI.checkIOPermission()) {
            saveData();
        }
    }

    private void beforeDirectory(FileSelectorLabel label) {

        nowStep = -1;

        int index = labelData.indexOf(label);
        int removeCount = 0;
        for (int i = index + 1; i < labelData.size(); ) {
            labelData.remove(i);
            removeCount++;
        }
        if (removeCount > 0) {
            labelAdapter.removeDatas(labelData, index + 1, removeCount);
        } else {
            labelAdapter.refreshData(labelData);
        }
        labels.smoothScrollToPosition(labelData.size() - 1);

        fileData.clear();
        fileAdapter.refreshData(fileData);

        progressBar.setVisibility(View.VISIBLE);
        fileSelectorUtils.getFiles(label.getFile().getAbsolutePath(), false, true, fileFilter);

        if (baseUI.checkIOPermission()) {
            saveData();
        }
    }

    @Override
    public void onBackPressed() {
        if (labelData.size() < 2) {
            super.onBackPressed();
        } else {
            beforeDirectory(labelData.get(labelData.size() - 2));
        }
    }

    private void checkFile() {

        if (selectedFiles.get(fileData.get(clickPosition).getAbsolutePath()) == null) {
            if (selectedFiles.size() < maxSelectorCount) {
                selectedFiles.put(fileData.get(clickPosition).getAbsolutePath(), fileData.get(clickPosition));
            } else {
                String tips = getString(R.string.is_had_max_count);
                tips = String.format(tips, maxSelectorCount + "");
                t.warning(tips);
            }
        } else {
            selectedFiles.remove(fileData.get(clickPosition).getAbsolutePath());
        }

        fileAdapter.refreshData(fileData, clickPosition);
        refreshCount();
    }

    private void refreshCount() {

        StringBuilder sb = new StringBuilder();
        sb.append("确定");
        sb.append("(");
        sb.append(selectedFiles.size());
        sb.append("/");
        sb.append(maxSelectorCount);
        sb.append(")");

        baseUI.setCommonRightTitleText(sb.toString());
    }

    @Override
    public void onRightTitleClick(View view, int id) {
        backData();
    }

    private void backData() {
        Event event = new Event(EventAction.FileSelector);
        if (maxSelectorCount == 1) {
            event.getData().putSerializable(Constants.PARAM_KEY_1, fileData.get(clickPosition));
        } else {
            event.getData().putSerializable(Constants.PARAM_KEY_1, selectedFiles);
        }
        EventBus.getDefault().post(event);
        finishMyActivity();
    }

    /**
     * 记录RecyclerView当前位置
     */
    private int[] getPositionAndOffset() {

        int[] result = {
                0,
                0,
        };
        LinearLayoutManager layoutManager = (LinearLayoutManager) files.getLayoutManager();
        // 获取可视的第一个view
        View topView = layoutManager.getChildAt(0);

        if (topView != null) {
            //获取与该view的顶部的偏移量
            result[0] = topView.getTop();
            //得到该View的数组位置
            result[1] = layoutManager.getPosition(topView);
        }

        return result;
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition(int lastOffset, int lastPosition) {
        if (files.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) files.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    private final String CACHE_DATA_LABEL_NAME = EncryptUtils.md5Encrypt(FileSelectorActivity.class.getName() + "label");

    private void saveData() {
        CacheUtils.create().save(CACHE_DATA_LABEL_NAME, labelData);
    }

    private void loadData() {
        labelData = CacheUtils.create().load(CACHE_DATA_LABEL_NAME, new TypeToken<ArrayList<FileSelectorLabel>>() {
        }.getType());
        if (labelData == null) {
            labelData = new ArrayList<>();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseUI.checkIOPermission()) {
            if (labelData.size() > 0) {
                int[] pao = getPositionAndOffset();
                FileSelectorLabel item = labelData.get(labelData.size() - 1);
                item.setLastOffset(pao[0]);
                item.setLastPosition(pao[1]);
            }
            saveData();
        }
    }
}
