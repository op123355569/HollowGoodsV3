package com.hg.hollowgoods.UI.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

public interface IBaseFragment {

    /**
     * 绑定布局界面
     *
     * @return int
     */
    @LayoutRes
    int bindLayout();

    /**
     * 初始化界面
     *
     * @param view               view
     * @param savedInstanceState savedInstanceState
     * @return 如需绑定EventBus则返回该类，否则返回null
     */
    @Nullable
    Object initView(View view, Bundle savedInstanceState);

    default void initViewDelay() {
        
    }

    /**
     * 设置监听
     */
    void setListener();

    /**
     * 初始化意图传递的数据
     */
    @Deprecated
    default void initArgumentsData(Bundle bundle) {

    }

    /**
     * 初始化传递的数据
     */
    default void initParamData() {

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

}
