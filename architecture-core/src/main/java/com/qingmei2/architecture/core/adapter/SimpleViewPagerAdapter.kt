package com.qingmei2.architecture.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size
}