package com.hg.hollowgoods.Adapter.ViewPagerAdapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * FragmentViewPager适配器
 * Created by HG on 2018-03-22.
 */
class FragmentViewPagerAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    private var fragments: ArrayList<Fragment>? = null

    init {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment? {
        return if (fragments == null) null else fragments!!.get(position)
    }

    override fun getCount(): Int {
        return if (fragments == null) 0 else fragments!!.size
    }

}