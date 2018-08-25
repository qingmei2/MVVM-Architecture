package com.qingmei2.sample.main.presentation

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class MainViewDelegate(
        val viewModel: MainViewModel
) : IViewDelegate {

    fun fetchUserInfo(username: String) = viewModel.fetchUserInfo(username)
}