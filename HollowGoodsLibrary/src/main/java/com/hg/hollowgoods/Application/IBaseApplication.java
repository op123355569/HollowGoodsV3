package com.hg.hollowgoods.Application;

import android.app.Application;

/**
 * 基Application接口
 * Created by HG on 2018-03-22.
 */
public interface IBaseApplication {

    Application initAppContext();

    void initAppDataBeforeDB();

    void initAppDataAfterDB();

    default boolean checkServerTime() {
        return false;
    }

}
