package com.hg.hollowgoods.UI.Base.MVP;

import android.content.Context;

/**
 * Created by Hollow Goods on 2019-01-17.
 */
public class BasePresenter<V extends IBaseView, P extends IBaseModel> implements IBasePresenter<V> {

    protected V mView;
    protected P mModel;
    protected Context mContext;

    public BasePresenter() {
        
    }

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 绑定View
     *
     * @param view view
     */
    @Override
    public void attachView(V view) {
        this.mView = view;
        afterAttachView();
    }

    /**
     * 解绑View
     */
    @Override
    public void detachView() {
        this.mModel.detachView();
        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return boolean
     */
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    public void afterAttachView() {

    }

}
