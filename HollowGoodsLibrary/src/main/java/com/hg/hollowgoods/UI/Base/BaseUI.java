package com.hg.hollowgoods.UI.Base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnFloatingSearchMenuItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnToolbarMenuItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.BaseDialog;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyConfig;
import com.hg.hollowgoods.UI.Fragment.Proxy.ProxyHelper;
import com.hg.hollowgoods.Util.CircularAnimUtils;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.ReflectUtils;
import com.hg.hollowgoods.Util.SearchHistory.SearchHistoryUtils;
import com.hg.hollowgoods.Util.SearchHistory.SearchKeys;
import com.hg.hollowgoods.Util.SystemBarTintUtils;
import com.hg.hollowgoods.Widget.CommonTitle.BaseCommonTitle;
import com.hg.hollowgoods.Widget.FloatingSearchView.FloatingSearchView;
import com.hg.hollowgoods.Widget.FloatingSearchView.suggestions.model.SearchSuggestion;
import com.hg.hollowgoods.Widget.FloatingSearchView.util.Util;
import com.hg.hollowgoods.Widget.HGStatusLayout;
import com.hg.hollowgoods.voice.VoiceListener;
import com.hg.hollowgoods.voice.VoiceUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * 基础UI
 * <p>
 * Created by Hollow Goods on 2018-12-18.
 */
public class BaseUI {

    /**** 无数据 ****/
    @Deprecated
    public final int DATA_MODE_NO_DATA = -5000;
    /**** 有数据 ****/
    @Deprecated
    public final int DATA_MODE_HAS_DATA = -5001;
    /**** 在中间加载数据 ****/
    @Deprecated
    public final int DATA_MODE_LOAD_DATA_CENTER = -5002;

    /**** 权限请求代码——文件操作 ****/
    public final int PERMISSION_CODE_IO = 10001;

    /**** 上下文 ****/
    private Activity context;
    /**** 注册EventBus的类 ****/
    private Object registerEventBusObj = null;
    /**** 搜索提示 ****/
    private Object initUI;
    private OnPermissionsCheckedListener iPermissionsListener = null;
    private OnCommonTitleClickListener iCommonTitleClickListener = null;
    private OnSearchViewClickListener iSearchViewClickListener = null;
    private String searchHint = "";
    private String searchKey = "";
    private ArrayList<SearchKeys> keys = new ArrayList<>();
    private int historyCodeTag = -9999;
    private boolean mIsNeedHistory;
    private boolean mIsAutoClearSearch = true;
    private int menuRes = -1;
    private long exitTime = 0l;
    private Map<String, Object> params = new HashMap<>();

    /**** 基础对话框 ****/
    public BaseDialog baseDialog;
    /**** 是否为Activity ****/
    public boolean isActivity;
    public boolean hasSharedElement = false;

    /**** **************** 控件 **************** ****/

    /**** 当前加载的视图界面 ****/
    public View rootView;
    private BaseCommonTitle commonTitle;
    private FrameLayout noDataView;
    private View contentView;
    private View loadDataViewCenter;
    private View loadDataViewBottom;
    private SmoothProgressBar bottomProgressBar;
    private FloatingSearchView floatingSearchView;
    private HGStatusLayout statusLayout;

    private VoiceUtils voiceUtils;

    /**** 暴露的方法 ****/

    void initUI(Object initUI) {
        initUI(initUI, null);
    }

    void initUI(Object initUI, View rootView) {

        this.initUI = initUI;

        isActivity = this.initUI instanceof Activity;

        // 初始化上下文
        if (isActivity) {
            context = (Activity) this.initUI;
        } else {
            context = ((Fragment) this.initUI).getActivity();
        }

        // 加入全局退出队列
        if (getBaseContext() != null) {
            IBaseApplication baseApplication = ApplicationBuilder.create();
            baseApplication.addActivity(getBaseContext());
        }

        // 加载Root Layout
        if (rootView != null) {
            this.rootView = rootView;

            if (isActivity) {
                getBaseContext().setContentView(this.rootView);
                x.view().inject(getBaseContext());
            } else {
                x.view().inject(this.initUI, this.rootView);
            }

            // 创建基础对话框
            baseDialog = new BaseDialog(getBaseContext());
            // 初始化搜索提示
            searchHint = getBaseContext().getString(R.string.search);
            // 获取是否有共享控件
            hasSharedElement = getBaseContext().getIntent().getBooleanExtra("hasSharedElement", false);

            // 初始化公共标题
            initCommonTitle();

            // 找到主体内容控件
            contentView = findViewById(R.id.view_content);
            // 找到无数据控件
            noDataView = findViewById(R.id.view_common_no_data);
            loadDataViewCenter = findViewById(R.id.view_common_load_data_center);
            loadDataViewBottom = findViewById(R.id.view_common_load_data_bottom);
            bottomProgressBar = loadDataViewBottom == null ? null : (SmoothProgressBar) ((LinearLayout) loadDataViewBottom).getChildAt(0);
            floatingSearchView = findViewById(R.id.floating_search_view);
            statusLayout = findViewById(R.id.hgStatusLayout);

            if (noDataView != null) {
                noDataView.setOnClickListener(new OnViewClickListener(false) {
                    @Override
                    public void onViewClick(View view, int id) {
                        iCommonTitleClickListener.onNoDataViewClick(view);
                    }
                });
            }
        }

        initParams();
    }

