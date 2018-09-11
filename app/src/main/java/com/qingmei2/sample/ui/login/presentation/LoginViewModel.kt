package com.qingmei2.sample.ui.login.presentation

import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewstate.SimpleViewState
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.http.entity.LoginUser
import com.qingmei2.sample.ui.login.data.LoginDataSourceRepository

class LoginViewModel(
        private val repo: LoginDataSourceRepository
) : BaseViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val userInfo: MutableLiveData<LoginUser> = MutableLiveData()

    fun login() = repo
            .login(username.value ?: "",
                    password.value ?: "")
            .map { SimpleViewState.result(it) }
            .startWith(SimpleViewState.loading())
            .startWith(SimpleViewState.idle())
            .onErrorReturn { it -> SimpleViewState.error(it) }
            .bindLifecycle(this)
            .subscribe { state ->
                when (state) {
                    is SimpleViewState.Loading -> applyState(isLoading = true)
                    is SimpleViewState.Idle -> applyState(isLoading = false)
                    is SimpleViewState.Error -> applyState(isLoading = false, error = state.error)
                    is SimpleViewState.Result -> applyState(isLoading = false, user = state.result)
                }
            }

    private fun applyState(isLoading: Boolean, user: LoginUser? = null, error: Throwable? = null) {
        this.loading.postValue(isLoading)
        this.userInfo.postValue(user)
        this.error.postValue(error)
    }
}