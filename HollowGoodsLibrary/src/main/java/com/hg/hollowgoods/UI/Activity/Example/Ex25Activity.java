package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

/**
 * 新吐司示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex25Activity extends BaseActivity {

    private View button;

    private int position = -1;
    private int length = 5;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_25;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        button = findViewById(R.id.button);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex25);

        return null;
    }

    @Override
    public void setListener() {
        button.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                position++;
                showToast(position % length);
            }
        });
    }

    private void showToast(int position) {

        switch (position) {
            case 0:
                t.normal("正常吐司");
                break;
            case 1:
                t.info("信息吐司");
                break;
            case 2:
                t.warning("警告吐司");
                break;
            case 3:
                t.error("错误吐司");
                break;
            case 4:
                t.success("成功吐司");
                break;
        }
    }

}