    /**
     * 绑定EventBus
     *
     * @param eventBusObj eventBusObj
     */
    void bindEventBus(Object eventBusObj) {

        registerEventBusObj = eventBusObj;

        if (registerEventBusObj != null) {
            if (!EventBus.getDefault().isRegistered(registerEventBusObj)) {
                EventBus.getDefault().register(registerEventBusObj);
            }
        }
    }

    public void setOnCommonTitleClickListener(OnCommonTitleClickListener onCommonTitleClickListener) {
        this.iCommonTitleClickListener = onCommonTitleClickListener;
    }

    public void setOnSearchViewClickListener(OnSearchViewClickListener onSearchViewClickListener) {
        this.iSearchViewClickListener = onSearchViewClickListener;
    }

    public void setOnPermissionsListener(OnPermissionsCheckedListener onPermissionsListener) {
        this.iPermissionsListener = onPermissionsListener;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (commonTitle != null && menuRes != -1) {
            if (isActivity) {
                getBaseContext().getMenuInflater().inflate(menuRes, menu);
            } else {
                menu.clear();
                inflater.inflate(menuRes, menu);
            }

            setIconVisible(menu, true);
        }
    }

    public void onDestroy() {

        if (registerEventBusObj != null) {
            if (EventBus.getDefault().isRegistered(registerEventBusObj)) {
                EventBus.getDefault().unregister(registerEventBusObj);
            }
        }

        baseDialog.closeAllDialog();

        if (mIsNeedHistory) {
            if (historyCodeTag != -9999) {
                SearchHistoryUtils.saveKeys(initUI.getClass(), keys, historyCodeTag);
            } else {
                SearchHistoryUtils.saveKeys(initUI.getClass(), keys);
            }
        }

        if (getBaseContext() != null) {
            IBaseApplication baseApplication = ApplicationBuilder.create();
            baseApplication.exit(getBaseContext());
        }
    }

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (iPermissionsListener != null && grantResults != null && grantResults.length > 0) {
            boolean[] isAgree = new boolean[grantResults.length];
            boolean isAgreeAll = true;

            for (int i = 0; i < grantResults.length; i++) {
                // grantResults[i] == PackageManager.PERMISSION_GRANTED 同意开放权限
                isAgree[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (!isAgree[i]) {
                    isAgreeAll = false;
                }
            }

            iPermissionsListener.onPermissionsResult(isAgreeAll, requestCode, permissions, isAgree);
        }
    }

