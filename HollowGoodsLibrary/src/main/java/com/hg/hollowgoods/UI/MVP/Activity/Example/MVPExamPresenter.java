package com.hg.hollowgoods.UI.MVP.Activity.Example;

import android.content.Context;

import com.hg.hollowgoods.UI.Base.MVP.BasePresenter;

/**
 * 管理层
 * Created by Hollow Goods on 2019-01-16.
 */
public class MVPExamPresenter extends BasePresenter<MVPExamContract.View, MVPExamContract.Model> implements MVPExamContract.Presenter {

    public MVPExamPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    public void afterAttachView() {
        mModel = new MVPExamModel(mView, mContext);
    }

    @Override
    public void login(String username, String password) {
        mModel.login(username, password);
    }

}
