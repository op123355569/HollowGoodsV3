package com.hg.hollowgoods.Application;

import android.app.Application;

/**
 * Application构造器
 * Created by Hollow Goods on 2019-09-04.
 */
public class ApplicationBuilder {

    private static Application instance = null;

    public static <T extends IBaseApplication> T create() {
        if (instance == null) {
            instance = new ExampleApplication();
        }
        return (T) instance;
    }

    public static void setApplication(Application application) {
        instance = application;
    }

}
