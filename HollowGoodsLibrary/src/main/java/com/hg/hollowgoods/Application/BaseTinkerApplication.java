package com.hg.hollowgoods.Application;

/**
 * 基 Tinker Application
 * 使用Tinker时继承
 * Created by HG on 2019-09-20.
 */
public abstract class BaseTinkerApplication /*extends TinkerApplication implements IBaseApplication*/ {

//    private static Application instance = null;
//
//    public BaseTinkerApplication() {
//        super(ShareConstants.TINKER_ENABLE_ALL, MyLike.class.getName(), TinkerLoader.class.getName(), false);
//    }
//
//    public static <T extends Application> T create() {
//        if (instance == null) {
//            instance = new ExampleApplication();
//        }
//        return (T) instance;
//    }
//
//    /**
//     * activity堆
//     */
//    private ArrayList<Activity> activityAllList = new ArrayList<>();
//    /**
//     * 最近一次自动检查APP更新的时间
//     */
//    private String autoCheckUpdateAppDate = "";
//    /**
//     * 当前时间戳
//     */
//    private long nowTime = 0L;
//    /**
//     * 计时器
//     */
//    private TimeThread timeThread;
//    /**
//     * 计时器标识
//     */
//    private int countFlag = 0;
//    /**
//     * 服务器校验时间间隔（单位：秒）
//     */
//    private int testSystemTime = 300;
//    /**
//     * X5内核初始化次数
//     */
//    private int X5InitTimes = 0;
//
//    @Override
//    public void addActivity(Activity activity) {
//        if (!activityAllList.contains(activity)) {
//            activityAllList.add(activity);
//        }
//    }
//
//    @Override
//    public void exit(Activity activity) {
//        activityAllList.remove(activity);
//    }
//
//    @Override
//    public void exitWithFinish(Activity activity) {
//        activityAllList.remove(activity);
//        activity.finish();
//    }
//
//    @Override
//    public void exitAll() {
//        for (Activity activity : activityAllList) {
//            activity.finish();
//        }
//        activityAllList.clear();
//    }
//
//    @Override
//    public ArrayList<Activity> getAllActivity() {
//        return activityAllList;
//    }
//
//    @Override
//    public void onCreate() {
//
//        instance = initAppContext();
//        ApplicationBuilder.setApplication(instance);
//        initCrashHandler();
//        initAppDataBeforeDB();
//        initXUtils();
//        initAppDataAfterDB();
//        initNetworkWatcher();
//        if (HGSystemConfig.IS_NEED_CHECK_SERVER_TIME) {
//            TimeService.start(create());
//        }
//        if (HGSystemConfig.IS_NEED_READ_OFFICE_FILE) {
//            initFileView();
//        }
//        initAppAutoCheckDate();
//        VoiceUtils.init(create());
//
//        super.onCreate();
//    }
//
//    /**
//     * 初始化APP自动检查更新日期
//     */
//    private void initAppAutoCheckDate() {
//        BaseTinkerApplication baseApplication = create();
//        baseApplication.setAutoCheckUpdateAppDate(CacheUtils.create().load("AppAutoCheckDate", String.class));
//    }
//
//    /**
//     * 初始化网络监察者
//     */
//    private void initNetworkWatcher() {
//
//        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (manager != null) {
//            manager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
//                @Override
//                public void onLost(Network network) {
//                    super.onLost(network);
//                    // 网络不可用
//                    LogUtils.Log("断网");
//                    sendMessage(HGEventActionCode.NETWORK_STATUS_BREAK);
//                    networkBreak();
//                }
//
//                @Override
//                public void onAvailable(Network network) {
//                    super.onAvailable(network);
//                    // 网络可用
//                    LogUtils.Log("联网");
//                    sendMessage(HGEventActionCode.NETWORK_STATUS_LINK);
//                    networkLink();
//                }
//            });
//        }
//    }
//
//    /**
//     * 设置断网界面布局
//     */
//    private void networkBreak() {
//
//        BaseActivity baseActivity = getTopActivity();
//
//        if (baseActivity != null) {
//            baseActivity.runOnUiThread(() -> baseActivity.baseUI.setStatus(HGStatusLayout.Status.NetworkBreak));
//        }
//    }
//
//    /**
//     * 设置联网界面布局
//     */
//    private void networkLink() {
//
//        BaseActivity baseActivity = getTopActivity();
//
//        if (baseActivity != null) {
//            if (baseActivity.baseUI.getStatusLayout() != null) {
//                baseActivity.runOnUiThread(() -> baseActivity.baseUI.getStatusLayout().networkLink());
//            }
//        }
//    }
//
//    @Override
//    public BaseActivity getTopActivity() {
//
//        BaseTinkerApplication application = BaseTinkerApplication.create();
//        List<Activity> activities = application.getAllActivity();
//
//        if (activities != null && activities.size() > 0) {
//            Activity activity = activities.get(activities.size() - 1);
//
//            if (activity instanceof BaseActivity) {
//                return (BaseActivity) activity;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 发送网络状态变化的Event消息
//     *
//     * @param code code
//     */
//    private void sendMessage(int code) {
//        Event event = new Event(code);
//        EventBus.getDefault().post(event);
//    }
//
//    /**
//     * 初始化文件查看控件
//     */
//    private void initFileView() {
//        QbSdk.initX5Environment(create(), new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//                LogUtils.Log("initFileView onCoreInitFinished");
//            }
//
//            @Override
//            public void onViewInitFinished(boolean isSuccess) {
//
//                X5InitTimes++;
//
//                LogUtils.Log("加载X5内核是否成功:", isSuccess, "加载次数:", X5InitTimes);
//
//                if (!isSuccess && X5InitTimes < 10) {
//                    new Handler().postDelayed(() -> {
//                        BaseTinkerApplication baseApplication = create();
//                        baseApplication.initFileView();
//                    }, 500);
//                }
//            }
//        });
//    }
//
//    /**
//     * 初始化XUtils
//     */
//    private void initXUtils() {
//        XUtils.init(create());
//    }
//
//    /**
//     * 初始化异常捕捉类
//     */
//    private void initCrashHandler() {
//        if (!HGSystemConfig.IS_DEBUG_MODEL) {
//            CrashHandler.getInstance().init(create());
//        }
//    }
//
//    @Override
//    public void setCrashHandlerUsername(String username) {
//        if (!HGSystemConfig.IS_DEBUG_MODEL) {
//            CrashHandler.getInstance().setUsername(username);
//        }
//    }
//
//    /**
//     * 防止6K方法爆炸
//     *
//     * @param base 上下文
//     */
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(create());
//    }
//
//    @Override
//    public String getAutoCheckUpdateAppDate() {
//        return autoCheckUpdateAppDate;
//    }
//
//    @Override
//    public void setAutoCheckUpdateAppDate(String autoCheckUpdateAppDate) {
//        this.autoCheckUpdateAppDate = autoCheckUpdateAppDate;
//        CacheUtils.create().save("AppAutoCheckDate", this.autoCheckUpdateAppDate);
//    }
//
//    @Override
//    public long getNowTime() {
//
//        if (nowTime == 0L) {
//            return System.currentTimeMillis();
//        }
//
//        return nowTime;
//    }
//
//    @Override
//    public void setNowTime(long nowTime) {
//        this.nowTime = nowTime;
//    }
//
//    @Override
//    public TimeThread getTimeThread() {
//        return timeThread;
//    }
//
//    @Override
//    public void setTimeThread(TimeThread timeThread) {
//        this.timeThread = timeThread;
//    }
//
//    @Override
//    public int getCountFlag() {
//        return countFlag;
//    }
//
//    @Override
//    public void setCountFlag(int countFlag) {
//        this.countFlag = countFlag;
//    }
//
//    @Override
//    public int getTestSystemTime() {
//        return testSystemTime;
//    }
//
//    @Override
//    public void setTestSystemTime(int testSystemTime) {
//        this.testSystemTime = testSystemTime;
//    }

}
