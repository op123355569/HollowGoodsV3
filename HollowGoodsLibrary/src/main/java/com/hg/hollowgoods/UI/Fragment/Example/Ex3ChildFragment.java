package com.hg.hollowgoods.UI.Fragment.Example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.BaseFragment;

/**
 * Fragment使用ToolBar碎片
 * Created by Hollow Goods on unknown.
 */

public class Ex3ChildFragment extends BaseFragment {

    private Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.fragment_ex_3_child;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        toolbar = baseUI.findViewById(R.id.toolbar);

        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle(R.string.title_level_1);
        // Sub Title
//        toolbar.setSubtitle(R.string.title_level_2);
        //Navigation Icon
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setBackgroundResource(HGCommonResource.TITLE_BAR_RESOURCE);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> ((BaseActivity) getActivity()).finishMyActivity());

        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_fragment_child_ex_3, menu);
        baseUI.setIconVisible(menu, true);
    }
}
