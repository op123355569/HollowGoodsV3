package ${packageName};

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hg.hollowgoods.Bean.TabBarData;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Fragment.Login.HGLoginFragment;
import com.hg.hollowgoods.UI.Fragment.Login.HGRegisterFragment;
import com.hg.hollowgoods.Util.ResUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * ${activityTitle}界面
 *
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @ViewInject(value = R.id.bottom_navigation_bar)
    private BottomNavigationBar bottomNavigationBar;

    private HashMap<Integer, Fragment> fragments = new HashMap<>();
    private ArrayList<TabBarData> fragmentData = new ArrayList<>();

	@Override
    public Activity addToExitGroup() {
        return this;
    }
	
	@Override
    public int bindLayout() {
        return R.layout.${layoutName};
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_${activityToLayout(activityClass)});

        TabBarData tabBarData = new TabBarData();
        tabBarData.setFragmentClass(HGLoginFragment.class);
        tabBarData.setLabelName("示例1");
        tabBarData.setActiveIconRes(R.drawable.ic_android_green_24dp);
        tabBarData.setInactiveIconRes(R.drawable.ic_android_black_24dp);
        fragmentData.add(tabBarData);

        tabBarData = new TabBarData();
        tabBarData.setFragmentClass(HGRegisterFragment.class);
        tabBarData.setLabelName("示例2");
        tabBarData.setActiveIconRes(R.drawable.ic_android_green_24dp);
        tabBarData.setInactiveIconRes(R.drawable.ic_android_black_24dp);
        fragmentData.add(tabBarData);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        // 添加tab标签页
        for (TabBarData t : fragmentData) {
            BottomNavigationItem item;

            // 加载激活状态图片
            Integer res = t.getActiveIconRes() == null ? ResUtils.getImageResources(baseUI.getBaseContext(), t.getActiveIconResName(), t.getActiveIconResType()) : t.getActiveIconRes();
            item = new BottomNavigationItem(
                    res == null ? R.drawable.ic_android_green_24dp : res,
                    t.getLabelName()
            );

            // 加载未激活状态图片
            res = t.getInactiveIconRes() == null ? ResUtils.getImageResources(baseUI.getBaseContext(), t.getInactiveIconResName(), t.getInactiveIconResType()) : t.getInactiveIconRes();
            item.setInactiveIconResource(
                    res == null ? R.drawable.ic_android_black_24dp : res
            );

            bottomNavigationBar.addItem(item);
        }
        bottomNavigationBar.initialise();

        // 首次进入不会主动回调选中页面的监听
        // 所以这里自己调用一遍，初始化第一个页面
        onTabSelected(0);

        return null;
    }

    @Override
    public void setListener() {
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        // 每次添加之前隐藏所有正在显示的Fragment
        // 然后如果是第一次添加的话使用transaction.add();
        // 但第二次显示的时候,使用transaction.show();
        // 这样子我们就可以保存Fragment的状态了

        hideFragment(transaction);

        if (fragments.get(position) == null) {
            Fragment fragment;

            try {
                fragment = fragmentData.get(position).getFragmentClass().newInstance();
            } catch (Exception e) {
                fragment = null;
            }

            fragments.put(position, fragment);
            transaction.add(R.id.layFrame, fragments.get(position));
        } else {
            transaction.show(fragments.get(position));
        }

        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 隐藏碎片
     *
     * @param transaction transaction
     */
    public void hideFragment(FragmentTransaction transaction) {

        Set<Integer> keySet = fragments.keySet();

        for (Integer integer : keySet) {
            transaction.hide(fragments.get(integer));
        }
    }
	
}
