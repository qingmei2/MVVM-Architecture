package com.qingmei2.sample.main.presentation

import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.http.entity.UserInfo

class MainViewModel : RhineViewModel() {

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    fun fetchUserInfo(username: String) {
        serviceManager.userinfoApi
                .fetchUserInfo(username)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .bindLifecycle(this)
                .subscribe {
                    userInfo.value = it
                }
    }
}