package com.hg.hollowgoods.UI.Fragment.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseFragment;

/**
 * @ClassName:注册碎片
 * @Description:
 * @author: HollowGoods
 * @date: 2018年12月29日
 */
public class HGRegisterFragment extends BaseFragment {

    @Override
    public int bindLayout() {
        return R.layout.fragment_hg_register;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleViewVisibility(false);

        return null;
    }

    @Override
    public void setListener() {

    }

}
