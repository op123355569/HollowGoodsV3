package com.hg.hollowgoods.UI.MVP.Activity.Example;

import com.hg.hollowgoods.Bean.HGUser;
import com.hg.hollowgoods.UI.Base.MVP.IBaseModel;
import com.hg.hollowgoods.UI.Base.MVP.IBaseView;

/**
 * @ClassName:协议层
 * @Description:
 * @author: HollowGoods
 * @date: 2019年01月16日
 */
public class MVPExamContract {

    public interface Model extends IBaseModel {
        void login(String username, String password);
    }

    public interface View extends IBaseView {
        void showProgressDialog();

        void hideProgressDialog();

        void onSuccess(HGUser user);
    }

    public interface Presenter {
        void login(String username, String password);
    }

}
