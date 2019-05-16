package com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack;

/**
 * 项目点击事件监听
 * Created by Hollow Goods on 2019-05-07.
 */
public interface OnHGFastItemClickListener {

    /**
     * 全部范围点击事件
     *
     * @param clickId itemId
     */
    void onItemClick(int clickId);

    /**
     * 左侧图标点击事件
     *
     * @param clickId itemId
     */
    void onRightIconClick(int clickId);

    /**
     * 文件预览点击事件
     *
     * @param clickId itemId
     */
    void onFilePreClick(int clickId);

}
