package com.hg.hollowgoods.UI.Base;

import android.app.Activity;

public interface IBaseActivity extends IBaseFragment {

    /**
     * 添加当前Activity全局退出队列
     *
     * @return Activity
     */
    Activity addToExitGroup();

}
