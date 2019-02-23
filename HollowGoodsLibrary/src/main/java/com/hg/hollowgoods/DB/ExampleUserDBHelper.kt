package com.hg.hollowgoods.DB

import com.hg.hollowgoods.Bean.HGUser
import com.hg.hollowgoods.Util.XUtils.XDBUtils
import org.xutils.ex.DbException

/**
 * 本地数据库帮助类-用户
 * Created by HG on 2018-03-21.
 */
class ExampleUserDBHelper {

    fun find(): HGUser? {

        var result: HGUser? = null

        try {
            result = XDBUtils.getDbManager().findFirst(HGUser::class.java)
        } catch (e: DbException) {

        }

        return result
    }

    fun delete() {

        try {
            XDBUtils.getDbManager().delete(HGUser::class.java)
        } catch (e: DbException) {

        }
    }

    fun update(user: HGUser) {
        delete()
        save(user)
    }

    private fun save(user: HGUser) {

        try {
            XDBUtils.getDbManager().save(user)
        } catch (e: DbException) {

        }
    }

}