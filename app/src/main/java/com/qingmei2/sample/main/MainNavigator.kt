package com.qingmei2.sample.main

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.qingmei2.sample.R
import com.qingmei2.sample.main.home.HomeFragment
import com.qingmei2.sample.main.profile.ProfileFragment
import com.qingmei2.sample.main.task.TaskFragment

class MainNavigator(
        private val context: MainActivity,
        private val homeFragment: HomeFragment,
        private val taskFragment: TaskFragment,
        private val profileFragment: ProfileFragment
) {

    init {
        addFragments(R.id.clContainer, arrayListOf(homeFragment, taskFragment, profileFragment))
    }

    private fun addFragments(@IdRes containerId: Int, fragments: List<Fragment>, defaultShow: Int = 0) {
        context.supportFragmentManager
                .beginTransaction()
                .apply {
                    fragments.forEachIndexed { index, fragment ->
                        add(containerId, fragment)
                        if (defaultShow != index) hide(fragment)
                    }
                }.commitAllowingStateLoss()
    }

    private fun showFragment(fragment: Fragment) {
        val fm = context.supportFragmentManager
        fm.beginTransaction().apply {
            fm.fragments.forEach { hide(it) }
            show(fragment)
        }.commitAllowingStateLoss()
    }

    fun switchFragment(menu: MenuItem) {
        when (menu.itemId) {
            R.id.nav_home -> showFragment(homeFragment)
            R.id.nav_task -> showFragment(taskFragment)
            R.id.nav_profile -> showFragment(profileFragment)
        }
    }
}