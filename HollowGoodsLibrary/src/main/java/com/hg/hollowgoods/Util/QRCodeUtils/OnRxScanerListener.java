package com.hg.hollowgoods.Util.QRCodeUtils;

import com.google.zxing.Result;

/**
 */

public interface OnRxScanerListener {
    void onSuccess(String type, Result result);

    void onFail(String type, String message);
}
