package com.qingmei2.sample.main

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class MainViewDelegate(
        val viewModel: MainViewModel
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(viewModel) {
        query.value = new
    }
}