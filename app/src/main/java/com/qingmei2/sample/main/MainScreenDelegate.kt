package com.qingmei2.sample.main

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class MainScreenDelegate(
        val viewModel: MainViewModel
) : IViewDelegate {

    fun fetchUserInfo() = viewModel.fetchUserInfo()
}