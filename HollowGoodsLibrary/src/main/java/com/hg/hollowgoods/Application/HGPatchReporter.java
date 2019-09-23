package com.hg.hollowgoods.Application;

import android.content.Context;
import android.util.Log;

import com.tencent.tinker.lib.reporter.DefaultPatchReporter;

import java.io.File;

/**
 * 热插件报告
 * Created by Hollow Goods on 2019-06-20.
 */
public class HGPatchReporter extends DefaultPatchReporter {

    public HGPatchReporter(Context context) {
        super(context);
    }

    @Override
    public void onPatchResult(File patchFile, boolean success, long cost) {
        super.onPatchResult(patchFile, success, cost);
        if (success) {
            Log.e("HGPatchReporter", "补丁修复成功!");
        }
    }
}
