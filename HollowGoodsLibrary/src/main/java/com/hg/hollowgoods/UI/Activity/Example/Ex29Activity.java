package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.FabOptions.FabOptions;

/**
 * 悬浮展开菜单示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex29Activity extends BaseActivity {

    private FabOptions fabOptions;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_29;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        fabOptions = findViewById(R.id.fab_options);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex29);

        return null;
    }

    @Override
    public void setListener() {

        fabOptions.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {

                int position = 0;

                if (id == R.id.action_1) {
                    position = 1;
                } else if (id == R.id.action_2) {
                    position = 2;
                } else if (id == R.id.action_3) {
                    position = 3;
                } else if (id == R.id.action_4) {
                    position = 4;
                }

                t.info("第" + position + "项");
            }
        });
    }

}
