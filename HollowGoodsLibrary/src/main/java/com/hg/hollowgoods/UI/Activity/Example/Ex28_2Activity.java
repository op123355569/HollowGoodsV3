package com.hg.hollowgoods.UI.Activity.Example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastItemData;
import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.ParamItem;
import com.hg.hollowgoods.Adapter.FastAdapter.FastAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Bean.Example.Ex28;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Plugin.ImagePreActivity;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Widget.MultiChoicesCircleButton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 快速适配器示例
 * Created by HG on 2018-03-21.
 */

public class Ex28_2Activity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView result;
    private MultiChoicesCircleButton multiChoicesCircleButton;

    private FastAdapter adapter;
    private ArrayList<CommonBean> data = new ArrayList<>();
    private Ex28 parentData;
    private int clickPosition;
    private int clickSortNumber;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_28_2;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        result = findViewById(R.id.rv_result);
        multiChoicesCircleButton = findViewById(R.id.multiChoicesCircleButton);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex28);

        refreshLayout.setColorSchemeColors(HGSystemConfig.REFRESH_COLORS);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this));

        parentData = new Ex28(FastAdapter.ITEM_TYPE_ITEM);
        parentData.setTitle("我是标题");
        parentData.setName("张三");
        parentData.setAge(1);
        parentData.setSex(1);
        parentData.setHobby("打灰机");
        parentData.setNeedButton(true);
        parentData.setUrl("http://img1.imgtn.bdimg.com/it/u=3180728821,3067358428&fm=27&gp=0.jpg");
        parentData.setBirthday(1500000000000l);

        FastAdapter.setAllItemOnlyRead(parentData, false);

        adapter = new FastAdapter(this, data, false, true);
