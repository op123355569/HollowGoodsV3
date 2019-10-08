package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;

/**
 * Activity使用ToolBar示例界面
 * Created by Hollow Goods on unknown.
 */
public class Ex4Activity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_4;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        toolbar = findViewById(R.id.toolbar);

        baseUI.setActionBar(HGCommonResource.TITLE_BAR_RESOURCE);

        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle(R.string.title_level_1);
        // Sub Title
//        toolbar.setSubtitle(R.string.title_level_2);
        //Navigation Icon
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setBackgroundResource(HGCommonResource.TITLE_BAR_RESOURCE);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMyActivity();
            }
        });

        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        baseUI.setIconVisible(menu, true);

        return true;
    }

}
