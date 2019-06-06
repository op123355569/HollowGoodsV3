package com.hg.hollowgoods.UI.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * 代理基础碎片
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            baseUI.initUI(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        baseUI.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
