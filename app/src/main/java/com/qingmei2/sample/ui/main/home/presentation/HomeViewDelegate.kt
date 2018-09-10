package com.qingmei2.sample.ui.main.home.presentation

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class HomeViewDelegate(
        val homeViewModel: HomeViewModel
) : IViewDelegate {

    fun onQuerySubmit(new: String) = with(homeViewModel) {
        query.value = new
    }
}