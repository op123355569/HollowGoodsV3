package com.hg.hollowgoods.UI.Base;

import android.app.Activity;
import android.content.Intent;

public interface IBaseActivity extends IBaseFragment {

    /**
     * 添加当前Activity全局退出队列
     *
     * @return Activity
     */
    Activity addToExitGroup();

    /**
     * 初始化意图传递的数据
     */
    @Deprecated
    default void initIntentData(Intent intent) {

    }

}
