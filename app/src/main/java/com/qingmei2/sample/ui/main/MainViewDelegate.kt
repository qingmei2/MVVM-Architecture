package com.qingmei2.sample.ui.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.sample.R

class MainViewDelegate(
        val viewModel: MainViewModel,
        private val navigator: MainNavigator,
        val fragments: List<Fragment>,
        val fragmentManager: FragmentManager,
        private val navigationView: BottomNavigationView,
        private val viewPager: ViewPager
) : IViewDelegate {

    fun onPageSelectChanged(index: Int) {
        for (position in 0..index) {
            navigationView.menu.getItem(position).isChecked = index == position
        }
    }

    fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                viewPager.currentItem = 2
            }
        }
    }
}