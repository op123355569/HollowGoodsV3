package com.hg.hollowgoods.UI.Base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.UI.Base.MVP.IBasePresenter;
import com.hg.hollowgoods.UI.Base.MVP.IBaseView;

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
@SuppressLint("NewApi")
public abstract class BaseMVPFragment<P extends IBasePresenter> extends BaseFragment implements IBaseMVPUI<P>, IBaseView {

    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null && mPresenter.isViewAttached()) {
            mPresenter.detachView();
            this.mPresenter = null;
        }
        super.onDestroy();
    }

}
