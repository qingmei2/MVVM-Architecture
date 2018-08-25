package com.qingmei2.sample.main

import com.qingmei2.rhine.base.IScreenDelegate

class MainScreenDelegate(
        val viewModel: MainViewModel
) : IScreenDelegate {

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() = viewModel.fetchUserInfo()
}