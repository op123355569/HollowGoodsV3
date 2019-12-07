package com.hg.hollowgoods.Task;

import android.os.Handler;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;
import com.hg.hollowgoods.Util.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载Office文件预览功能引擎任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskReadOfficeFile extends Task {

    @Override
    public List<Class<? extends Task>> dependsOn() {

        List<Class<? extends Task>> tasks = new ArrayList<>();
        tasks.add(TaskInitXUtils.class);

        return tasks;
    }

    /**
     * X5内核初始化次数
     */
    private int X5InitTimes = 0;

    @Override
    public void run() {
        if (HGSystemConfig.IS_NEED_READ_OFFICE_FILE) {
            initFileView();
        }
    }

    /**
     * 初始化文件查看控件
     */
    private void initFileView() {
        QbSdk.initX5Environment(ApplicationBuilder.create(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtils.Log("initFileView onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean isSuccess) {

                X5InitTimes++;

                LogUtils.Log("加载X5内核是否成功:", isSuccess, "加载次数:", X5InitTimes);

                if (!isSuccess && X5InitTimes < 10) {
                    new Handler().postDelayed(() -> initFileView(), 500);
                }
            }
        });
    }

}
