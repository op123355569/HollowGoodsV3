package com.hg.hollowgoods.UI.Activity.Test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Bean.Test.HGTest;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.ReflectUtils;

import java.util.ArrayList;

/**
 * 测试界面
 * Created by Hollow Goods on unknown.
 */
public class HGTestActivity extends BaseActivity {

    private final int baseId = 1;

    private LinearLayout menu;

    private ArrayList<HGTest> tests;

    @Override
    public int bindLayout() {
        return R.layout.activity_hg_test;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public void initParamData() {

        tests = baseUI.getParam(HGParamKey.ListData.getValue(), new TypeToken<ArrayList<HGTest>>() {
        }.getType());

        if (tests == null) {
            tests = new ArrayList<>();
        }
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        menu = findViewById(R.id.menu);

        return null;
    }

    @Override
    public void setListener() {

        Button btn;
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(20, 20, 20, 20);

        for (int i = 0; i < tests.size(); i++) {
            btn = new Button(this);
            btn.setText(tests.get(i).getBtnName());
            btn.setId(baseId + i);
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setBackgroundResource(R.drawable.button_main_color);
            btn.setAllCaps(false);
            btn.setLayoutParams(llp);
            btn.setOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View v, int id) {

                    int position = id;
                    position = position - 1;

                    HGTest t = tests.get(position);

                    if (t.getKeys() == null) {
                        baseUI.startMyActivityRipple(ReflectUtils.getClassByPackageName(t.getJumpClassName()), v, HGSystemConfig.ACTIVITY_CHANGE_RES, null);
                    } else {
                        baseUI.startMyActivityRipple(ReflectUtils.getClassByPackageName(t.getJumpClassName()), v, HGSystemConfig.ACTIVITY_CHANGE_RES, t.getKeys(), t.getValues(), null);
                    }
                }
            });
            menu.addView(btn);
        }
    }

    @Override
    public boolean haveScroll() {
        return true;
    }

}
