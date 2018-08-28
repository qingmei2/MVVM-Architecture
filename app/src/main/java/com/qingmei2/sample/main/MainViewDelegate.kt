package com.qingmei2.sample.main

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class MainViewDelegate(
        val search: MainViewModel
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(search) {
        query.value = new
    }
}