package com.hg.hollowgoods.UI.MVP.Activity.Example;

import android.os.Handler;

import com.hg.hollowgoods.Bean.HGUser;

/**
 * @ClassName:数据层
 * @Description:
 * @author: HollowGoods
 * @date: 2019年01月17日
 */
public class MVPExamModel implements MVPExamContract.Model {

    private MVPExamContract.View mView;

    public MVPExamModel(MVPExamContract.View mView) {
        this.mView = mView;
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
        }, 3 * 1000l);
    }

}
