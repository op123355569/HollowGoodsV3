package com.hg.hollowgoods.Service.Time;

import android.content.Context;

import com.hg.hollowgoods.Application.BaseApplication;

/**
 * @ClassName:
 * @Description:
 * @author: HollowGoods
 * @date: 2018年11月07日
 */
public class TimeThread extends Thread {

    private Context context;

    public TimeThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        while (true) {
            BaseApplication baseApplication = BaseApplication.create();

            if (baseApplication.getNowTime() != 0l) {
                baseApplication.setNowTime(baseApplication.getNowTime() + 1000l);
                baseApplication.setCountFlag(baseApplication.getCountFlag() + 1);
                
                if (baseApplication.getCountFlag() == baseApplication.getTestSystemTime()) {
                    TimeService.start(context);
                    baseApplication.setCountFlag(0);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

}
