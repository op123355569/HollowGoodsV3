package com.hg.hollowgoods.Widget.BugView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.APPUtils;
import com.hg.hollowgoods.Util.CircularAnimUtils;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.StatusBarUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * BugView菜单项
 */
public class FloatBugViewMenuItem extends FrameLayout {

    private final String IMG_PATH = HGSystemConfig.getBugPath() + "/img/";

    private Context mContext;
    /**
     * 捕捉按钮
     */
    private Button mWatch;
    /**
     * 查看按钮
     */
    private Button mCatch;
    /**
     * 返回控件
     */
    private View mBack;
    private View mScan;

    /**
     * 记录菜单项的宽度
     */
    public static int mViewWidth;
    /**
     * 记录菜单项的高度
     */
    public static int mViewHeight;
    private String mImgName = "";

    public FloatBugViewMenuItem(Context context) {

        super(context);
        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.float_bug_view_menu_item, this);
        View view = findViewById(R.id.bug_view_menu_item_layout);
        mViewWidth = view.getLayoutParams().width;
        mViewHeight = view.getLayoutParams().height;

        mWatch = findViewById(R.id.btn_watch);
        mCatch = findViewById(R.id.btn_catch);
        mBack = findViewById(R.id.back);
        mScan = findViewById(R.id.scan);

        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });

        mWatch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BugWatchActivity.class);
            }
        });
        mCatch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot();
            }
        });
    }

    private void startActivity(final Class<?> clazz) {

        IBaseApplication baseApplication = ApplicationBuilder.create();
        if (APPUtils.getAndroidSDKVersion() >= 21
                && baseApplication.getAllActivity() != null
                && baseApplication.getAllActivity().size() > 0) {
            CircularAnimUtils.fullActivity(
                    baseApplication.getAllActivity().get(baseApplication.getAllActivity().size() - 1),
                    mWatch)
                    .colorOrImageRes(R.color.colorAccent)
                    .go(() -> {
                        Intent intent = new Intent(mContext, clazz);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("hasSharedElement", false);
                        intent.putExtra("data", mImgName);
                        mContext.startActivity(intent);
                    });
        } else {
            Intent intent = new Intent(mContext, clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("hasSharedElement", false);
            intent.putExtra("data", mImgName);
            mContext.startActivity(intent);
        }

        doBack();
    }

    private void doBack() {

        // 点击返回的时候，移除菜单项，创建菜单开关
        FloatBugViewWindowManager.removeMenuItem(mContext);
        FloatBugViewWindowManager.createMenuSwitch(mContext);
    }

    private void screenshot() {

        IBaseApplication baseApplication = ApplicationBuilder.create();
        if (baseApplication.getAllActivity() != null && baseApplication.getAllActivity().size() > 0) {
            mImgName = System.currentTimeMillis() + ".jpg";

            // 获取屏幕
            Activity activity = baseApplication
                    .getAllActivity()
                    .get(baseApplication.getAllActivity().size() - 1);
            WindowManager windowManager = (WindowManager) activity.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Object mGlobal = getObjValue(windowManager, "mGlobal");
            ArrayList<WindowManager.LayoutParams> mParams;
            ArrayList<View> mViews;
            Object temp1 = getObjValue(mGlobal, "mParams");
            Object temp2 = getObjValue(mGlobal, "mViews");

            if (temp1 != null) {
                if (temp1 instanceof List) {
                    mParams = (ArrayList<WindowManager.LayoutParams>) temp1;
                } else {
                    WindowManager.LayoutParams[] mParams2 = (WindowManager.LayoutParams[]) temp1;
                    mParams = new ArrayList<>();
                    for (int i = 0; i < mParams2.length; i++) {
                        mParams.add(mParams2[i]);
                    }
                }
            } else {
                mParams = new ArrayList<>();
            }

            if (temp2 != null) {
                if (temp2 instanceof List) {
                    mViews = (ArrayList<View>) temp2;
                } else {
                    View[] mViews2 = (View[]) temp2;
                    mViews = new ArrayList<>();
                    for (int i = 0; i < mViews2.length; i++) {
                        mViews.add(mViews2[i]);
                    }
                }
            } else {
                mViews = new ArrayList<>();
            }

            Bitmap bmp = groupViews(
                    mViews,
                    mParams,
                    windowManager.getDefaultDisplay().getWidth(),
                    windowManager.getDefaultDisplay().getHeight(),
                    activity.getClass().getName()
            );

            if (bmp != null) {
                FormatUtils.savePhoto(bmp, IMG_PATH, mImgName, 100, false);
                doScanAnim();
            }
        } else {
            Toast.makeText(mContext, "截图失败", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap groupViews(
            ArrayList<View> views,
            ArrayList<WindowManager.LayoutParams> params,
            int phoneWidth,
            int phoneHeight,
            String activityName) {

        if (views != null && views.size() > 0) {
            Bitmap result = Bitmap.createBitmap(phoneWidth, phoneHeight, Bitmap.Config.ARGB_8888);
            Bitmap temp;
            Canvas canvas = null;
            int viewWidth;
            int viewHeight;
            Activity activity;
            int flag = -1;
            int breakPosition = -1;
            boolean needDuckBg = false;

            for (int i = 0; i < views.size(); i++) {
                if (views.get(i).getContext() instanceof Activity) {
                    activity = (Activity) views.get(i).getContext();
                    if (TextUtils.equals(activity.getClass().getName(), activityName) && flag == -1) {
                        flag = i;
                    }
                }

                if (views.get(i) instanceof FloatBugViewMenuItem) {
                    breakPosition = i;
                }
            }

            if (params != null && params.size() > flag) {
                for (int i = flag; i < params.size(); i++) {
                    if (params.get(i).dimAmount != 1.0f) {
                        needDuckBg = true;
                    }
                }
            }

            for (int i = flag; i < views.size(); i++) {
                if (i != breakPosition) {
                    viewWidth = views.get(i).getWidth();
                    viewHeight = views.get(i).getHeight();
                    if (viewHeight != phoneHeight) {
                        viewHeight = viewHeight - StatusBarUtils.getStatusBarHeight(mContext);
                    }
                    views.get(i).setDrawingCacheEnabled(true);
                    views.get(i).buildDrawingCache();
                    temp = views.get(i).getDrawingCache();

                    if (i == flag) {
                        canvas = new Canvas(result);
                    }
                    canvas.drawBitmap(temp, (phoneWidth - viewWidth) / 2, (phoneHeight - viewHeight) / 2, null);

                    if (needDuckBg && i == flag) {
                        temp = Bitmap.createBitmap(phoneWidth, phoneHeight, Bitmap.Config.ARGB_8888);
                        temp.eraseColor(Color.parseColor("#99000000"));//填充颜色
                        canvas.drawBitmap(temp, 0, 0, null);
                    }
                }
            }

            return result;
        }

        return null;
    }

    private Object getObjValue(Object obj, String valueName) {

        Object result = null;

        try {
            Class clazz = obj.getClass();
            Field field = clazz.getDeclaredField(valueName);

            if (field != null) {
                //设置些属性是可以访问的
                field.setAccessible(true);
                //得到此属性的值
                result = field.get(obj);
            }
        } catch (Exception e) {
            LogUtils.Log(e.getMessage());
        }

        return result;
    }

    private void doScanAnim() {

        AnimatorSet set = new AnimatorSet();
        AnimatorSet setZoom1 = new AnimatorSet();
        AnimatorSet setZoom2 = new AnimatorSet();

        setZoom1.playTogether(
                ObjectAnimator.ofFloat(mScan, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(mScan, "scaleY", 1f, 0f)
        );
        setZoom2.playTogether(
                ObjectAnimator.ofFloat(mScan, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(mScan, "scaleY", 0f, 1f)
        );

        set.playSequentially(
                setZoom1,
                setZoom2
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mScan.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mScan.setVisibility(GONE);

                startActivity(BugCatchActivity.class);
            }
        });
        set.setDuration(300).start();
    }

}
