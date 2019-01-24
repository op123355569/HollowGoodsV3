package com.hg.hollowgoods.UI.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public interface IBaseFragment {

    /**
     * 绑定布局界面
     *
     * @return
     */
    int bindLayout();

    /**
     * 初始化界面
     *
     * @param view
     * @param savedInstanceState
     * @return 如需绑定EventBus则返回该类，否则返回null
     */
    @Nullable
    Object initView(View view, Bundle savedInstanceState);

    /**
     * 设置监听
     */
    void setListener();

}
