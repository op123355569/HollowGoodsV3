package com.hg.hollowgoods.Bean.Example

import com.hg.hollowgoods.Bean.CommonBean.CommonBean

/**
 * 示例12
 * Created by Hollow Goods 2018-03-22.
 */
class Ex12(itemType: Int, res: Int, txt: String?) : CommonBean(itemType) {

    var res: Int = 0
    var m1: String? = null

    init {
        super.setItemType(itemType)
        this.res = res
        this.m1 = txt
    }

    constructor(res: Int, txt: String?) : this(-1, res, txt) {
        this.res = res
        this.m1 = txt
    }

}