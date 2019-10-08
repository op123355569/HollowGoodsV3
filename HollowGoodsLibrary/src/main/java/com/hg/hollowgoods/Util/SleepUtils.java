package com.hg.hollowgoods.Util;

/**
 * 进程休息工具类
 * Created by Hollow Goods on 2019-10-08.
 */
public class SleepUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LogUtils.Log(e.getMessage());
        }
    }

}
