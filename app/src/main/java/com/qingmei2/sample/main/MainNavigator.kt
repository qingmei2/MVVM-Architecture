package com.qingmei2.sample.main

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.qingmei2.sample.R
import com.qingmei2.sample.main.profile.ProfileFragment

class MainNavigator(
        private val context: MainActivity,
        private val profileFragment: ProfileFragment
) {

    init {
        addFragments(R.id.clContainer, arrayListOf(profileFragment))
    }

    fun addFragments(@IdRes containerId: Int, fragments: List<Fragment>, defaultShow: Int = 0) {
        val fm = context.supportFragmentManager
        fm.beginTransaction().apply {
            fragments.forEach { add(containerId, it) }
            show(fragments[defaultShow])
            commitAllowingStateLoss()
        }
    }

    fun showFragment(fragment: Fragment) {
        val fm = context.supportFragmentManager
        fm.beginTransaction().apply {
            fm.fragments.forEach { hide(it) }
            show(fragment)
            commitAllowingStateLoss()
        }
    }

    fun switchFragment(menu: MenuItem) {
        when (menu.itemId) {
            R.id.nav_profile -> {
                showFragment(profileFragment)
            }
        }
    }
}