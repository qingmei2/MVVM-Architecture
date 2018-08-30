package com.qingmei2.sample.main

import android.view.MenuItem
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class MainViewDelegate(
        val search: MainViewModel,
        val navigator: MainNavigator
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(search) {
        query.value = new
    }

    fun onNavSelectChanged(menu: MenuItem) = navigator.switchFragment(menu)
}