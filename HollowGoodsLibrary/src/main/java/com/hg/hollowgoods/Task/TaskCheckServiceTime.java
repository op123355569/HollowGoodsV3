package com.hg.hollowgoods.Task;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Service.Time.TimeService;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * 校对服务器时间任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskCheckServiceTime extends Task {

    @Override
    public List<Class<? extends Task>> dependsOn() {

        List<Class<? extends Task>> tasks = new ArrayList<>();
        tasks.add(TaskInitXUtils.class);

        return tasks;
    }

    @Override
    public void run() {
        if (HGSystemConfig.IS_NEED_CHECK_SERVER_TIME) {
            TimeService.start(ApplicationBuilder.create());
        }
    }

}
