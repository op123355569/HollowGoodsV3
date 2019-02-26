package com.hg.hollowgoods.UI.Fragment.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Activity.Main.HollowGoodsActivity;
import com.hg.hollowgoods.UI.Base.BaseFragment;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;

/**
 * @ClassName:登录碎片
 * @Description:
 * @author: HollowGoods
 * @date: 2018年12月29日
 */
public class HGLoginFragment extends BaseFragment {

    private Button login;

    @Override
    public int bindLayout() {
        return R.layout.fragment_hg_login;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        login = baseUI.findViewById(R.id.btn_login);

        baseUI.setCommonTitleViewVisibility(false);

        return null;
    }

    @Override
    public void setListener() {

        login.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                baseUI.startMyActivity(HollowGoodsActivity.class);
                finishMyActivity();
            }
        });
    }

}
