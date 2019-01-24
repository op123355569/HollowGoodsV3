package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.FloatingNavigationView;

/**
 * 侧边菜单示例界面
 * Created by HG
 */

public class Ex19Activity extends BaseActivity {

    private FloatingNavigationView mFloatingNavigationView;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_19;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        mFloatingNavigationView = findViewById(R.id.floating_navigation_view);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex19);

        return null;
    }

    @Override
    public void setListener() {
        mFloatingNavigationView.setOnClickListener(view -> mFloatingNavigationView.open());
        mFloatingNavigationView.setNavigationItemSelectedListener(item -> {
            t.showShortToast(item.getTitle());
            mFloatingNavigationView.close();
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

}
