package com.hg.hollowgoods.UI.Base.Message.Toast;

import com.hg.hollowgoods.R;

/**
 * 吐司类型 主要用于区分吐司的图标
 * Created by HG on 2018-06-07.
 */
public enum ToastType {

    Info(R.drawable.ic_toast_info),// 信息
    Warning(R.drawable.ic_toast_warning),// 警告
    Error(R.drawable.ic_toast_error),// 错误
    Success(R.drawable.ic_toast_success),// 成功
    Normal(R.drawable.ic_toast_normal),// 正常
    ;

    private int iconRes;

    ToastType(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

}
