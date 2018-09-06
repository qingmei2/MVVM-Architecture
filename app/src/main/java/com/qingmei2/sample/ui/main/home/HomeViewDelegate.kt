package com.qingmei2.sample.ui.main.home

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class HomeViewDelegate(
        val search: HomeViewModel
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(search) {
        query.value = new
    }
}