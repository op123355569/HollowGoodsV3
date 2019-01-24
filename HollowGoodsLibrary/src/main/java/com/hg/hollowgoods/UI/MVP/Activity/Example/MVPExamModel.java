package com.hg.hollowgoods.UI.MVP.Activity.Example;

import android.os.Handler;

import com.hg.hollowgoods.Bean.User;

/**
 * @ClassName:数据层
 * @Description:
 * @author: 马禛
 * @date: 2019年01月17日
 */
public class MVPExamModel implements MVPExamContract.Model {

    private MVPExamContract.View mView;

    public MVPExamModel(MVPExamContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void login(String username, String password) {

        mView.showProgressDialog();

        new Handler().postDelayed(() -> {

            mView.hideProgressDialog();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            mView.onSuccess(user);

        }, 3 * 1000l);
    }

}
