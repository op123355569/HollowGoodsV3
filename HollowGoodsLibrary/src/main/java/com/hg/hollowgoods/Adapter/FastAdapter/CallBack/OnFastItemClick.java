package com.hg.hollowgoods.Adapter.FastAdapter.CallBack;

import android.view.View;

/**
 * Created by Hollow Goods 2018-06-13.
 */
public interface OnFastItemClick extends OnFastBaseClick {

    /**
     * 操作按钮点击事件
     *
     * @param view
     * @param position
     * @param sortNumber
     */
    void onOperateClick(View view, int position, int sortNumber);

    /**
     * 文件预览控件点击事件
     *
     * @param view
     * @param position
     * @param sortNumber
     */
    void onFilePreClick(View view, int position, int sortNumber);

    /**
     * 数字选择器点击事件
     *
     * @param view
     * @param position
     * @param sortNumber
     * @param num
     */
    void onNumberPickerClick(View view, int position, int sortNumber, Object num);

}
