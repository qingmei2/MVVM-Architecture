package com.qingmei2.sample.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.arrow.whenNotNull
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.utils.toast
import com.uber.autodispose.autoDisposable
import io.reactivex.Single
import retrofit2.HttpException

@SuppressWarnings("checkResult")
class LoginViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val error: MutableLiveData<Option<Throwable>> = MutableLiveData()

    val loginIndicatorVisible: MutableLiveData<Boolean> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    val autoLogin: MutableLiveData<AutoLoginEvent> = MutableLiveData()

    init {
        autoLogin.toReactiveStream()
                .filter { it.autoLogin }
                .doOnNext { login(it.username, it.password) }
                .autoDisposable(this)
                .subscribe()

        error.toReactiveStream()
                .map { errorOpt ->
                    errorOpt.flatMap {
                        when (it) {
                            is Errors.EmptyInputError -> "username or password can't be null.".some()
                            is HttpException ->
                                when (it.code()) {
                                    401 -> "username or password failure.".some()
                                    else -> "network failure".some()
                                }
                            else -> none()
                        }
                    }
                }
                .autoDisposable(this)
                .subscribe { errorMsg ->
                    errorMsg.whenNotNull {
                        toast { it }
                    }
                }

        initAutoLogin()
                .autoDisposable(this)
                .subscribe()
    }

    private fun initAutoLogin(): Single<AutoLoginEvent> {
        return repo.fetchAutoLogin()
                .singleOrError()
                .onErrorReturn { AutoLoginEvent(false, "", "") }
                .doOnSuccess { event ->
                    applyState(autoLogin = event, loginIndicator = false)
                }
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> applyState(error = Errors.EmptyInputError.some())
            false -> repo
                    .login(username, password)
                    .compose(globalHandleError())
                    .map { either ->
                        either.fold({
                            Result.failure<UserInfo>(it)
                        }, {
                            Result.success(it)
                        })
                    }
                    .startWith(Result.loading())
                    .startWith(Result.idle())
                    .onErrorReturn { Result.failure(it) }
                    .autoDisposable(this)
                    .subscribe { state ->
                        when (state) {
                            is Result.Loading -> applyState(loginIndicator = true)
                            is Result.Idle -> applyState(loginIndicator = false)
                            is Result.Failure -> applyState(error = state.error.some(), loginIndicator = false)
                            is Result.Success -> applyState(user = state.data.some(), loginIndicator = false)
                        }
                    }
        }
    }

    private fun applyState(user: Option<UserInfo> = none(),
                           error: Option<Throwable> = none(),
                           loginIndicator: Boolean? = null,
                           autoLogin: AutoLoginEvent? = null) {
        this.error.postValue(error)
        this.userInfo.postValue(user.orNull())

        loginIndicator?.apply(loginIndicatorVisible::postValue)

        autoLogin?.let {
            this.autoLogin.postValue(autoLogin)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        private val repo: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LoginViewModel(repo) as T

    companion object : SingletonHolderSingleArg<LoginViewModelFactory, LoginRepository>(::LoginViewModelFactory)
}