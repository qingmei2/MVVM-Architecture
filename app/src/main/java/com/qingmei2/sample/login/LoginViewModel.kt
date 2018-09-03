package com.qingmei2.sample.login

import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.http.entity.LoginUser

class LoginViewModel : RhineViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val userInfo: MutableLiveData<LoginUser> = MutableLiveData()

    fun login() = serviceManager
            .loginService
            .login(username.value ?: "",
                    password.value ?: "")
            .map { LoginViewState.result(it) }
            .subscribeOn(schedulers.io())
            .startWith(LoginViewState.loading())
            .startWith(LoginViewState.idle())
            .bindLifecycle(this)
            .subscribe { state ->
                when (state) {
                    is LoginViewState.Loading -> applyState(isLoading = true)
                    is LoginViewState.Idle -> applyState(isLoading = false)
                    is LoginViewState.Error -> applyState(isLoading = false, error = state.error)
                    is LoginViewState.Result -> applyState(isLoading = false, user = state.result)
                }
            }

    private fun applyState(isLoading: Boolean, user: LoginUser? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.userInfo.postValue(user)
        this.error.postValue(error)
    }

}