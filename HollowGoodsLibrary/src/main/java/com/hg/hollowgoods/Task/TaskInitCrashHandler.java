package com.hg.hollowgoods.Task;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Exception.CrashHandler;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;

/**
 * 初始化异常捕捉类任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskInitCrashHandler extends Task {

    @Override
    public void run() {
        if (!HGSystemConfig.IS_DEBUG_MODEL) {
            CrashHandler.getInstance().init(ApplicationBuilder.create());
        }
    }

}
