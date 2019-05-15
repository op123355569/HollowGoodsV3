package com.hg.hollowgoods.UI.MVP.Activity.Example;

import android.content.Context;
import android.os.Handler;

import com.hg.hollowgoods.Bean.HGUser;

/**
 * 数据层
 * Created by Hollow Goods on 2019-01-16.
 */
public class MVPExamModel implements MVPExamContract.Model {

    private MVPExamContract.View mView;
    protected Context mContext;

    public MVPExamModel(MVPExamContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    @Override
    public void login(String username, String password) {

        if (isViewAttached()) {
            mView.showProgressDialog();
        }

        new Handler().postDelayed(() -> {

            if (isViewAttached()) {
                mView.hideProgressDialog();
            }

            HGUser user = new HGUser();
            user.setUsername(username);
            user.setPassword(password);

            if (isViewAttached()) {
                mView.onSuccess(user);
            }
        }, 3 * 1000L);
    }

}
