package com.hg.hollowgoods.Widget.BugView;

import android.content.Context;
import android.content.Intent;

import com.hg.hollowgoods.Constant.HGSystemConfig;

import java.util.ArrayList;

/**
 * Bug捕捉
 * Created by Hollow Goods 2017-06-01.
 */

public class Bug {

    /**
     * 模块名称
     */
    private static ArrayList<String> moduleNames;
    /**
     * 记录系统状态栏的高度
     */
    private static int mStatusBarHeight;

    /**
     * 初始化Bug
     *
     * @param context
     */
    public static void init(Context context) {
        init(context, null);
    }

    /**
     * 初始化Bug
     *
     * @param context
     * @param moduleNames
     */
    public static void init(Context context, ArrayList<String> moduleNames) {

        if (HGSystemConfig.IS_DEBUG_MODEL) {
            Bug.moduleNames = moduleNames;
            if (Bug.moduleNames == null) {
                Bug.moduleNames = new ArrayList<String>() {
                    {
                        add("模块1");
                        add("模块2");
                        add("模块3");
                        add("模块4");
                        add("模块5");
                        add("模块6");
                        add("模块7");
                        add("模块8");
                        add("模块9");
                        add("模块10");
                    }
                };
            }

            context.startService(new Intent(context, FloatBugViewService.class));
        }
    }

    /**
     * 销毁Bug
     *
     * @param context
     */
    public void destroyBugCatcher(Context context) {

        if (HGSystemConfig.IS_DEBUG_MODEL) {
            context.stopService(new Intent(context, FloatBugViewService.class));
        }
    }

    /**
     * 获取模块名称
     *
     * @return
     */
    public static ArrayList<String> getModuleNames() {
        return moduleNames;
    }

}
