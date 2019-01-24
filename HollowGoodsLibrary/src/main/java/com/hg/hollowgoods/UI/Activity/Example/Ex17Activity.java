package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hg.hollowgoods.Adapter.ViewPagerAdapter.FragmentViewPagerAdapter;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Fragment.Example.Ex17ChildFragment;
import com.hg.hollowgoods.Widget.ViewPager.ScaleTransformer;

import java.util.ArrayList;

/**
 * ViewPager示例界面
 * Created by HG
 */

public class Ex17Activity extends BaseActivity {

    private final int viewCount = 3;

    private ViewPager viewPager;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentViewPagerAdapter adapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_17;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        viewPager = findViewById(R.id.viewPager);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex17);

        Ex17ChildFragment fragment;
        for (int i = 0; i < viewCount; i++) {
            fragment = new Ex17ChildFragment();
            fragment.setFragmentArguments(new String[]{"title"}, new Object[]{"" + (i + 1)});
            fragments.add(fragment);
        }

        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setPageMargin(0);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new ScaleTransformer());

        return null;
    }

    @Override
    public void setListener() {
        ((ViewGroup) (viewPager.getParent())).setOnTouchListener((v, event) -> viewPager.onTouchEvent(event));
    }

}
