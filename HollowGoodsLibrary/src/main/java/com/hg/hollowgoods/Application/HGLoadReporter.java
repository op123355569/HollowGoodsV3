package com.hg.hollowgoods.Application;

import android.content.Context;

import com.tencent.tinker.lib.reporter.DefaultLoadReporter;

/**
 * 热插件加载报告
 * Created by Hollow Goods on 2019-06-20.
 */
public class HGLoadReporter extends DefaultLoadReporter {

    public HGLoadReporter(Context context) {
        super(context);
    }

}
