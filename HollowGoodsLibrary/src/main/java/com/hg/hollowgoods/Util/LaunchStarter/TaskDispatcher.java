package com.hg.hollowgoods.Util.LaunchStarter;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.UiThread;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Util.LaunchStarter.Sort.TaskSortUtil;
import com.hg.hollowgoods.Util.LaunchStarter.Stat.TaskStat;
import com.hg.hollowgoods.Util.LaunchStarter.Task.DispatchRunnable;
import com.hg.hollowgoods.Util.LaunchStarter.Task.Task;
import com.hg.hollowgoods.Util.LaunchStarter.Utils.Utils;
import com.hg.hollowgoods.Util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启动器调用类
 */

public class TaskDispatcher {

    private long mStartTime;
    private static final int WAIT_TIME = 10000;
    private static boolean sIsMainProcess;
    private List<Future> mFutures = new ArrayList<>();
    private static volatile boolean sHasInit;
    private List<Task> mAllTasks = new ArrayList<>();
    private List<Class<? extends Task>> mClsAllTasks = new ArrayList<>();
    private volatile List<Task> mMainThreadTasks = new ArrayList<>();
    private CountDownLatch mCountDownLatch;
    private AtomicInteger mNeedWaitCount = new AtomicInteger();//保存需要Wait的Task的数量
    private List<Task> mNeedWaitTasks = new ArrayList<>();//调用了await的时候还没结束的且需要等待的Task
    private volatile List<Class<? extends Task>> mFinishedTasks = new ArrayList<>(100);//已经结束了的Task
    private HashMap<Class<? extends Task>, ArrayList<Task>> mDependedHashMap = new HashMap<>();
    private AtomicInteger mAnalyseCount = new AtomicInteger();//启动器分析的次数，统计下分析的耗时；

    private TaskDispatcher() {
    }

    public static void init(Context context) {
        if (context != null) {
            sHasInit = true;
            sIsMainProcess = Utils.isMainProcess(ApplicationBuilder.create());
        }
    }

    /**
     * 注意：每次获取的都是新对象
     *
     * @return TaskDispatcher
     */
    public static TaskDispatcher createInstance() {
        if (!sHasInit) {
            throw new RuntimeException("must call TaskDispatcher.init first");
        }
        return new TaskDispatcher();
    }

    public TaskDispatcher addTask(Task task) {

        if (task != null) {
            collectDepends(task);
            mAllTasks.add(task);
            mClsAllTasks.add(task.getClass());

            // 非主线程且需要wait的，主线程不需要CountDownLatch也是同步的
            if (ifNeedWait(task)) {
                mNeedWaitTasks.add(task);
                mNeedWaitCount.getAndIncrement();
            }
        }

        return this;
    }

    private void collectDepends(Task task) {

        if (task.dependsOn() != null && task.dependsOn().size() > 0) {
            for (Class<? extends Task> cls : task.dependsOn()) {
                if (mDependedHashMap.get(cls) == null) {
                    mDependedHashMap.put(cls, new ArrayList<Task>());
                }

                mDependedHashMap.get(cls).add(task);

                if (mFinishedTasks.contains(cls)) {
                    task.satisfy();
                }
            }
        }
    }

    private boolean ifNeedWait(Task task) {
        return !task.runOnMainThread() && task.needWait();
    }

    @UiThread
    public void start() {

        mStartTime = System.currentTimeMillis();

        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must be called from UiThread");
        }

        if (mAllTasks.size() > 0) {
            mAnalyseCount.getAndIncrement();
            printDependedMsg();
            mAllTasks = TaskSortUtil.getSortResult(mAllTasks, mClsAllTasks);
            mCountDownLatch = new CountDownLatch(mNeedWaitCount.get());

            sendAndExecuteAsyncTasks();

            LogUtils.Log("task analyse cost ", (System.currentTimeMillis() - mStartTime), "  begin main ");
            executeTaskMain();
        }

        LogUtils.Log("task analyse cost startTime cost ", (System.currentTimeMillis() - mStartTime));
    }

    public void cancel() {
        for (Future future : mFutures) {
            future.cancel(true);
        }
    }

    private void executeTaskMain() {

        mStartTime = System.currentTimeMillis();

        for (Task task : mMainThreadTasks) {
            long time = System.currentTimeMillis();
            new DispatchRunnable(task, this).run();
            LogUtils.Log("real main ", task.getClass().getSimpleName(), " cost   ", (System.currentTimeMillis() - time));
        }

        LogUtils.Log("main task cost ", (System.currentTimeMillis() - mStartTime));
    }

    private void sendAndExecuteAsyncTasks() {
        for (Task task : mAllTasks) {
            if (task.onlyInMainProcess() && !sIsMainProcess) {
                markTaskDone(task);
            } else {
                sendTaskReal(task);
            }
            task.setSend(true);
        }
    }

    /**
     * 查看被依赖的信息
     */
    private void printDependedMsg() {

        LogUtils.Log("needWait size : ", (mNeedWaitCount.get()));

        if (HGSystemConfig.IS_DEBUG_MODEL) {
            for (Class<? extends Task> cls : mDependedHashMap.keySet()) {
                LogUtils.Log("cls", cls.getSimpleName(), +mDependedHashMap.get(cls).size());
                for (Task task : mDependedHashMap.get(cls)) {
                    LogUtils.Log("cls", task.getClass().getSimpleName());
                }
            }
        }
    }

    /**
     * 通知Children一个前置任务已完成
     *
     * @param launchTask Task
     */
    public void satisfyChildren(Task launchTask) {

        ArrayList<Task> arrayList = mDependedHashMap.get(launchTask.getClass());

        if (arrayList != null && arrayList.size() > 0) {
            for (Task task : arrayList) {
                task.satisfy();
            }
        }
    }

    public void markTaskDone(Task task) {
        if (ifNeedWait(task)) {
            mFinishedTasks.add(task.getClass());
            mNeedWaitTasks.remove(task);
            mCountDownLatch.countDown();
            mNeedWaitCount.getAndDecrement();
        }
    }

    private void sendTaskReal(final Task task) {
        if (task.runOnMainThread()) {
            mMainThreadTasks.add(task);

            if (task.needCall()) {
                task.setTaskCallBack(() -> {
                    TaskStat.markTaskDone();
                    task.setFinished(true);
                    satisfyChildren(task);
                    markTaskDone(task);
                    LogUtils.Log(task.getClass().getSimpleName() + " finish");

                    LogUtils.Log("testLog", "call");
                });
            }
        } else {
            // 直接发，是否执行取决于具体线程池
            Future future = task.runOn().submit(new DispatchRunnable(task, this));
            mFutures.add(future);
        }
    }

    public void executeTask(Task task) {

        if (ifNeedWait(task)) {
            mNeedWaitCount.getAndIncrement();
        }

        task.runOn().execute(new DispatchRunnable(task, this));
    }

    @UiThread
    public void await() {
        try {
            if (HGSystemConfig.IS_DEBUG_MODEL) {
                LogUtils.Log("still has ", mNeedWaitCount.get());

                for (Task task : mNeedWaitTasks) {
                    LogUtils.Log("needWait: ", task.getClass().getSimpleName());
                }
            }

            if (mNeedWaitCount.get() > 0) {
                mCountDownLatch.await(WAIT_TIME, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException ignored) {

        }
    }

    public static boolean isMainProcess() {
        return sIsMainProcess;
    }

}
