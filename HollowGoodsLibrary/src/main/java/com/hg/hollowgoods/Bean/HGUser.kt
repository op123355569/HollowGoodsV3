package com.hg.hollowgoods.Bean

import org.xutils.db.annotation.Column
import java.io.Serializable

/**
 * 用户
 * Created by HG on 2018-03-22.
 */
class HGUser : Serializable {

    /**
     * UserId
     */
    @Column(name = "tableId", isId = true)
    var tableId: Long? = null
    /**
     * 用户名
     */
    @Column(name = "username")
    var username: String? = null
    /**
     * 密码
     */
    @Column(name = "password")
    var password: String? = null

}