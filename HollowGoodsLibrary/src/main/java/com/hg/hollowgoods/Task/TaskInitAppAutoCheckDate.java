package com.hg.hollowgoods.Task;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Util.CacheUtils;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;

/**
 * 初始化APP自动检查更新日期任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskInitAppAutoCheckDate extends Task {

    @Override
    public void run() {
        ApplicationBuilder.create().setAutoCheckUpdateAppDate(CacheUtils.create().load("AppAutoCheckDate", String.class));
    }

}
