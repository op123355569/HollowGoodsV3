package com.hg.hollowgoods.UI.Base;

import android.view.View;

/**
 * Created by Hollow Goods on 2018-12-19.
 */
public interface OnCommonTitleClickListener {

    /**
     * 左标题点击事件
     */
    void onLeftTitleClick(View view);

    /**
     * 中标题点击事件
     */
    void onCenterTitleClick(View view);

    /**
     * 右标题点击事件
     */
    void onRightTitleClick(View view, int id);

    /**
     * 无数据控件点击事件
     **/
    void onNoDataViewClick(View v);

}
