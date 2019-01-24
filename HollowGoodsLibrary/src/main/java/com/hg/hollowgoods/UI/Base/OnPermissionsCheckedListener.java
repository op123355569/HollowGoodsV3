package com.hg.hollowgoods.UI.Base;

/**
 * Created by HG
 */

public interface OnPermissionsCheckedListener {

    void onPermissionsResult(boolean isAgreeAll, int requestCode, String[] permissions, boolean[] isAgree);

}