    /**
     * 请求文件操作权限
     *
     * @return boolean
     */
    public boolean requestIOPermission() {
        return requestPermission(PERMISSION_CODE_IO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
//                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        );
    }

    /**
     * 检查文件操作权限
     *
     * @return boolean
     */
    public boolean checkIOPermission() {
        return checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
//                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        ) == null;
    }

    /**
     * 请求权限
     *
     * @param requestCode requestCode
     * @param permission  permission
     * @return boolean
     */
    public boolean requestPermission(int requestCode, String... permission) {

        String[] result = checkPermission(permission);

        if (result != null) {
            // 在这里面向系统请求权限,如果没有在这里面处理,不会执行下面的方法了.
            // 这里就是向系统请求权限了,这里我还做了一个判断.
            // SDK是M(M = 23 android L)才做这个请求,否则就不做.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isActivity) {
                    getBaseContext().requestPermissions(result, requestCode);
                } else {
                    ((Fragment) this.initUI).requestPermissions(result, requestCode);
                }
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查权限
     *
     * @param permission permission
     * @return String[]
     */
    public String[] checkPermission(String... permission) {

        String[] result = null;
        ArrayList<String> temp = new ArrayList<>();

        if (permission != null && permission.length > 0) {
            for (String t : permission) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), t) != PackageManager.PERMISSION_GRANTED) {
                    temp.add(t);
                }
            }
        }

        if (temp.size() > 0) {
            result = new String[temp.size()];

            for (int i = 0; i < temp.size(); i++) {
                result[i] = temp.get(i);
            }
        }

        return result;
    }

    /**
     * 检查返回按钮
     *
     * @return boolean
     */
    public boolean checkBackPressed() {

        if (System.currentTimeMillis() - exitTime <= HGSystemConfig.PRESS_AGAIN_TO_EXIT_TIME) {
            return true;
        }

        exitTime = System.currentTimeMillis();
        t.showShortToast(R.string.press_again_to_exit);

        return false;
    }

    /**
     * 查找绑定控件
     *
     * @param id  id
     * @param <T> <T>
     * @return <T extends View> T
     */
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    public BaseActivity getBaseContext() {
        return (BaseActivity) context;
    }

    /**
     * 设置公共标题风格<p>
     * 背景颜色自动填充 CommonResource.TITLE_BAR_RESOURCE
     *
     * @param titleText 中间标题文字
     */
    public void setCommonTitleStyleAutoBackground(Object titleText) {
        setCommonTitleStyle(null, titleText, HGCommonResource.TITLE_BAR_RESOURCE, -1);
    }

    /**
     * 设置公共标题风格<p>
     * 背景颜色自动填充 CommonResource.TITLE_BAR_RESOURCE
     *
     * @param leftIcon  左侧图标
     * @param titleText 中间标题文字
     */
    public void setCommonTitleStyleAutoBackground(Object leftIcon, Object titleText) {
        setCommonTitleStyle(leftIcon, titleText, HGCommonResource.TITLE_BAR_RESOURCE, -1);
    }

    /**
     * 设置公共标题风格
     *
     * @param leftIcon   左侧图标
     * @param titleText  中间标题文字
     * @param background 背景颜色
     */
    public void setCommonTitleStyle(Object leftIcon, Object titleText, Object background) {
        setCommonTitleStyle(leftIcon, titleText, background, -1);
    }

    /**
     * 设置公共标题风格<p>
     * 背景颜色自动填充 CommonResource.TITLE_BAR_RESOURCE
     *
     * @param leftIcon  左侧图标
     * @param titleText 中间标题文字
     * @param menuRes   右侧菜单资源
     */
    public void setCommonTitleStyleAutoBackground(Object leftIcon, Object titleText, @MenuRes int menuRes) {
        setCommonTitleStyle(leftIcon, titleText, HGCommonResource.TITLE_BAR_RESOURCE, menuRes);
    }

    /**
     * 设置公共标题风格
     *
     * @param leftIcon   左侧图标
     * @param titleText  中间标题文字
     * @param background 背景颜色
     * @param menuRes    右侧菜单资源
     */
    public void setCommonTitleStyle(Object leftIcon, Object titleText, Object background, int menuRes) {

        setMenuRes(menuRes);
        setCommonTitleLeftIcon(leftIcon);
        setCommonTitleText(titleText);
        setCommonTitleBackground(background);
    }

    public void setMenuRes(int menuRes) {
        this.menuRes = commonTitle != null ? menuRes : -1;
        if (!isActivity) {
            ((BaseFragment) initUI).setHasOptionsMenu(menuRes != -1);
        }
    }

    public void addCommonTitleOtherView(View view) {
        if (commonTitle != null) {
            commonTitle.addOtherView(view);
        }
    }

    public void addCommonTitleOtherView(View view, Toolbar.LayoutParams layoutParams) {
        if (commonTitle != null) {
            commonTitle.addOtherView(view, layoutParams);
        }
    }

    /**
     * 设置公共标题左侧图标
     *
     * @param leftIcon leftIcon
     */
    public void setCommonTitleLeftIcon(Object leftIcon) {

        if (commonTitle != null) {
            if (leftIcon instanceof Integer) {
                commonTitle.setLeftIcon((Integer) leftIcon);
            } else if (leftIcon instanceof Drawable) {
                commonTitle.setLeftIcon((Drawable) leftIcon);
            }
        }
    }

    /**
     * 设置公共标题中间文字
     *
     * @param titleText titleText
     */
    public void setCommonTitleText(Object titleText) {

        if (commonTitle != null) {
            if (titleText instanceof Integer) {
                commonTitle.setCenterTitle((Integer) titleText);
            } else if (titleText instanceof String) {
                commonTitle.setCenterTitle((String) titleText);
            }
        }
    }

    /**
     * 设置公共标题中间文字大小
     *
     * @param size size
     */
    public void setCommonTitleTextSize(float size) {
        if (commonTitle != null) {
            commonTitle.setCenterTitleTextSize(size);
        }
    }

    /**
     * 设置公共标题中间文字颜色
     *
     * @param color color
     */
    public void setCommonTitleTextColor(Object color) {

        if (commonTitle != null) {
            if (color instanceof Integer) {
                commonTitle.setCenterTitleTextColorRes((Integer) color);
            } else if (color instanceof String) {
                commonTitle.setCenterTitleTextColor(Color.parseColor((String) color));
            }
        }
    }

    /**
     * 设置公共标题右侧文字
     *
     * @param titleText titleText
     */
    public void setCommonRightTitleText(Object titleText) {

        if (commonTitle != null && menuRes == -1) {
            if (titleText instanceof Integer) {
                commonTitle.setRightTitle((Integer) titleText);
            } else if (titleText instanceof String) {
                commonTitle.setRightTitle((String) titleText);
            }
        }
    }

    /**
     * 设置公共标题右侧文字大小
     *
     * @param size size
     */
    public void setCommonRightTitleTextSize(float size) {
        if (commonTitle != null && menuRes == -1) {
            commonTitle.setRightTitleTextSize(size);
        }
    }

    /**
     * 设置公共标题右侧文字颜色
     *
     * @param color color
     */
    public void setCommonRightTitleTextColor(Object color) {

        if (commonTitle != null && menuRes == -1) {
            if (color instanceof Integer) {
                commonTitle.setRightTitleTextColorRes((Integer) color);
            } else if (color instanceof String) {
                commonTitle.setRightTitleTextColor(Color.parseColor((String) color));
            }
        }
    }

    /**
     * 设置公共标题背景
     *
     * @param background background
     */
    public void setCommonTitleBackground(Object background) {

        if (commonTitle != null) {
            if (background instanceof Integer) {
                commonTitle.setTitleBackgroundResource((Integer) background);
                setActionBar(background);
            } else if (background instanceof Drawable) {
                commonTitle.setTitleBackground((Drawable) background);
                setActionBar(background);
            } else if (background instanceof String) {
                commonTitle.setTitleBackgroundColor(Color.parseColor((String) background));
                setActionBar(background);
            }
        }
    }

    /**
     * 设置右侧菜单溢出时的图标
     *
     * @param icon icon
     */
    public void setCommonTitleOverflowIcon(Object icon) {

        if (commonTitle != null) {
            if (icon instanceof Integer) {
                commonTitle.setOverflowIcon(getBaseContext().getResources().getDrawable((Integer) icon));
            } else if (icon instanceof Drawable) {
                commonTitle.setOverflowIcon((Drawable) icon);
            }
        }
    }

    /**
     * 获取公共标题体
     * CommonTitleLayout
     * 或
     * CommonTitleView
     *
     * @return View
     */
    public BaseCommonTitle getCommonTitleBody() {
        return commonTitle;
    }

    /**
     * 设置公共标题可见性
     *
     * @param visibility visibility
     */
    public void setCommonTitleViewVisibility(boolean visibility) {
        if (commonTitle != null) {
            commonTitle.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 隐藏公共标题右侧菜单
     */
    public void hideCommonTitleRightTitleMenu() {
        if (menuRes != -1 && commonTitle != null) {
            Toolbar toolbar = commonTitle.getToolbar();

            if (toolbar != null) {
                toolbar.getMenu().clear();
            }
        }
    }

    /**
     * 显示公共标题右侧菜单
     */
    public void showCommonTitleRightTitleMenu() {

        hideCommonTitleRightTitleMenu();

        if (menuRes != -1 && commonTitle != null) {
            Toolbar toolbar = commonTitle.getToolbar();

            if (toolbar != null) {
                getBaseContext().getMenuInflater().inflate(menuRes, toolbar.getMenu());
                setIconVisible(toolbar.getMenu(), true);
            }
        }
    }

    /**
     * 当使用menu需要让溢出菜单显示图标时使用
     *
     * @param menu    menu
     * @param visible visible
     */
    public void setIconVisible(Menu menu, boolean visible) {

        Field field;

        try {
            field = menu.getClass().getDeclaredField("mOptionalIconsVisible");
            field.setAccessible(true);
            field.set(menu, visible);
        } catch (Exception ignored) {

        }
    }

    /**
     * 改变通知栏颜色
     */
    public void setActionBar(Object style) {

        if (HGSystemConfig.IMMERSE_MODE) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 判断当前手机系统是否是4.4以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    setTranslucentStatus(true);
                }
                // 创建状态栏的管理实例
                SystemBarTintUtils tintManager = new SystemBarTintUtils(getBaseContext());
                // 激活状态栏设置
                tintManager.setStatusBarTintEnabled(true);

                if (style instanceof String) {
                    // 设置状态栏背景
                    tintManager.setStatusBarTintColor(Color.parseColor((String) style));
                } else if (style instanceof Integer) {
                    tintManager.setStatusBarTintResource((Integer) style);
                } else if (style instanceof Drawable) {
                    tintManager.setStatusBarTintDrawable((Drawable) style);
                }
            }

            if (isActivity) {
                rootView.setFitsSystemWindows(true);
            }
        }
    }

    /**
     * 改变通知栏颜色
     */
    public void setActionBarAllSDK(Object style) {

        if (HGSystemConfig.IMMERSE_MODE) {
            // 判断当前手机系统是否是4.4以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
            // 创建状态栏的管理实例
            SystemBarTintUtils tintManager = new SystemBarTintUtils(getBaseContext());
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);

            if (style instanceof String) {
                // 设置状态栏背景
                tintManager.setStatusBarTintColor(Color.parseColor((String) style));
            } else if (style instanceof Integer) {
                tintManager.setStatusBarTintResource((Integer) style);
            } else if (style instanceof Drawable) {
                tintManager.setStatusBarTintDrawable((Drawable) style);
            }

            if (isActivity) {
                rootView.setFitsSystemWindows(true);
            }
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getBaseContext().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置通知栏为透明
     *
     * @param isContentGoActionBar 内容是否要顶到通知栏
     */
    @Deprecated // 过时注解
    public void setTranslucentActionBar(boolean isContentGoActionBar) {

        Window window = getBaseContext().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        rootView.setFitsSystemWindows(!isContentGoActionBar);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBackgroundColor(Object color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseContext().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            int colorInt = ContextCompat.getColor(getBaseContext(), R.color.black_transparent);

            if (color instanceof String) {
                colorInt = Color.parseColor((String) color);
            } else if (color instanceof Integer) {
                colorInt = ContextCompat.getColor(getBaseContext(), (Integer) color);
            }

            getBaseContext().getWindow().setStatusBarColor(colorInt);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBackgroundColorWithoutFullscreen(Object color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseContext().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );

            int colorInt = ContextCompat.getColor(getBaseContext(), R.color.black_transparent);

            if (color instanceof String) {
                colorInt = Color.parseColor((String) color);
            } else if (color instanceof Integer) {
                colorInt = ContextCompat.getColor(getBaseContext(), (Integer) color);
            }

            getBaseContext().getWindow().setStatusBarColor(colorInt);
        }
    }

    /**
     * 隐藏状态栏
     */
    public void hideActionBar() {
        if (getBaseContext().getSupportActionBar() != null) {
            getBaseContext().getSupportActionBar().hide();
        }
    }

    /**
     * 设置数据模式
     *
     * @param dataMode 数据模式
     */
    @Deprecated
    public void setDataMode(int dataMode) {

        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }

        if (loadDataViewCenter != null) {
            loadDataViewCenter.setVisibility(View.GONE);
        }

        if (dataMode == DATA_MODE_NO_DATA) {
            if (noDataView != null) {
                noDataView.setVisibility(View.VISIBLE);
            }
        } else if (dataMode == DATA_MODE_HAS_DATA) {
            if (contentView != null) {
                contentView.setVisibility(View.VISIBLE);
            }
        } else if (dataMode == DATA_MODE_LOAD_DATA_CENTER) {
            if (loadDataViewCenter != null) {
                loadDataViewCenter.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 显示底部加载数据模式
     *
     * @param isShow isShow
     */
    public void setLoadDataBottomMode(boolean isShow) {

        if (loadDataViewBottom != null) {
            loadDataViewBottom.setVisibility(isShow ? View.VISIBLE : View.GONE);

            if (bottomProgressBar != null) {
                if (isShow) {
                    bottomProgressBar.progressiveStart();
                } else {
                    bottomProgressBar.progressiveStop();
                }
            }
        }
    }

    /**
     * 添加自定义无数据界面
     *
     * @param view view
     */
    @Deprecated
    public void addNoDataView(View view) {
        noDataView.addView(view);
    }

    /**
     * 根据View所在坐标和用户点击的坐标相对比，来判断是否触摸在原本所点击的View上
     *
     * @param v     v
     * @param event event
     * @return boolean
     */
    private boolean isTouchView(View v, MotionEvent event) {

        if (v != null) {
            int left = 0, top = 0, bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public void initSearchView(View dataView, boolean isNeedHistory, int... historyCode) {

        this.mIsNeedHistory = isNeedHistory;

        if (floatingSearchView != null) {
            if (VoiceUtils.isIncludeSDK()) {
                voiceUtils = new VoiceUtils(getBaseContext());

                voiceUtils.setVoiceListener(new VoiceListener() {
                    @Override
                    public void onStart() {
                        floatingSearchView.showProgress();
                    }

                    @Override
                    public void onEnd() {
                        floatingSearchView.hideProgress();
                    }

                    @Override
                    public void onResult(String text) {
                        if (!floatingSearchView.isSearchBarFocused()) {
                            floatingSearchView.setSearchFocused(true);
                        }
                        setSearchText(floatingSearchView.getQuery() + text);
                    }
                });
            }

            if (isNeedHistory) {
                if (historyCode == null || historyCode.length == 0) {
                    keys = SearchHistoryUtils.getKeys(initUI.getClass());
                } else {
                    historyCodeTag = historyCode[0];
                    keys = SearchHistoryUtils.getKeys(initUI.getClass(), historyCodeTag);
                }
            }

            floatingSearchView.setVisibility(View.VISIBLE);
            floatingSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {

                if (mIsNeedHistory) {
                    // 输入监听
                    // 输入框无内容则清空历史记录列表
                    // 否则进行匹配
                    if (!oldQuery.equals("") && newQuery.equals("")) {
                        floatingSearchView.clearSuggestions();
                    } else {
                        floatingSearchView.showProgress();

                        if (mIsNeedHistory) {
                            floatingSearchView.swapSuggestions(keys);
                        }

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getBaseContext().runOnUiThread(() -> floatingSearchView.hideProgress());
                            }
                        }, 500);
                    }
                }

                iSearchViewClickListener.onSearchKeyChanging(newQuery);
            });

            floatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {

                @Override
                public void onFocus() {

                    // 获取到焦点
                    floatingSearchView.setSearchBarTitle(TextUtils.isEmpty(searchKey) ? "" : searchKey);
                    floatingSearchView.setSearchHint("");
                    if (mIsNeedHistory) {
                        floatingSearchView.swapSuggestions(keys);
                    }
                }

                @Override
                public void onFocusCleared() {

                    // 焦点消失
                    floatingSearchView.setSearchBarTitle("");
                    floatingSearchView.setSearchHint(TextUtils.isEmpty(searchKey) ? searchHint : searchKey);
                }
            });

            floatingSearchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
                // 历史列表绑定数据
                SearchKeys searchKeys = (SearchKeys) item;

                int textColor = getBaseContext().getResources().getColor(R.color.search_history_text);
                String textLight = getBaseContext().getString(R.string.search_history_text_light);

                if (searchKeys.getIsHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getBaseContext().getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, textColor);
                    leftIcon.setAlpha(0.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }

                textView.setTextColor(textColor);
                String text = searchKeys.getBody()
                        .replaceFirst(floatingSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + floatingSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            });

            floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
                @Override
                public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                    // 点击了历史记录列表
                    SearchKeys searchKeys = (SearchKeys) searchSuggestion;
                    searchKey = searchKeys.getBody();
                    if (mIsNeedHistory) {
                        addSearchKey();
                    }
                    iSearchViewClickListener.onSearched(searchKey);
                    floatingSearchView.clearSearchFocus();
                }

                @Override
                public void onSearchAction(String query) {

                    searchKey = query;

                    if (!TextUtils.isEmpty(query)) {
                        // 按了回车
                        if (mIsNeedHistory) {
                            addSearchKey();
                        }
                    }

                    iSearchViewClickListener.onSearched(searchKey);
                }
            });

            floatingSearchView.setOnSuggestionsListHeightChanged(newHeight -> {

                // 历史记录列表长度变化
                // 结果列表位置也需变化
                if (dataView != null) {
                    dataView.setTranslationY(newHeight);
                }
            });

            floatingSearchView.setOnArrowActionClickListener(() -> {
                if (mIsAutoClearSearch) {
                    clearSearch();
                }
            });

            floatingSearchView.setOnMenuItemClickListener(new OnFloatingSearchMenuItemClickListener(getBaseContext(), false) {
                @Override
                public void onFloatingSearchMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_hg_voice) {
                        if (VoiceUtils.isIncludeSDK()) {
                            ProxyHelper.create(getBaseContext()).requestProxy(
                                    new ProxyConfig()
                                            .setPermissions(voiceUtils.permissions)
                                            .setRequestCode(12000)
                                            .setOnProxyRequestPermissionsResult((isAgreeAll, requestCode, permissions, isAgree) -> {
                                                if (isAgreeAll) {
                                                    voiceUtils.setParam(false);
                                                    voiceUtils.start();
                                                }
                                            })
                            );
                        }
                    } else {
                        iSearchViewClickListener.onSearchMenuItemClick(item.getItemId());
                    }
                }
            });
        }
    }

    public void setIsAutoClearSearch(boolean mIsAutoClearSearch) {
        this.mIsAutoClearSearch = mIsAutoClearSearch;
    }

    public void setSearchHint(Object text) {

        if (text instanceof String) {
            searchHint = (String) text;
        } else if (text instanceof Integer) {
            searchHint = getBaseContext().getString((Integer) text);
        } else {
            searchHint = "" + text;
        }

        floatingSearchView.setSearchHint(searchHint);
    }

    public void setSearchText(Object text) {

        String searchText;

        if (text instanceof String) {
            searchText = (String) text;
        } else if (text instanceof Integer) {
            searchText = getBaseContext().getString((Integer) text);
        } else {
            searchText = "" + text;
        }

        floatingSearchView.setSearchText(searchText);
    }

    private void addSearchKey() {

        if (!TextUtils.isEmpty(searchKey)) {
            SearchHistoryUtils.addKey(keys, searchKey);
        }
    }

    public void showSearchingProgress(boolean isShow) {

        if (isShow) {
            floatingSearchView.showProgress();
        } else {
            floatingSearchView.hideProgress();
        }
    }

    public void clearSearch() {
        searchKey = "";
        iSearchViewClickListener.onSearched(searchKey);
        floatingSearchView.clearSearchFocus();
    }

    public FloatingSearchView getFloatingSearchView() {
        return floatingSearchView;
    }

    /**
     * 显示短底部提示
     *
     * @param tip tip
     */
    public void showShortSnackbar(Object tip) {

        String txt = "";
        if (tip instanceof String) {
            txt = (String) tip;
        } else if (tip instanceof Integer) {
            txt = getBaseContext().getString((Integer) tip);
        }

        ViewGroup contentView = (ViewGroup) rootView;
        View includeView = rootView;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }

        Snackbar.make(includeView, txt, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示短底部提示并附带可点击文字
     *
     * @param tip       tip
     * @param clickText clickText
     */
    public void showShortSnackbar(Object tip, Object clickText, View.OnClickListener onClickListener) {

        String txt = "";
        String txt2 = "";

        if (tip instanceof String) {
            txt = (String) tip;
        } else if (tip instanceof Integer) {
            txt = getBaseContext().getString((Integer) tip);
        }

        if (clickText instanceof String) {
            txt2 = (String) clickText;
        } else if (tip instanceof Integer) {
            txt2 = getBaseContext().getString((Integer) clickText);
        }

        ViewGroup contentView = (ViewGroup) rootView;
        View includeView = rootView;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }

        Snackbar.make(includeView, txt, Snackbar.LENGTH_SHORT).setAction(txt2, onClickListener).show();
    }

    /**
     * 显示长底部提示
     *
     * @param tip tip
     */
    public void showLongSnackbar(Object tip) {

        String txt = "";
        if (tip instanceof String) {
            txt = (String) tip;
        } else if (tip instanceof Integer) {
            txt = getBaseContext().getString((Integer) tip);
        }

        ViewGroup contentView = (ViewGroup) rootView;
        View includeView = rootView;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }

        Snackbar.make(includeView, txt, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 显示长底部提示并附带可点击文字
     *
     * @param tip             tip
     * @param clickText       clickText
     * @param onClickListener onClickListener
     */
    public void showLongSnackbar(Object tip, Object clickText, View.OnClickListener onClickListener) {

        String txt = "";
        String txt2 = "";

        if (tip instanceof String) {
            txt = (String) tip;
        } else if (tip instanceof Integer) {
            txt = getBaseContext().getString((Integer) tip);
        }

        if (clickText instanceof String) {
            txt2 = (String) clickText;
        } else if (tip instanceof Integer) {
            txt2 = getBaseContext().getString((Integer) clickText);
        }

        ViewGroup contentView = (ViewGroup) rootView;
        View includeView = rootView;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof CoordinatorLayout) {
                includeView = contentView.getChildAt(i);
            }
        }

        Snackbar.make(includeView, txt, Snackbar.LENGTH_LONG).setAction(txt2, onClickListener).show();
    }

    /**
     * 界面跳转方法1
     *
     * @param clazz 要跳转的类
     */
    public void startMyActivity(
            Class<?> clazz) {

        Intent intent = new Intent(getBaseContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("hasSharedElement", false);
        getBaseContext().startActivity(intent);
        getBaseContext().overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_IN, HGSystemConfig.NOW_ACTIVITY_OUT);
    }

    /**
     * 界面跳转方法1-2
     *
     * @param clazz          clazz
     * @param publicView     publicView
     * @param transitionName transitionName
     */
    public void startMyActivity(
            Class<?> clazz,
            View publicView,
            Object transitionName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(getBaseContext(), clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("hasSharedElement", true);

            String strTransitionName = "";

            if (transitionName instanceof String) {
                strTransitionName = (String) transitionName;
            } else if (transitionName instanceof Integer) {
                strTransitionName = getBaseContext().getString((Integer) transitionName);
            }

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseContext(),
                            publicView,   // The view which starts the transition
                            strTransitionName    // The transitionName of the view we’re transitioning to
                    );
            ActivityCompat.startActivity(getBaseContext(), intent, options.toBundle());
        } else {
            startMyActivity(clazz);
        }
    }

    /**
     * 界面跳转方法2
     *
     * @param clazz  要跳转的类
     * @param key    键
     * @param values 值
     */
    public void startMyActivity(
            Class<?> clazz,
            String[] key,
            Object[] values) {

        Intent intent = new Intent(getBaseContext(), clazz);
        setIntentKeys(intent, key, values);
        intent.putExtra("hasSharedElement", false);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getBaseContext().startActivity(intent);
        getBaseContext().overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_IN, HGSystemConfig.NOW_ACTIVITY_OUT);
    }

    /**
     * 界面跳转方法2-2
     *
     * @param clazz          clazz
     * @param key            key
     * @param values         values
     * @param publicView     publicView
     * @param transitionName transitionName
     */
    public void startMyActivity(
            Class<?> clazz,
            String[] key,
            Object[] values,
            View publicView,
            Object transitionName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(getBaseContext(), clazz);
            setIntentKeys(intent, key, values);
            intent.putExtra("hasSharedElement", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            String strTransitionName = "";

            if (transitionName instanceof String) {
                strTransitionName = (String) transitionName;
            } else if (transitionName instanceof Integer) {
                strTransitionName = getBaseContext().getString((Integer) transitionName);
            }

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseContext(),
                            publicView,   // The view which starts the transition
                            strTransitionName    // The transitionName of the view we’re transitioning to
                    );
            ActivityCompat.startActivity(getBaseContext(), intent, options.toBundle());
        } else {
            startMyActivity(clazz, key, values);
        }
    }

    /**
     * 界面跳转方法3
     *
     * @param clazz       要跳转的类
     * @param requestCode 请求码
     */
    public void startMyActivityForResult(
            Class<?> clazz,
            int requestCode) {

        Intent intent = new Intent(getBaseContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("hasSharedElement", false);
        if (isActivity) {
            getBaseContext().startActivityForResult(intent, requestCode);
        } else {
            ((Fragment) initUI).startActivityForResult(intent, requestCode);
        }
        getBaseContext().overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_IN, HGSystemConfig.NOW_ACTIVITY_OUT);
    }

    /**
     * 界面跳转方法3-2
     *
     * @param clazz          clazz
     * @param requestCode    requestCode
     * @param publicView     publicView
     * @param transitionName transitionName
     */
    public void startMyActivityForResult(
            Class<?> clazz,
            int requestCode,
            View publicView,
            Object transitionName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(getBaseContext(), clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("hasSharedElement", true);

            String strTransitionName = "";

            if (transitionName instanceof String) {
                strTransitionName = (String) transitionName;
            } else if (transitionName instanceof Integer) {
                strTransitionName = getBaseContext().getString((Integer) transitionName);
            }

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseContext(),
                            publicView,   // The view which starts the transition
                            strTransitionName    // The transitionName of the view we’re transitioning to
                    );
            ActivityCompat.startActivityForResult(getBaseContext(), intent, requestCode, options.toBundle());
        } else {
            startMyActivityForResult(clazz, requestCode);
        }
    }

    /**
     * 界面跳转方法4
     *
     * @param clazz       要跳转的类
     * @param requestCode 请求码
     * @param key         键
     * @param values      值
     */
    public void startMyActivityForResult(
            Class<?> clazz,
            int requestCode,
            String[] key,
            Object[] values) {

        Intent intent = new Intent(getBaseContext(), clazz);
        setIntentKeys(intent, key, values);
        intent.putExtra("hasSharedElement", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (isActivity) {
            getBaseContext().startActivityForResult(intent, requestCode);
        } else {
            ((Fragment) initUI).startActivityForResult(intent, requestCode);
        }
        getBaseContext().overridePendingTransition(HGSystemConfig.NOW_ACTIVITY_IN, HGSystemConfig.NOW_ACTIVITY_OUT);
    }

    /**
     * 界面跳转方法4-2
     *
     * @param clazz          clazz
     * @param requestCode    requestCode
     * @param key            key
     * @param values         values
     * @param publicView     publicView
     * @param transitionName transitionName
     */
    public void startMyActivityForResult(
            Class<?> clazz,
            int requestCode,
            String[] key,
            Object[] values,
            View publicView,
            Object transitionName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(getBaseContext(), clazz);
            setIntentKeys(intent, key, values);
            intent.putExtra("hasSharedElement", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            String strTransitionName = "";

            if (transitionName instanceof String) {
                strTransitionName = (String) transitionName;
            } else if (transitionName instanceof Integer) {
                strTransitionName = getBaseContext().getString((Integer) transitionName);
            }

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseContext(),
                            publicView,   // The view which starts the transition
                            strTransitionName    // The transitionName of the view we’re transitioning to
                    );
            ActivityCompat.startActivityForResult(getBaseContext(), intent, requestCode, options.toBundle());
        } else {
            startMyActivityForResult(clazz, requestCode, key, values);
        }
    }

    /**
     * 界面跳转方法5
     *
     * @param clazz 要跳转的类
     */
    public void startMyActivityRipple(
            final Class<?> clazz,
            View triggerView,
            Integer rippleRes,
            final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CircularAnimUtils.fullActivity(getBaseContext(), triggerView).colorOrImageRes(rippleRes).go(new CircularAnimUtils.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {

                    Intent intent = new Intent(getBaseContext(), clazz);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getBaseContext().startActivity(intent);

                    new Handler().postDelayed(() -> {
                        if (activity != null) {
                            activity.finish();
                        }
                    }, 500);
                }
            });
        } else {
            startMyActivity(clazz);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 界面跳转方法5_2
     *
     * @param clazz 要跳转的类
     */
    public void startMyActivityRipple(
            final Class<?> clazz,
            View triggerView,
            Integer rippleRes,
            final String[] key,
            final Object[] values,
            final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CircularAnimUtils.fullActivity(getBaseContext(), triggerView).colorOrImageRes(rippleRes).go(() -> {

                Intent intent = new Intent(getBaseContext(), clazz);
                setIntentKeys(intent, key, values);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getBaseContext().startActivity(intent);

                new Handler().postDelayed(() -> {
                    if (activity != null) {
                        activity.finish();
                    }
                }, 500);
            });
        } else {
            startMyActivity(clazz, key, values);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 界面跳转方法6
     *
     * @param clazz 要跳转的类
     */
    public void startMyActivityForResultRipple(
            final Class<?> clazz,
            final int requestCode,
            View triggerView,
            Integer rippleRes,
            final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CircularAnimUtils.fullActivity(getBaseContext(), triggerView).colorOrImageRes(rippleRes).go(() -> {

                Intent intent = new Intent(getBaseContext(), clazz);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (isActivity) {
                    getBaseContext().startActivityForResult(intent, requestCode);
                } else {
                    ((Fragment) initUI).startActivityForResult(intent, requestCode);
                }

                new Handler().postDelayed(() -> {
                    if (activity != null) {
                        activity.finish();
                    }
                }, 500);
            });
        } else {
            startMyActivityForResult(clazz, requestCode);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 界面跳转方法6_2
     *
     * @param clazz 要跳转的类
     */
    public void startMyActivityForResultRipple(
            final Class<?> clazz,
            final int requestCode,
            View triggerView,
            Integer rippleRes,
            final String[] key,
            final Object[] values,
            final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CircularAnimUtils.fullActivity(getBaseContext(), triggerView).colorOrImageRes(rippleRes).go(() -> {

                Intent intent = new Intent(getBaseContext(), clazz);
                setIntentKeys(intent, key, values);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (isActivity) {
                    getBaseContext().startActivityForResult(intent, requestCode);
                } else {
                    ((Fragment) initUI).startActivityForResult(intent, requestCode);
                }

                new Handler().postDelayed(() -> {
                    if (activity != null) {
                        activity.finish();
                    }
                }, 500);
            });
        } else {
            startMyActivityForResult(clazz, requestCode, key, values);

            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 设置Intent的携带参数
     *
     * @param intent intent
     * @param key    key
     * @param value  values
     */
    private void setIntentKeys(Intent intent, String[] key, Object[] value) {

        if (key.length == value.length) {
            Map<String, Object> params = new HashMap<>();
            for (int i = 0; i < key.length; i++) {
                if (value[i] instanceof Class<?>) {
                    // 单独处理Class类型的参数
                    intent.putExtra(key[i], (Class<?>) value[i]);
                } else {
                    params.put(key[i], value[i]);
                }
            }
            intent.putExtra(HGParamKey.MapData.getValue(), new Gson().toJson(params));
        } else {
            LogUtils.Log(R.string.length_different);
        }
    }

    public View getContentView() {
        return contentView;
    }

    // 不暴露的方法

    /**
     * 初始化公共标题
     */
    private void initCommonTitle() {

        commonTitle = findViewById(R.id.commonTitleView);

        if (commonTitle != null) {
            getBaseContext().setSupportActionBar(commonTitle.getToolbar());

            commonTitle.setLeftOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    iCommonTitleClickListener.onLeftTitleClick(view);
                }
            });

            commonTitle.setCenterTitleOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    iCommonTitleClickListener.onCenterTitleClick(view);
                }
            });

            commonTitle.setRightOnClickListener(new OnToolbarMenuItemClickListener(getBaseContext(), false) {
                @Override
                public void onToolbarMenuItemClick(MenuItem item) {

                    View view = item.getActionView();

                    if (view == null) {
                        Object obj = ReflectUtils.getObjValue(commonTitle.getToolbar(), "mMenuView");
                        if (obj != null) {
                            View parent = (View) obj;
                            view = parent.findViewById(item.getItemId());
                        }
                    }

                    iCommonTitleClickListener.onRightTitleClick(view, item.getItemId());
                }
            });

            commonTitle.setRightTitleTextOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    iCommonTitleClickListener.onRightTitleClick(view, view.getId());
                }
            });
        }
    }

    /**
     * 获取状态控件
     *
     * @return HGStatusLayout
     */
    public HGStatusLayout getStatusLayout() {
        return statusLayout;
    }

    /**
     * 设置界面状态
     *
     * @param status {@link com.hg.hollowgoods.Widget.HGStatusLayout.Status}
     */
    public void setStatus(HGStatusLayout.Status status) {
        if (statusLayout != null) {
            statusLayout.setStatus(status);
        }
    }

    /**
     * 初始化传递的参数
     */
    private void initParams() {

        String str = "";

        if (isActivity) {
            str = getBaseContext().getIntent().getStringExtra(HGParamKey.MapData.getValue());
        } else {
            Bundle bundle = ((Fragment) initUI).getArguments();
            if (bundle != null) {
                str = bundle.getString(HGParamKey.MapData.getValue(), "");
            }
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                }.getType(), new MapTypeAdapter()).create();
        params = gson.fromJson(str, new TypeToken<Map<String, Object>>() {
        }.getType());

        if (params == null) {
            params = new HashMap<>();
        }
    }

    /**
     * 用于获取基本数据类型
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <T>          根据接收数据类型而定返回类型
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getParam(String key, T defaultValue) {
        Object obj = params.get(key);
        return obj == null ? defaultValue : (T) obj;
    }

    /**
     * 用于获取封装类型数据
     *
     * @param key  键
     * @param type 数据类型
     * @param <T>  根据接收数据类型而定返回类型
     * @return <T>
     */
    public <T> T getParam(String key, Type type) {
        return new Gson().fromJson(new Gson().toJson(params.get(key)), type);
    }

}
