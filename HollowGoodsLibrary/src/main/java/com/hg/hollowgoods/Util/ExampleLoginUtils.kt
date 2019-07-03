package com.hg.hollowgoods.Util

import com.hg.hollowgoods.Application.ExampleApplication
import com.hg.hollowgoods.Bean.HGUser
import com.hg.hollowgoods.DB.ExampleUserDBHelper

/**
 * 示例登录工具类
 * Created by HG on 2018-03-22.
 */
object ExampleLoginUtils {

    @JvmStatic
    fun isLogin(): Boolean {
        return ExampleApplication.createApplication().user != null
    }

    @JvmStatic
    fun initUser() {
        ExampleApplication.createApplication().user = ExampleUserDBHelper().find()
    }

    @JvmStatic
    fun updateUser(user: HGUser) {
        ExampleUserDBHelper().update(user)
        ExampleApplication.createApplication().user = user
    }

    @JvmStatic
    fun getUser(): HGUser? {

        return if (ExampleApplication.createApplication().user != null)
            ExampleApplication.createApplication().user
        else
            HGUser()
    }

    @JvmStatic
    fun destroyUser() {
        ExampleApplication.createApplication().user = null
        ExampleUserDBHelper().delete()
    }

}