package com.hg.hollowgoods.UI.Activity.Login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * @ClassName:登录/注册界面
 * @Description:
 * @author: HollowGoods
 * @date: 2018年12月29日
 */
public class LoginActivity extends BaseActivity {

    private TextView registerInvoker;
    private LinearLayout registerLayout;
    private TextView loginInvoker;
    private LinearLayout loginLayout;
    private Button register;
    private Button login;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        registerInvoker = findViewById(R.id.tv_registerInvoker);
        registerLayout = findViewById(R.id.ll_register);
        loginInvoker = findViewById(R.id.tv_loginInvoker);
        loginLayout = findViewById(R.id.ll_login);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.btn_login);

        baseUI.setCommonTitleViewVisibility(false);
        baseUI.setTranslucentActionBar(true);
        baseUI.hideActionBar();

        showLoginForm();

        return null;
    }

    @Override
    public void setListener() {

        registerInvoker.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                showRegisterForm();
            }
        });

        loginInvoker.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                showLoginForm();
            }
        });
    }

    private void showRegisterForm() {

        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) loginLayout.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        loginLayout.requestLayout();

        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) registerLayout.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        registerLayout.requestLayout();

        registerInvoker.setVisibility(View.GONE);
        loginInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        registerLayout.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        register.startAnimation(clockwise);
    }

    private void showLoginForm() {

        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) loginLayout.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        loginLayout.requestLayout();

        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) registerLayout.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        registerLayout.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        loginLayout.startAnimation(translate);

        registerInvoker.setVisibility(View.VISIBLE);
        loginInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        login.startAnimation(clockwise);
    }

}
