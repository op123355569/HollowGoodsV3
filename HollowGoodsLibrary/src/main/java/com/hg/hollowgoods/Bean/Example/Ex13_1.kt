package com.hg.hollowgoods.Bean.Example

import java.util.*

/**
 * 示例13-1
 * Created by HG on 2018-03-22.
 */
class Ex13_1 {

    var postition: Int = 0
    var url: String? = null
    var name: String? = null

    constructor(postition: Int, url: String, name: String) {
        this.postition = postition
        this.url = url
        this.name = name
    }

    companion object {

        @JvmStatic
        fun initDatas(): List<Ex13_1> {

            val datas = ArrayList<Ex13_1>()
            var i = 1

            datas.add(Ex13_1(i++, "", "Item 1"))
            datas.add(Ex13_1(i++, "", "Item 2"))
            datas.add(Ex13_1(i++, "", "Item 3"))
            datas.add(Ex13_1(i++, "", "Item 4"))
            datas.add(Ex13_1(i++, "", "Item 5"))
            datas.add(Ex13_1(i++, "", "Item 6"))
            datas.add(Ex13_1(i++, "", "Item 7"))
            datas.add(Ex13_1(i++, "", "Item 8"))

            return datas
        }

    }

}