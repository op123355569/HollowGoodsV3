package com.hg.hollowgoods.UI.Activity.Main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Example.ExampleActivity;
import com.hg.hollowgoods.UI.Activity.Test.HGTestActivity;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.IP.InterfaceConfig;

/**
 * 主界面
 * Created by Hollow Goods 2018-06-07.
 */
public class HollowGoodsActivity extends BaseActivity {

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_hollow_goods;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_example_white_24dp, R.string.title_activity_main, R.menu.menu_activity_hollow_goods);

        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onLeftTitleClick(View view) {
        baseUI.startMyActivityRipple(ExampleActivity.class, view, HGSystemConfig.ACTIVITY_CHANGE_RES, null);
    }

    @Override
    public void onRightTitleClick(View view, int id) {
        if (id == R.id.action_1) {
            new InterfaceConfig().showIPDialog(this);
        } else if (id == R.id.action_2) {
            baseUI.startMyActivityRipple(HGTestActivity.class, view, HGSystemConfig.ACTIVITY_CHANGE_RES, null);
        } else if (id == R.id.action_3) {
            baseUI.startMyActivity(HGFeedbackActivity.class);
        }
    }

    @Override
    public void onBackPressed() {

        if (HGSystemConfig.IS_DEBUG_MODEL) {
            super.onBackPressed();
        } else {
            if (baseUI.checkBackPressed()) {
                super.onBackPressed();
            }
        }
    }

}
