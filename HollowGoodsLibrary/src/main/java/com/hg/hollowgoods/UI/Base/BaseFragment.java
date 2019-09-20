package com.hg.hollowgoods.UI.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.Constant.HGSystemConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 基Fragment<br>
 * 特殊接口：DialogClickListener
 * <p>
 * Created by HG
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment, OnCommonTitleClickListener, OnSearchViewClickListener {

    public BaseUI baseUI = new BaseUI();
    private boolean hasRootViewLoaded = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 初始化界面
        baseUI.initUI(this, inflater.inflate(bindLayout(), container, false));
        // 初始化意图传递的数据
        initParamData();
        initArgumentsData(getArguments());
        // 绑定EventBus
        baseUI.bindEventBus(initView(baseUI.rootView, savedInstanceState));
        // 设置公共标题点击事件监听
        baseUI.setOnCommonTitleClickListener(this);
        // 设置搜索框监听
        baseUI.setOnSearchViewClickListener(this);

        baseUI.rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (!hasRootViewLoaded) {
                hasRootViewLoaded = true;
                initViewDelay();
            }
        });
        // 设置监听
        setListener();

        return baseUI.rootView;
    }

    /**
     * 设置Fragment的携带参数
     *
     * @param key   key
     * @param value value
     */
    public void setFragmentArguments(String[] key, Object[] value) {

        Bundle b = new Bundle();

        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            params.put(key[i], value[i]);
        }
        b.putString(HGParamKey.MapData.getValue(), new Gson().toJson(params));

        setArguments(b);
    }

    /**
     * 创建公共标题右侧菜单
     *
     * @param menu     menu
     * @param inflater inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        baseUI.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        baseUI.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        baseUI.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 执行EventBus
     *
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUI(Event event) {

    }

    /**
     * 左标题点击事件
     */
    @Override
    public void onLeftTitleClick(View view) {
        finishMyActivity();
    }

    /**
     * 中标题点击事件
     */
    @Override
    public void onCenterTitleClick(View view) {

    }

    /**
     * 右标题点击事件
     */
    @Override
    public void onRightTitleClick(View view, int id) {

    }

    /**
     * 无数据控件点击事件
     **/
    @Override
    public void onNoDataViewClick(View v) {

    }

    public void finishMyActivity() {
        ActivityCompat.finishAfterTransition(baseUI.getBaseContext());
        baseUI.getBaseContext().overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_FINISH_IN, HGSystemConfig.NOW_ACTIVITY_FINISH_OUT);
    }

    /**
     * 点击了历史记录列表或者按了回车
     *
     * @param searchKey searchKey
     */
    @Override
    public void onSearched(String searchKey) {

    }

    /**
     * 搜索框输入内容有变化
     *
     * @param searchKey searchKey
     */
    @Override
    public void onSearchKeyChanging(String searchKey) {

    }

    /**
     * 搜索框菜单点击
     *
     * @param id id
     */
    @Override
    public void onSearchMenuItemClick(int id) {

    }

}
