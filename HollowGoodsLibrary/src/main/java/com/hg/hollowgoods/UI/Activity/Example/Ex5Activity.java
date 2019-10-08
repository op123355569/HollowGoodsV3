package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Fragment.Example.Ex5ChildFragment;
import com.hg.hollowgoods.Util.LogUtils;

import java.util.ArrayList;

/**
 * BottomNavigationBar示例界面
 * Created by Hollow Goods on unknown.
 */

public class Ex5Activity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private Ex5ChildFragment fragment1;
    private Ex5ChildFragment fragment2;
    private Ex5ChildFragment fragment3;
    private Ex5ChildFragment fragment4;
    private TextBadgeItem numberBadgeItem;
    private String msgCount = "99";

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_5;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex5, R.menu.menu_activity_ex_5);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        /**
         *添加标签的消息数量
         */
        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText(msgCount)
                .setHideOnSelect(true);
        TextBadgeItem numberBadgeItem2 = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("10")
                .setHideOnSelect(true);
        /**
         *添加tab标签页
         */
        bottomNavigationBar//
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Books")
                        .setActiveColorResource(R.color.teal)
                        .setBadgeItem(numberBadgeItem)
                )
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music")
                        .setActiveColorResource(R.color.blue)
                )
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Movies & TV")
                        .setActiveColorResource(R.color.brown)
                )
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Games")
                        .setActiveColorResource(R.color.grey2)
                        .setBadgeItem(numberBadgeItem2)
                )
                .initialise();
        /**
         *首次进入不会主动回调选中页面的监听
         *所以这里自己调用一遍，初始化第一个页面
         */
        onTabSelected(0);
        bottomNavigationBar.setTabSelectedListener(this);

        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onRightTitleClick(View view, int id) {
        msgCount = "0";
        numberBadgeItem.setText(msgCount);
        numberBadgeItem.hide();
    }

    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        /**
         *每次添加之前隐藏所有正在显示的Fragment
         *然后如果是第一次添加的话使用transaction.add();
         *但第二次显示的时候,使用transaction.show();
         *这样子我们就可以保存Fragment的状态了
         */
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new Ex5ChildFragment();
                    fragment1.setFragmentArguments(new String[]{"title"}, new Object[]{"1"});
                    transaction.add(R.id.layFrame, fragment1);
                    fragments.add(fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = new Ex5ChildFragment();
                    fragment2.setFragmentArguments(new String[]{"title"}, new Object[]{"2"});
                    transaction.add(R.id.layFrame, fragment2);
                    fragments.add(fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
            case 2:
                if (fragment3 == null) {
                    fragment3 = new Ex5ChildFragment();
                    fragment3.setFragmentArguments(new String[]{"title"}, new Object[]{"3"});
                    transaction.add(R.id.layFrame, fragment3);
                    fragments.add(fragment3);
                } else {
                    transaction.show(fragment3);
                }
                break;
            case 3:
                if (fragment4 == null) {
                    fragment4 = new Ex5ChildFragment();
                    fragment4.setFragmentArguments(new String[]{"title"}, new Object[]{"4"});
                    transaction.add(R.id.layFrame, fragment4);
                    fragments.add(fragment4);
                } else {
                    transaction.show(fragment4);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        LogUtils.Log(position);
        if (position == 0 && msgCount.equals("0")) {
            numberBadgeItem.hide(false);
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * @param transaction
     */
    public void hideFragment(FragmentTransaction transaction) {

        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
    }

}
