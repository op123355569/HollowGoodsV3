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
import com.hg.hollowgoods.Bean.EventBus.EventAction;
import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.SystemAppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 图片预览界面
 * Created by HG on 2018-06-15.
 */
public class ImagePreActivity extends BaseActivity {

    private RecyclerView result;

    private ArrayList<Media> data;
    private MediaAdapter adapter;
    private SystemAppUtils systemAppUtils = new SystemAppUtils();
    private int clickPosition;

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

        result = findViewById(R.id.rv_result);

        data = (ArrayList<Media>) getIntent().getSerializableExtra(Constants.PARAM_KEY_1);
        if (data == null) {
            data = new ArrayList<>();
        }

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_image_pre);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new MediaAdapter(this, R.layout.item_fast_image, data, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int position) {
                clickPosition = position;
                baseUI.baseDialog.showAlertDialog(R.string.tips_best, R.string.is_sure_delete_image, 3366);
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
                    case 3366:
                        data.remove(clickPosition);
                        adapter.removeDatas(data, clickPosition, 1);
                        backData();
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

                ArrayList<String> urls = new ArrayList<>();

                for (Media t : data) {
                    if (t.getFile() != null) {
                        urls.add(t.getFile().getAbsolutePath());
                    } else if (!TextUtils.isEmpty(t.getUrl())) {
                        urls.add(t.getUrl());
                    } else {
                        urls.add("");
                    }
                }

                systemAppUtils.previewPhotos(baseUI.getBaseContext(), urls, position);
            }
        });
    }

    private void backData() {
        Event event = new Event(EventAction.RemoveImage);
        event.getData().putInt(Constants.PARAM_KEY_1, clickPosition);
        EventBus.getDefault().post(event);
    }

}
