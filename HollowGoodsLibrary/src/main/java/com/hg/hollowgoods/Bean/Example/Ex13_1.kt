package com.hg.hollowgoods.Bean.Example

import java.util.*

/**
 * 示例13-1
 * Created by Hollow Goods 2018-03-22.
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
        fun initData(): List<Ex13_1> {

            val data = ArrayList<Ex13_1>()
            var i = 1

            data.add(Ex13_1(i++, "", "Item 1"))
            data.add(Ex13_1(i++, "", "Item 2"))
            data.add(Ex13_1(i++, "", "Item 3"))
            data.add(Ex13_1(i++, "", "Item 4"))
            data.add(Ex13_1(i++, "", "Item 5"))
            data.add(Ex13_1(i++, "", "Item 6"))
            data.add(Ex13_1(i++, "", "Item 7"))
            data.add(Ex13_1(i++, "", "Item 8"))

            return data
        }

    }

}