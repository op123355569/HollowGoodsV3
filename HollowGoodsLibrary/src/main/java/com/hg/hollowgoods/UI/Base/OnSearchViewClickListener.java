package com.hg.hollowgoods.UI.Base;

/**
 * Created by Hollow Goods on 2018-12-19.
 */
public interface OnSearchViewClickListener {

    /**
     * 点击了历史记录列表或者按了回车
     *
     * @param searchKey
     */
    void onSearched(String searchKey);

    /**
     * 搜索框输入内容有变化
     *
     * @param searchKey
     */
    void onSearchKeyChanging(String searchKey);

    /**
     * 搜索框菜单点击
     *
     * @param id
     */
    void onSearchMenuItemClick(int id);

}
