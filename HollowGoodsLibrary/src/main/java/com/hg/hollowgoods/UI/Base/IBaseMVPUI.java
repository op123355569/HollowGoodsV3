package com.hg.hollowgoods.UI.Base;

import com.hg.hollowgoods.UI.Base.MVP.IBasePresenter;

public interface IBaseMVPUI<P extends IBasePresenter> {

    /**
     * 创建Presenter
     *
     * @return
     */
    P createPresenter();

}
