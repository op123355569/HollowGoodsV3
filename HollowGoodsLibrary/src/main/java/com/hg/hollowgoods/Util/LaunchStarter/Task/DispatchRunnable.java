package com.hg.hollowgoods.Util.LaunchStarter.Task;

import android.os.Looper;
import android.os.Process;
import android.support.v4.os.TraceCompat;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.LaunchStarter.Stat.TaskStat;
import com.hg.hollowgoods.Util.LaunchStarter.TaskDispatcher;
import com.hg.hollowgoods.Util.LogUtils;

/**
 * 任务真正执行的地方
 */

public class DispatchRunnable implements Runnable {

    private Task mTask;
    private TaskDispatcher mTaskDispatcher;

    public DispatchRunnable(Task task) {
        this.mTask = task;
    }

    public DispatchRunnable(Task task, TaskDispatcher dispatcher) {
        this.mTask = task;
        this.mTaskDispatcher = dispatcher;
    }

    @Override
    public void run() {

        TraceCompat.beginSection(mTask.getClass().getSimpleName());
        LogUtils.Log(mTask.getClass().getSimpleName(), " begin run" + "  Situation  ", TaskStat.getCurrentSituation());

        Process.setThreadPriority(mTask.priority());

        long startTime = System.currentTimeMillis();

        mTask.setWaiting(true);
        mTask.waitToSatisfy();

        long waitTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 执行Task
        mTask.setRunning(true);
        mTask.run();

        // 执行Task的尾部任务
        Runnable tailRunnable = mTask.getTailRunnable();
        if (tailRunnable != null) {
            tailRunnable.run();
        }

        if (!mTask.needCall() || !mTask.runOnMainThread()) {
            printTaskLog(startTime, waitTime);

            TaskStat.markTaskDone();
            mTask.setFinished(true);
            if (mTaskDispatcher != null) {
                mTaskDispatcher.satisfyChildren(mTask);
                mTaskDispatcher.markTaskDone(mTask);
            }
            LogUtils.Log(mTask.getClass().getSimpleName(), " finish");
        }

        TraceCompat.endSection();
    }

    /**
     * 打印出来Task执行的日志
     *
     * @param startTime long
     * @param waitTime  long
     */
    private void printTaskLog(long startTime, long waitTime) {

        long runTime = System.currentTimeMillis() - startTime;

        if (HGSystemConfig.IS_DEBUG_MODEL) {
            LogUtils.Log(
                    mTask.getClass().getSimpleName() + "  wait " + waitTime + "    run ",
                    runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper()),
                    "  needWait " + (mTask.needWait() || (Looper.getMainLooper() == Looper.myLooper())),
                    "  ThreadId " + Thread.currentThread().getId(),
                    "  ThreadName " + Thread.currentThread().getName(),
                    "  Situation  " + TaskStat.getCurrentSituation()
            );
        }
    }

}
