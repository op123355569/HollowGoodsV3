package com.hg.hollowgoods.UI.Base;

/**
 * 列表数据助手
 * Created by Hollow Goods on 2019-07-31.
 */
public interface ListDataHelper {

    /**
     * 下拉刷新
     */
    void doRefresh();

    /**
     * 上拉加载
     */
    default void doLoadMore() {

    }

    /**
     * 搜索
     */
    default void doSearch(String searchKey) {

    }

}
