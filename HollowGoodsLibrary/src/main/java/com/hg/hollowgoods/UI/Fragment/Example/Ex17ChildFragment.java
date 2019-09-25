package com.hg.hollowgoods.UI.Fragment.Example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseFragment;

/**
 * Created by HG
 */

public class Ex17ChildFragment extends BaseFragment {

    private TextView title;

    private String strTitle;

    @Override
    public int bindLayout() {
        return R.layout.fragment_ex_17_child;
    }

    @Override
    public void initParamData() {
        strTitle = baseUI.getParam("title", "");
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        title = baseUI.findViewById(R.id.tv_title);

        title.setText(strTitle);

        return null;
    }

    @Override
    public void setListener() {

    }

}
