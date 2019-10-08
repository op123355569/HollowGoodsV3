package com.hg.hollowgoods.Util.XUtils;

import com.hg.hollowgoods.Constant.HGSystemConfig;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * XUtils数据库工具类
 * Created by Hollow Goods on unknown.
 */
public class XDBUtils {

    /**
     * 数据库版本更新监听
     **/
    private static MyDbUpgradeListener myDbUpgradeListener = null;

    /**
     * 获取数据库管理器
     *
     * @return
     */
    public static DbManager getDbManager() {

        if (myDbUpgradeListener == null) {
            myDbUpgradeListener = new MyDbUpgradeListener();
        }

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName(HGSystemConfig.DATABASE_NAME)// 创建数据库的名称
                .setDbVersion(HGSystemConfig.DB_VERSION)// 数据库版本号
                .setDbUpgradeListener(myDbUpgradeListener);// 数据库更新操作

        return x.getDb(daoConfig);
    }

    /**
     * 数据库版本更新监听
     *
     * Created by Hollow Goods on unknown.
     */
    private static class MyDbUpgradeListener implements DbManager.DbUpgradeListener {

        /**
         * 版本更新操作
         */
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
            // TODO: ...
            // db.addColumn(...);
            // db.dropTable(...);
            // ...
        }
    }

}
