package com.hg.hollowgoods.Bean.EventBus;

public class HGEventActionCode {

    /**** NFC扫描结果 ****/
    public static final int NFC_SCAN_RESULT = -1000;
    /**** Tag扫描结果 ****/
    public static final int TAG_SCAN_RESULT = -1001;
    /**** 删除图片 ****/
    public static final int REMOVE_IMAGE = -1002;
    /**** 文件选择 ****/
    public static final int FILE_SELECTOR = -1003;
    /**** 二维码扫描结果 ****/
    public static final int QR_CODE_SCAN_RESULT = -1004;
    /**** 网络状态——断网 ****/
    public static final int NETWORK_STATUS_BREAK = -1005;
    /**** 网络状态——联网 ****/
    public static final int NETWORK_STATUS_LINK = -1006;

}
