package com.hg.hollowgoods.UI.Base;

import android.view.View;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2018年12月19日
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
