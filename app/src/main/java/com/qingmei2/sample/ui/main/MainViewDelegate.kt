package com.qingmei2.sample.ui.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qingmei2.rhine.base.viewdelegate.BaseViewDelegate
import com.qingmei2.sample.R

class MainViewDelegate(
        val viewModel: MainViewModel,
        private val navigator: MainNavigator,
        val fragments: List<Fragment>,
        val fragmentManager: FragmentManager,
        private val navigationView: BottomNavigationView,
        private val viewPager: ViewPager
) : BaseViewDelegate() {

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