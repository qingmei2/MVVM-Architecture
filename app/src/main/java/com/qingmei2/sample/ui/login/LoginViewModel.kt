package com.qingmei2.sample.ui.login

import androidx.lifecycle.*
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.scanNext
import com.qingmei2.sample.entity.Resource
import com.qingmei2.sample.http.Errors
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class LoginViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<LoginViewState> = MutableLiveData(LoginViewState.initial())

    val stateLiveData: LiveData<LoginViewState> = _stateLiveData

    init {
        viewModelScope.launch {
            val autoLoginEvent = repo.fetchAutoLogin()
            _stateLiveData.scanNext { state ->
                state.copy(
                        isLoading = false,
                        throwable = null,
                        autoLoginEvent = autoLoginEvent,
                        loginInfo = null
                )
            }
        }
    }

    fun onAutoLoginEventUsed() {
        _stateLiveData.scanNext { state ->
            state.copy(isLoading = false, throwable = null, useAutoLoginEvent = false, loginInfo = null)
        }
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> _stateLiveData.scanNext { state ->
                state.copy(isLoading = false, throwable = Errors.EmptyInputError,
                        loginInfo = null, autoLoginEvent = null)
            }
            false -> viewModelScope.launch {
                _stateLiveData.scanNext {
                    it.copy(isLoading = true, throwable = null, loginInfo = null)
                }
                when (val result = repo.login(username, password)) {
                    is Resource.DataError -> _stateLiveData.scanNext {
                        it.copy(isLoading = false, throwable = result.error, loginInfo = null)
                    }
                    is Resource.Success -> _stateLiveData.scanNext {
                        it.copy(isLoading = false, throwable = null, loginInfo = result.data)
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