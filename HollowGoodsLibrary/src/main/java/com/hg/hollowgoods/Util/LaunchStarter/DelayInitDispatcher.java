package com.hg.hollowgoods.Util.LaunchStarter;

import android.os.Looper;
import android.os.MessageQueue;

import com.hg.hollowgoods.Util.LaunchStarter.Task.DispatchRunnable;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;

import java.util.LinkedList;
import java.util.Queue;

public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = () -> {

        if (mDelayTasks.size() > 0) {
            Task task = mDelayTasks.poll();
            new DispatchRunnable(task).run();
        }

        return !mDelayTasks.isEmpty();
    };

    public DelayInitDispatcher addTask(Task task) {
        mDelayTasks.add(task);
        return this;
    }

    public void start() {
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
