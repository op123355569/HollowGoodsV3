package com.hg.hollowgoods.UI.Base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * 基Fragment<br>
 * 特殊接口：DialogClickListener
 * <p>
 * Created by HG
 */
@SuppressLint("NewApi")
public abstract class ProxyBaseFragment extends Fragment {

    public BaseUI baseUI = new BaseUI();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseUI.initUI(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        baseUI.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
