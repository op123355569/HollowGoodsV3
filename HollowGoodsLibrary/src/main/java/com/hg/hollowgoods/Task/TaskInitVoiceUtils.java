package com.hg.hollowgoods.Task;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;
import com.hg.hollowgoods.voice.VoiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化语音识别工具类任务——改造验证通过
 * <p>
 * Created by Hollow Goods on 2019-11-29.
 */
public class TaskInitVoiceUtils extends Task {

    @Override
    public List<Class<? extends Task>> dependsOn() {

        List<Class<? extends Task>> tasks = new ArrayList<>();
        tasks.add(TaskInitXUtils.class);

        return tasks;
    }

    @Override
    public void run() {
        VoiceUtils.init(ApplicationBuilder.create());
    }

}
