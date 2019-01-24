package com.hg.hollowgoods.UI.Activity.Index;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Login.LoginActivity;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.LogUtils;
import com.jaredrummler.android.widget.AnimatedSvgView;

/**
 * 启动界面
 * Created by HG on 2018-06-07.
 */
public class IndexActivity extends BaseActivity {

    private AnimatedSvgView animatedSvgView;
    private View gotoNext;

    private boolean canGotoNext = true;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_index;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        animatedSvgView = findViewById(R.id.animatedSvgView);
        gotoNext = findViewById(R.id.gotoNext);

        baseUI.setCommonTitleViewVisibility(false);
        baseUI.hideActionBar();
        baseUI.setStatusBackgroundColor(R.color.main_body_color);

        return null;
    }

    @Override
    public void setListener() {

        baseUI.setOnPermissionsListener((isAgreeAll, requestCode, permissions, isAgree) -> {
            if (isAgreeAll) {
                gotoNext();
            }
        });

        gotoNext.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                gotoNext();
            }
        });

        animatedSvgView.setOnStateChangeListener(state -> {

            switch (state) {
                case AnimatedSvgView.STATE_NOT_STARTED:
                    // 动画重新开始或者未开始
                    LogUtils.Log("动画重新开始或者未开始");
                    break;
                case AnimatedSvgView.STATE_TRACE_STARTED:
                    // 描边开始
                    LogUtils.Log("描边开始");
                    break;
                case AnimatedSvgView.STATE_FILL_STARTED:
                    // 填充开始
                    LogUtils.Log("填充开始");
                    break;
                case AnimatedSvgView.STATE_FINISHED:
                    // 动画结束
                    gotoNext();
                    break;
            }
        });

        animatedSvgView.start();
    }

    private void gotoNext() {
        if (canGotoNext) {
            canGotoNext = false;

            if (baseUI.requestIOPermission()) {
                baseUI.startMyActivityRipple(LoginActivity.class, gotoNext, HGSystemConfig.ACTIVITY_CHANGE_RES, this);
            } else {
                canGotoNext = true;
            }
        }
    }

}
