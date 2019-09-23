package com.hg.hollowgoods.UI.Base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Widget.SlideBack.SlideBack;
import com.hg.hollowgoods.Widget.SlideBack.callback.SlideBackCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/**
 * 基Activity<p>
 * 如需开启沉浸式，在SystemConfig中修改IMMERSE_MODE为true<p>
 * 设置方式：<p>
 * 1.设置公共标题的背景颜色<p>
 * 2.手动调用setActionBar<p>
 * 特殊接口：IDialogClickListener
 * <p>
 * Created by HG
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity, OnCommonTitleClickListener, OnSearchViewClickListener {

    public BaseUI baseUI = new BaseUI();
    /**** OnCreate是否已调用 ****/
    private boolean onCreateFlag = false;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 初始化界面
        baseUI.initUI(addToExitGroup(), LayoutInflater.from(this).inflate(bindLayout(), null));
        // 初始化意图传递的数据
        initParamData();
        initIntentData(getIntent());
        // 绑定EventBus
        baseUI.bindEventBus(initView(baseUI.rootView, savedInstanceState));
        // 设置公共标题点击事件监听
        baseUI.setOnCommonTitleClickListener(this);
        // 设置搜索框监听
        baseUI.setOnSearchViewClickListener(this);
        // 设置监听
        setListener();

        // 绑定侧滑返回
        if (HGSystemConfig.IS_OPEN_SLIDEBACK && isOpenSlideBack()) {
            // 在需要滑动返回的Activity中注册，最好但非必须在onCreate中
//            SlideBack.with(this)
//                    .callBack(this::onSlideBackWork)
//                    .haveScroll(haveScroll())
//                    .register();

            // 为了配合RePlugin，不能使用lambda表达式
            SlideBack.with(this)
                    .callBack(new SlideBackCallBack() {
                        @Override
                        public void onSlideBack() {
                            onSlideBackWork();
                        }
                    })
                    .haveScroll(haveScroll())
                    .register();
        }

        onCreateFlag = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (onCreateFlag && hasFocus) {
            onCreateFlag = false;
//            new Handler(Looper.getMainLooper()).post(this::initViewDelay);

            // 为了配合RePlugin，不能使用lambda表达式
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    initViewDelay();
                }
            });
        }
    }

    /**
     * 创建公共标题右侧菜单
     *
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        baseUI.onCreateOptionsMenu(menu, null);
        return true;
    }

    @Override
    public void onSlideBackWork() {
        finishMyActivity();
    }

    @Override
    public void onBackPressed() {
        finishMyActivity();
    }

    public void finishMyActivity() {
        if (baseUI.hasSharedElement) {
            super.onBackPressed();
        } else {
            ActivityCompat.finishAfterTransition(baseUI.getBaseContext());
            overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_FINISH_IN, HGSystemConfig.NOW_ACTIVITY_FINISH_OUT);
        }
    }

    @Override
    protected void onDestroy() {
        baseUI.onDestroy();
        // 解绑侧滑返回
        if (HGSystemConfig.IS_OPEN_SLIDEBACK && isOpenSlideBack()) {
            // onDestroy时解绑
            // 内部使用WeakHashMap，理论上不解绑也行，但最好还是手动解绑一下
            SlideBack.unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
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
