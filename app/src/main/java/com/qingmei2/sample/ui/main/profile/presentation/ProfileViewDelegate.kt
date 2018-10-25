package com.qingmei2.sample.ui.main.profile.presentation

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.sample.utils.toast

class ProfileViewDelegate(val viewModel: ProfileViewModel) : IViewDelegate {

    fun edit() = toast { "comming soon..." }
}