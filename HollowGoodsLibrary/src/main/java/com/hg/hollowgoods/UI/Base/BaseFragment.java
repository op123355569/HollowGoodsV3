package com.hg.hollowgoods.UI.Base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 基Fragment<br>
 * 特殊接口：DialogClickListener
 * <p>
 * Created by HG
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment implements IBaseFragment, OnCommonTitleClickListener, OnSearchViewClickListener {

    public BaseUI baseUI = new BaseUI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 初始化界面
        baseUI.initUI(this, inflater.inflate(bindLayout(), container, false));
        // 初始化意图传递的数据
        initIntentData();
        // 绑定EventBus
        baseUI.bindEventBus(initView(baseUI.rootView, savedInstanceState));
        // 设置公共标题点击事件监听
        baseUI.setOnCommonTitleClickListener(this);
        // 设置搜索框监听
        baseUI.setOnSearchViewClickListener(this);
        // 设置监听
        setListener();

        return baseUI.rootView;
    }

    /**
     * 设置Fragment的携带参数
     *
     * @param key
     * @param values
     * @return
     */
    public void setFragmentArguments(String[] key, Object[] values) {

        Bundle b = new Bundle();

        if (key.length == values.length) {
            for (int i = 0; i < key.length; i++) {
                if (values[i] instanceof String) {
                    b.putString(key[i], (String) values[i]);
                } else if (values[i] instanceof Integer) {
                    b.putInt(key[i], (Integer) values[i]);
                } else if (values[i] instanceof Boolean) {
                    b.putBoolean(key[i], (Boolean) values[i]);
                } else if (values[i] instanceof Float) {
                    b.putFloat(key[i], (Float) values[i]);
                } else if (values[i] instanceof Double) {
                    b.putDouble(key[i], (Double) values[i]);
                } else if (values[i] instanceof CharSequence) {
                    b.putCharSequence(key[i], (CharSequence) values[i]);
                } else if (values[i] instanceof Long) {
                    b.putLong(key[i], (Long) values[i]);
                } else if (values[i] instanceof Short) {
                    b.putShort(key[i], (Short) values[i]);
                } else if (values[i] instanceof Byte) {
                    b.putByte(key[i], (Byte) values[i]);
                } else if (values[i] instanceof Bundle) {
                    b.putBundle(key[i], (Bundle) values[i]);
                } else if (values[i] instanceof Serializable) {
                    if (values[i] instanceof ArrayList<?>) {
                        if (values[i] != null && ((ArrayList) (values[i])).size() != 0
                                && ((ArrayList) (values[i])).get(0) instanceof String) {
                            b.putStringArrayList(key[i], (ArrayList<String>) values[i]);
                        } else if (values[i] != null && ((ArrayList) (values[i])).size() != 0
                                && ((ArrayList) (values[i])).get(0) instanceof Serializable) {
                            b.putSerializable(key[i], (Serializable) values[i]);
                        } else {
                            // 不合法的参数
                        }
                    } else {
                        b.putSerializable(key[i], (Serializable) values[i]);
                    }
                } else if (values[i] instanceof Parcelable) {
                    b.putParcelable(key[i], (Parcelable) values[i]);
                } else {
                    // 不合法的参数
                }
            }
        } else {
            t.showShortToast(getString(R.string.length_different));
        }

        setArguments(b);
    }

    /**
     * 创建公共标题右侧菜单
     *
     * @param menu
     * @return
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        baseUI.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 执行EventBus
     *
     * @param item
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUI(Event item) {

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
     * @param searchKey
     */
    @Override
    public void onSearched(String searchKey) {

    }

    /**
     * 搜索框输入内容有变化
     *
     * @param searchKey
     */
    @Override
    public void onSearchKeyChanging(String searchKey) {

    }

    @Override
    public void onSearchMenuItemClick(int id) {

    }

}
