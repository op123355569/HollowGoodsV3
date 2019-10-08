package com.hg.hollowgoods.UI.Base;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
 * Created by Hollow Goods on unknown.
 */
@SuppressLint("NewApi")
public abstract class BaseMVPActivity<P extends IBasePresenter> extends BaseActivity implements IBaseMVPUI<P>, IBaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null && mPresenter.isViewAttached()) {
            mPresenter.detachView();
            this.mPresenter = null;
        }
        super.onDestroy();
    }

}
