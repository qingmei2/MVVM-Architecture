package com.qingmei2.sample.ui.login.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.base.viewstate.SimpleViewState
import com.qingmei2.rhine.ext.arrow.whenNotNull
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.LoginUser
import com.qingmei2.sample.ui.login.data.LoginDataSourceRepository

@SuppressWarnings("checkResult")
class LoginViewModel(
        private val repo: LoginDataSourceRepository
) : BaseViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val userInfo: MutableLiveData<LoginUser> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        repo.prefsUser()
                .bindLifecycle(this)
                .subscribe { either ->
                    either.fold({ error ->
                        applyState(error = error.some())
                    }, { entity ->
                        applyState(username = entity.username.some(), password = entity.password.some())
                    })
                }
    }

    fun login() {
        when (username.value != null && password.value != null) {
            false -> applyState(isLoading = false, error = Errors.EmptyInputError.some())
            true -> repo
                    .login(username.value!!, password.value!!)
                    .map { either ->
                        either.fold({
                            SimpleViewState.error<LoginUser>(it)
                        }, {
                            SimpleViewState.result(it)
                        })
                    }
                    .startWith(SimpleViewState.loading())
                    .startWith(SimpleViewState.idle())
                    .onErrorReturn { it -> SimpleViewState.error(it) }
                    .bindLifecycle(this)
                    .subscribe { state ->
                        when (state) {
                            is SimpleViewState.Loading -> applyState(isLoading = true)
                            is SimpleViewState.Idle -> applyState()
                            is SimpleViewState.Error -> applyState(error = state.error.some())
                            is SimpleViewState.Result -> applyState(user = state.result.some())
                        }
                    }
        }
    }

    private fun applyState(isLoading: Boolean = false,
                           user: Option<LoginUser> = none(),
                           error: Option<Throwable> = none(),
                           username: Option<String> = none(),
                           password: Option<String> = none()) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        this.userInfo.postValue(user.orNull())

        username.whenNotNull { this.username.postValue(it) }
        password.whenNotNull { this.password.postValue(it) }
    }
}