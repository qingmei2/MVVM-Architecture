package com.qingmei2.sample.login

import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel

class LoginViewModel : RhineViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()


}