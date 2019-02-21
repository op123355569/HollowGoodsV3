package com.hg.hollowgoods.DB

import com.hg.hollowgoods.Bean.User
import com.hg.hollowgoods.Util.XUtils.XDBUtils
import org.xutils.ex.DbException

/**
 * 本地数据库帮助类-用户
 * Created by HG on 2018-03-21.
 */
class ExampleUserDBHelper {

    fun find(): User? {

        var result: User? = null

        try {
            result = XDBUtils.getDbManager().findFirst(User::class.java)
        } catch (e: DbException) {

        }

        return result
    }

    fun delete() {

        try {
            XDBUtils.getDbManager().delete(User::class.java)
        } catch (e: DbException) {

        }
    }

    fun update(user: User) {
        delete()
        save(user)
    }

    private fun save(user: User) {

        try {
            XDBUtils.getDbManager().save(user)
        } catch (e: DbException) {

        }
    }

}