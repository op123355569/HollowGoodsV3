package com.hg.hollowgoods.Widget.BugView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.StatusBarUtils;

/**
 * BugView菜单开关
 */
public class FloatBugViewMenuSwitch extends FrameLayout {

    private Context mContext;
    /**
     * 记录菜单开关的宽度
     */
    public static int mViewWidth;
    /**
     * 记录菜单开关的高度
     */
    public static int mViewHeight;
    /**
     * 用于更新菜单开关的位置
     */
    private WindowManager mWindowManager;
    /**
     * 菜单开关的参数
     */
    private WindowManager.LayoutParams mParams;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float mXInScreen;
    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float mYInScreen;
    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float mXDownInScreen;
    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float mYDownInScreen;
    /**
     * 记录手指按下时在菜单开关的View上的横坐标的值
     */
    private float mXInView;
    /**
     * 记录手指按下时在菜单开关的View上的纵坐标的值
     */
    private float mYInView;
    private int mScreenWidth;

    private boolean isMove = false;

    public FloatBugViewMenuSwitch(Context context) {
        super(context);
        mContext = context;

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater.from(context).inflate(R.layout.float_bug_view_menu_switch, this);
        View view = findViewById(R.id.bug_view_menu_switch_layout);

        mViewWidth = view.getLayoutParams().width;
        mViewHeight = view.getLayoutParams().height;

        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                mXInView = event.getX();
                mYInView = event.getY();
                mXDownInScreen = event.getRawX();
                mYDownInScreen = event.getRawY() - StatusBarUtils.getStatusBarHeight(mContext);
                mXInScreen = event.getRawX();
                mYInScreen = event.getRawY() - StatusBarUtils.getStatusBarHeight(mContext);
                break;
            case MotionEvent.ACTION_MOVE:
                mXInScreen = event.getRawX();
                mYInScreen = event.getRawY() - StatusBarUtils.getStatusBarHeight(mContext);
                // 手指移动的时候更新菜单开关的位置
                updateViewPosition(false);
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition(true);
                // 如果未移动过，则视为触发了单击事件。
                if (!isMove) {
                    openMenuItem();
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 将菜单开关的参数传入，用于更新菜单开关的位置。
     *
     * @param params 菜单开关的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新菜单开关在屏幕中的位置。
     */
    private void updateViewPosition(boolean isUp) {

        if (Math.abs(mXDownInScreen - mXInScreen) > 5 || Math.abs(mYDownInScreen - mYInScreen) > 5) {
            isMove = true;
            mParams.x = (int) (mXInScreen - mXInView);
            mParams.y = (int) (mYInScreen - mYInView);
            if (isUp) {
                if (mParams.x < (mScreenWidth - mViewWidth) / 2) {
                    mParams.x = 0;
                } else {
                    mParams.x = mScreenWidth - mViewWidth;
                }
            }
            mWindowManager.updateViewLayout(this, mParams);
        }
    }

    /**
     * 打开菜单项，同时关闭菜单开关。
     */
    private void openMenuItem() {
        FloatBugViewWindowManager.createMenuItem(getContext());
        FloatBugViewWindowManager.removeMenuSwitch(getContext());
    }

}
