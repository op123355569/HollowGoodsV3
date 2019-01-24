package com.hg.hollowgoods.UI.Base.MVP;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2019年01月17日
 */
public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V mView;

    /**
     * 绑定View
     *
     * @param view
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
        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return
     */
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    public void afterAttachView() {

    }

}
