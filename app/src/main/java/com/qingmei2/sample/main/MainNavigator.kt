package com.qingmei2.sample.main

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

class MainNavigator(
        private val context: FragmentActivity
) {

    fun addFragments(@IdRes containerId: Int, fragments: List<Fragment>, defaultShow: Int = 0) {
        val fm = context.supportFragmentManager
        fm.beginTransaction().apply {
            fragments.forEach { add(containerId, it) }
            show(fragments[defaultShow])
            commitAllowingStateLoss()
        }
    }

    fun switchFragment(fragment: Fragment) {
        val fm = context.supportFragmentManager
        fm.beginTransaction().apply {
            fm.fragments.forEach { hide(it) }
            show(fragment)
            commitAllowingStateLoss()
        }
    }
}