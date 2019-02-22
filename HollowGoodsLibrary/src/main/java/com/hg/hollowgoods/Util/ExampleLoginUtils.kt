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
        val application: ExampleApplication = ExampleApplication.create()
        return application.user != null
    }

    @JvmStatic
    fun initUser() {
        val application: ExampleApplication = ExampleApplication.create()
        application.user = ExampleUserDBHelper().find()
    }

    @JvmStatic
    fun updateUser(user: HGUser) {
        val application: ExampleApplication = ExampleApplication.create()
        ExampleUserDBHelper().update(user)
        application.user = user
    }

    @JvmStatic
    fun getUser(): HGUser? {

        val application: ExampleApplication = ExampleApplication.create()

        return if (application.user != null)
            application.user
        else
            HGUser()
    }

    @JvmStatic
    fun destroyUser() {
        val application: ExampleApplication = ExampleApplication.create()
        application.user = null
        ExampleUserDBHelper().delete()
    }

}