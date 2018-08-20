package com.qingmei2.sample

import android.databinding.ObservableField
import com.qingmei2.rhine.base.BaseRhineViewModel

class MainRhineViewModel : BaseRhineViewModel() {

    val showContent: ObservableField<String> = ObservableField()

    fun fetchUserInfo() {
        serviceManager.userInfoService
                .getUserInfo("qingmei2")
                .map { it.toString() }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe { showContent::set }
    }
}