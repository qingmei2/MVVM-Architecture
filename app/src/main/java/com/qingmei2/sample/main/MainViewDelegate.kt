package com.qingmei2.sample.main

import android.view.MenuItem
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.sample.R
import com.qingmei2.sample.main.profile.ProfileFragment

class MainViewDelegate(
        val search: MainViewModel,
        val profileView: ProfileFragment,
        val navigator: MainNavigator
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(search) {
        query.value = new
    }

    fun onNavSelectChanged(menu: MenuItem) = with(menu) {
        when (menu.itemId) {
            R.id.nav_profile -> {

            }
        }
    }
}