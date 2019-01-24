package com.hg.hollowgoods.UI.Base;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2018年12月19日
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
