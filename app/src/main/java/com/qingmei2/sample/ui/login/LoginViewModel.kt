package com.qingmei2.sample.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import com.qingmei2.sample.base.Results
import com.qingmei2.sample.http.Errors
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class LoginViewModel @ViewModelInject constructor(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<LoginViewState> = MutableLiveData(LoginViewState.initial())

    val stateLiveData: LiveData<LoginViewState> = _stateLiveData

    init {
        viewModelScope.launch {
            val autoLoginEvent = repo.fetchAutoLogin()
            _stateLiveData.postNext { state ->
                state.copy(
                        isLoading = false,
                        throwable = null,
                        autoLoginEvent = autoLoginEvent,
                        useAutoLoginEvent = true,
                        loginInfo = null
                )
            }
        }
    }

    fun onAutoLoginEventUsed() {
        _stateLiveData.postNext { state ->
            state.copy(isLoading = false, throwable = null,
                    autoLoginEvent = null, useAutoLoginEvent = false, loginInfo = null)
        }
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, throwable = Errors.EmptyInputError,
                        loginInfo = null, autoLoginEvent = null)
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, throwable = null, loginInfo = null, autoLoginEvent = null)
                }
                when (val result = repo.login(username, password)) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = result.error, loginInfo = null, autoLoginEvent = null)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = null, loginInfo = result.data, autoLoginEvent = null)
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
