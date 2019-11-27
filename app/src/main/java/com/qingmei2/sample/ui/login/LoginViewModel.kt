package com.qingmei2.sample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.reactivex.onNextWithLast
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.http.globalHandleError
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@SuppressWarnings("checkResult")
class LoginViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val mViewStateSubject: BehaviorSubject<LoginViewState> =
            BehaviorSubject.createDefault(LoginViewState.initial())

    init {
        repo.fetchAutoLogin().singleOrError()
                .onErrorReturn { AutoLoginEvent(false, "", "") }
                .autoDisposable(this)
                .subscribe { event ->
                    mViewStateSubject.onNextWithLast { state ->
                        state.copy(isLoading = false, throwable = null, autoLoginEvent = event, loginInfo = null)
                    }
                }
    }

    fun observeViewState(): Observable<LoginViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun onAutoLoginEventUsed() {
        mViewStateSubject.onNextWithLast { state ->
            state.copy(isLoading = false, throwable = null, useAutoLoginEvent = false, loginInfo = null)
        }
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> mViewStateSubject.onNextWithLast { state ->
                state.copy(isLoading = false, throwable = Errors.EmptyInputError,
                        loginInfo = null, autoLoginEvent = null)
            }
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
                            is Result.Loading -> mViewStateSubject.onNextWithLast {
                                it.copy(isLoading = true, throwable = null, loginInfo = null)
                            }
                            is Result.Idle -> mViewStateSubject.onNextWithLast {
                                it.copy(isLoading = false, throwable = null, loginInfo = null)
                            }
                            is Result.Failure -> mViewStateSubject.onNextWithLast {
                                it.copy(isLoading = true, throwable = state.error, loginInfo = null)
                            }
                            is Result.Success -> mViewStateSubject.onNextWithLast {
                                it.copy(isLoading = true, throwable = null, loginInfo = state.data)
                            }
                        }
                    }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        private val repo: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LoginViewModel(repo) as T
}