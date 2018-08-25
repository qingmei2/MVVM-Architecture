package com.qingmei2.sample

import android.databinding.ObservableField
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle

class MainViewModel : RhineBaseViewModel() {

    val showContent: ObservableField<String> = ObservableField()

    fun fetchUserInfo() {
        serviceManager.userInfoService
                .getUserInfo("qingmei2")
                .map { it.toString() }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .bindLifecycle(this)
                .subscribe { showContent::set }
    }
}