package com.hg.hollowgoods.Task;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;
import com.hg.hollowgoods.Util.XUtils.XUtils;

/**
 * 初始化XUtils任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskInitXUtils extends Task {

    @Override
    public void run() {
        XUtils.init(ApplicationBuilder.create());
        ApplicationBuilder.create().initAppDataAfterDB();
    }

}
