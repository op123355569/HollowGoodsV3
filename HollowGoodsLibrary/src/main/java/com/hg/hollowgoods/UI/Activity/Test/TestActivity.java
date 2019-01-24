package com.hg.hollowgoods.UI.Activity.Test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

import java.util.ArrayList;

/**
 * 测试界面
 * Created by HG
 */
public class TestActivity extends BaseActivity {

    private LinearLayout menu;

    private final int baseId = 1;
    private ArrayList<Test> tests = new ArrayList<>();

    private void initTestData() {
//        tests.add(Test test);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_test;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        menu = findViewById(R.id.menu);

        initTestData();

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

                    Test t = tests.get(position);

                    if (t.getKeys() == null) {
                        baseUI.startMyActivityRipple(t.getJumpClass(), v, HGSystemConfig.ACTIVITY_CHANGE_RES, null);
                    } else {
                        baseUI.startMyActivityRipple(t.getJumpClass(), v, HGSystemConfig.ACTIVITY_CHANGE_RES, t.getKeys(), t.getValues(), null);
                    }
                }
            });
            menu.addView(btn);
        }
    }

    private class Test {
        private String btnName;
        private Class<?> jumpClass;
        private String[] keys;
        private Object[] values;

        public Test(String btnName, Class<?> jumpClass, String[] keys, Object[] values) {
            this.btnName = btnName;
            this.jumpClass = jumpClass;
            this.keys = keys;
            this.values = values;
        }

        public Test(String btnName, Class<?> jumpClass) {
            this.btnName = btnName;
            this.jumpClass = jumpClass;
            this.keys = null;
            this.values = null;
        }

        public String getBtnName() {
            return btnName;
        }

        public void setBtnName(String btnName) {
            this.btnName = btnName;
        }

        public Class<?> getJumpClass() {
            return jumpClass;
        }

        public void setJumpClass(Class<?> jumpClass) {
            this.jumpClass = jumpClass;
        }

        public String[] getKeys() {
            return keys;
        }

        public void setKeys(String[] keys) {
            this.keys = keys;
        }

        public Object[] getValues() {
            return values;
        }

        public void setValues(Object[] values) {
            this.values = values;
        }
    }

}