//        adapter.initSubmitButton(view, result);
        result.setAdapter(adapter);

        data.addAll(adapter.getDetailItemData(parentData));
        adapter.refreshData(data);

        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("Like", getResources().getDrawable(R.drawable.ic_adb_white_24dp), 30);
        buttonItems.add(item1);
        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("Message", getResources().getDrawable(R.drawable.ic_android_white_24dp), 90);
        buttonItems.add(item2);
        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("Tag", getResources().getDrawable(R.drawable.ic_room_white_24dp), 150);
        buttonItems.add(item3);

        multiChoicesCircleButton.setButtonItems(buttonItems);
        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                t.showShortToast(item.getText());
            }
        });

        return this;
    }

    @Override
    public void setListener() {

        adapter.setOnFastClick(new OnFastClick() {

            @Override
            public void onTakePhotoClick(View view, int position, int sortNumber) {

                clickPosition = position;
                clickSortNumber = sortNumber;

                if (baseUI.requestPermission(FastAdapter.REQUEST_CODE_TAKE_PHOTO, Manifest.permission.CAMERA)) {
                    adapter.takePhoto(baseUI.getBaseContext(), position, sortNumber);
                }
            }

            @Override
            public void onOpenAlbumClick(View view, int position, int sortNumber) {

                clickPosition = position;
                clickSortNumber = sortNumber;

                adapter.openAlbum(baseUI.getBaseContext(), parentData, HGSystemConfig.HG_PHOTO_MAX_COUNT, position, sortNumber);
            }

            @Override
            public void onSubmitClick(View view, int id) {
                if (adapter.checkNotEmptyItem(parentData)) {
                    baseUI.baseDialog.showTipDialog("测试", new Gson().toJson(parentData), HGConstants.DEFAULT_CODE);
                    LogUtils.Log(new Gson().toJson(parentData));
                }
            }

            @Override
            public void onOperateClick(View view, int position, int sortNumber) {
                onFastItemClick(view, position, sortNumber);
            }

            @Override
            public void onFilePreClick(View view, int position, int sortNumber) {

                clickPosition = position;
                clickSortNumber = sortNumber;

                baseUI.startMyActivity(ImagePreActivity.class,
                        new String[]{HGConstants.PARAM_KEY_1},
                        new Object[]{parentData.getMedia().get(sortNumber)}
                );
            }

            @Override
            public void onNumberPickerClick(View view, int position, int sortNumber, Object num) {

                clickPosition = position;
                clickSortNumber = sortNumber;

                switch (sortNumber) {
                    case 2:
                        // 年龄
                        parentData.setAge((Integer) num);
                        adapter.refreshFastItem(parentData, position);
                        break;
                }
            }

            @Override
            public void onEditClick(View view, int position) {

            }

            @Override
            public void onDeleteClick(View view, int position) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                FastItemData item = data.get(position).getData();
                int sortNumber = item.sortNumber;

                onFastItemClick(view, position, sortNumber);
            }
        });

        baseUI.baseDialog.setOnDialogClickListener((code, result, data) -> {

            if (result) {
                String value;
                int position;

                switch (code) {
                    case 1:
                        // 姓名
                        value = data.getString(HGConstants.PARAM_KEY_1, "");
                        parentData.setName(value);
                        break;
                    case 2:
                        // 年龄
                        value = data.getString(HGConstants.PARAM_KEY_1, "");
                        if (TextUtils.isEmpty(value)) {
                            value = "0";
                        }
                        parentData.setAge(Integer.valueOf(value));
                        break;
                    case 3:
                        position = data.getInt(HGConstants.PARAM_KEY_1, -1);
                        if (position != -1) {
                            parentData.setSex(position);
                        }
                        break;
                    case 4:
                        // 爱好
                        value = data.getString(HGConstants.PARAM_KEY_1, "");
                        parentData.setHobby(value);
                        break;
                }

                adapter.refreshFastItem(parentData, clickPosition);
            }
        });

        baseUI.setOnPermissionsListener((isAgreeAll, requestCode, permissions, isAgree) -> {
            if (isAgreeAll) {
                switch (requestCode) {
                    case FastAdapter.REQUEST_CODE_TAKE_PHOTO:
                        adapter.takePhoto(baseUI.getBaseContext(), clickPosition, clickSortNumber);
                        break;
                }
            }
        });
    }

    @Override
    public void onEventUI(Event item) {

        switch (item.getEventActionCode()) {
            case HGEventActionCode.REMOVE_IMAGE:
                parentData.getMedia().get(clickSortNumber).remove(item.getData().getInt(HGConstants.PARAM_KEY_1, 0));
                adapter.refreshFastItem(parentData, clickPosition);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.onActivityResultForImage(this, parentData, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onFastItemClick(View view, int position, int sortNumber) {

        clickPosition = position;
        clickSortNumber = sortNumber;

        switch (sortNumber) {
            case 1:
                // 姓名
                baseUI.baseDialog.showInputDialog("请输入姓名", parentData.getName(), HGConstants.INPUT_TYPE_DEFAULT, sortNumber, HGConstants.INPUT_SIZE_DEFAULT, HGConstants.INPUT_SIZE_DEFAULT);
                break;
            case 2:
                // 年龄
                baseUI.baseDialog.showInputDialog("请输入年龄", parentData.getAge() + "", HGConstants.INPUT_TYPE_NUMBER, sortNumber, HGConstants.INPUT_SIZE_DEFAULT, HGConstants.INPUT_SIZE_DEFAULT);
                break;
            case 3:
                // 性别
                baseUI.baseDialog.showSingleDialog("请选择性别", ParamItem.SEX, parentData.getSex(), sortNumber);
                break;
            case 4:
                // 爱好
                baseUI.baseDialog.showInputDialog("请输入爱好", parentData.getHobby(), HGConstants.INPUT_TYPE_DEFAULT, sortNumber, HGConstants.INPUT_SIZE_DEFAULT, HGConstants.INPUT_SIZE_DEFAULT);
                break;
            case 5:
                // 生活照
                adapter.showImageWindow(view, position, sortNumber);
                break;
            case 6:
                // 是否已婚
                parentData.setMarry(!parentData.isMarry());
                adapter.refreshFastItem(parentData, position);
                break;
            case 7:
                // 出生日期
                t.warning("出生日期");
                break;
        }
    }

}
