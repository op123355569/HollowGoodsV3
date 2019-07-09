package com.hg.hollowgoods.UI.Activity.Plugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.Plugin.MediaAdapter;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.SystemAppUtils.SystemAppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 多媒体预览界面
 * Created by HG on 2018-06-15.
 */
public class ImagePreActivity extends BaseActivity {

    private final int DIALOG_CODE_REMOVE_IMAGE = 3366;

    private ArrayList<Media> data;
    private MediaAdapter adapter;
    private SystemAppUtils systemAppUtils = new SystemAppUtils();
    private int clickPosition;
    private String title;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_image_pre;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        title = getIntent().getStringExtra(HGParamKey.Title.getValue());
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.title_activity_image_pre);
        }

        data = (ArrayList<Media>) getIntent().getSerializableExtra(HGParamKey.ListData.getValue());
        if (data == null) {
            data = new ArrayList<>();
        }

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, title);

        RecyclerView result = findViewById(R.id.rv_result);
        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new MediaAdapter(this, R.layout.item_fast_image, data, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int position) {
                tipRemoveImage(position);
            }
        });
        result.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {

        baseUI.baseDialog.setOnDialogClickListener((code, result, backData) -> {

            if (result) {
                switch (code) {
                    case DIALOG_CODE_REMOVE_IMAGE:
                        data.remove(clickPosition);
                        adapter.removeData(data, clickPosition, 1);
                        backData();
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {

            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

                String name;
                String url;
                if (data.get(position).getFile() == null) {
                    name = data.get(position).getOldName();
                    url = data.get(position).getUrl();
                } else {
                    name = data.get(position).getFile().getName();
                    url = data.get(position).getFile().getAbsolutePath();
                }

                if (FileUtils.isImageFile(url)) {
                    ArrayList<String> urls = new ArrayList<>();
                    int imageCount = 0;
                    int imagePosition = 0;

                    for (Media t : data) {
                        if (t.getFile() == null) {
                            url = t.getUrl();
                        } else {
                            url = t.getFile().getAbsolutePath();
                        }

                        if (FileUtils.isImageFile(url)) {
                            if (t.getFile() != null) {
                                urls.add(t.getFile().getAbsolutePath());
                            } else if (!TextUtils.isEmpty(t.getUrl())) {
                                urls.add(t.getUrl());
                            } else {
                                urls.add("");
                            }

                            if (t == data.get(position)) {
                                imagePosition = imageCount;
                            }

                            imageCount++;
                        }
                    }

                    systemAppUtils.previewPhotos(baseUI.getBaseContext(), urls, imagePosition);
                } else if (FileUtils.isOfficeFile(url)) {
                    systemAppUtils.readFile(baseUI.getBaseContext(), url, name);
                } else {
                    t.error(R.string.not_support_file_type);
                }
            }

            @Override
            public void onRecyclerViewItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                tipRemoveImage(position);
            }
        });
    }

    private void tipRemoveImage(int position) {
        if (data.get(position).isCanRemove()) {
            clickPosition = position;
            baseUI.baseDialog.showAlertDialog(R.string.tips_best, R.string.is_sure_delete_image, DIALOG_CODE_REMOVE_IMAGE);
        }
    }

    private void backData() {
        Event event = new Event(HGEventActionCode.REMOVE_IMAGE);
        event.getData().putInt(HGParamKey.Position.getValue(), clickPosition);
        EventBus.getDefault().post(event);
    }

}
