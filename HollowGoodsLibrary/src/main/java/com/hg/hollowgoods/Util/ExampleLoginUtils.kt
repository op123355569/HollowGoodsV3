package com.hg.hollowgoods.Util

import com.hg.hollowgoods.Application.ExampleApplication
import com.hg.hollowgoods.Bean.User
import com.hg.hollowgoods.DB.UserDBHelper

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
        application.user = UserDBHelper().find()
    }

    @JvmStatic
    fun updateUser(user: User) {
        val application: ExampleApplication = ExampleApplication.create()
        UserDBHelper().update(user)
        application.user = user
    }

    @JvmStatic
    fun getUser(): User? {

        val application: ExampleApplication = ExampleApplication.create()

        return if (application.user != null)
            application.user
        else
            User()
    }

    @JvmStatic
    fun destroyUser() {
        val application: ExampleApplication = ExampleApplication.create()
        application.user = null
        UserDBHelper().delete()
    }

}