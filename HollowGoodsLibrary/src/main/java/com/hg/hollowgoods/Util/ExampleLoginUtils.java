package com.hg.hollowgoods.Util;

import com.hg.hollowgoods.Application.ExampleApplication;
import com.hg.hollowgoods.Bean.HGUser;
import com.hg.hollowgoods.DB.ExampleUserDBHelper;

/**
 * 登录工具类示例
 * Created by Hollow Goods on 2019-07-11.
 */
public class ExampleLoginUtils {

    public static boolean isLogin() {
        return ExampleApplication.createApplication().getUser() != null;
    }

    public static void initUser() {
        ExampleApplication.createApplication().setUser(new ExampleUserDBHelper().findFirst(HGUser.class));
    }

    public static void updateUser(HGUser user) {
        new ExampleUserDBHelper().updateAll(HGUser.class, user);
        ExampleApplication.createApplication().setUser(user);
    }

    public static HGUser getUser() {

        if (ExampleApplication.createApplication().getUser() != null) {
            return ExampleApplication.createApplication().getUser();
        }

        return new HGUser();
    }

    public static void destroyUser() {
        ExampleApplication.createApplication().setUser(null);
        new ExampleUserDBHelper().deleteAll(HGUser.class);
    }

}
