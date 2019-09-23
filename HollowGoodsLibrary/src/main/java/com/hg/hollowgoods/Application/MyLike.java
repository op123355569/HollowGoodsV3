package com.hg.hollowgoods.Application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tencent.tinker.entry.ApplicationLike;

/**
 * Created by Hollow Goods on 2019-06-18.
 */
public class MyLike extends ApplicationLike {

    public MyLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        TinkerManager.installedTinker(this);
    }
}
