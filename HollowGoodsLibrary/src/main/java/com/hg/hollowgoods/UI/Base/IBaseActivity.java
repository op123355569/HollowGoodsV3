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

    /**
     * 是否开启改界面的侧滑返回
     * 默认 true
     */
    default boolean isOpenSlideBack() {
        return true;
    }

    /**
     * 是否包含滑动控件
     * 默认 false
     */
    default boolean haveScroll() {
        return false;
    }

    /**
     * 触发了侧滑返回
     */
    void onSlideBackWork();

}
