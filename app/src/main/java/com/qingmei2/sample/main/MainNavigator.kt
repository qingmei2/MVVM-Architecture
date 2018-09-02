package com.qingmei2.sample.main

import android.support.design.widget.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainNavigator(val navHostFragment: NavHostFragment,
                    private val navBottomView: BottomNavigationView) {

    init {
        navHostFragment.navController.apply {
            NavigationUI.setupWithNavController(navBottomView, this)
        }
    }
}