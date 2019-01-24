package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Fragment.Example.Ex3ChildFragment;

/**
 * Fragment使用ToolBar示例界面
 * Created by HG
 */
public class Ex3Activity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_3;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setActionBar(CommonResource.TITLE_BAR_RESOURCE);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl, new Ex3ChildFragment());
        ft.commit();

        return null;
    }

    @Override
    public void setListener() {

    }

}
