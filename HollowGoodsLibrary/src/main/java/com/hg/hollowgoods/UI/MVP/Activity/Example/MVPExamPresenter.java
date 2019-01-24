package com.hg.hollowgoods.UI.MVP.Activity.Example;

import com.hg.hollowgoods.UI.Base.MVP.BasePresenter;

/**
 * @ClassName:管理层
 * @Description:
 * @author: 马禛
 * @date: 2019年01月16日
 */
public class MVPExamPresenter extends BasePresenter<MVPExamContract.View, MVPExamContract.Model> implements MVPExamContract.Presenter {

    @Override
    public void afterAttachView() {
        mModel = new MVPExamModel(mView);
    }

    @Override
    public void login(String username, String password) {
        mModel.login(username, password);
    }

}
