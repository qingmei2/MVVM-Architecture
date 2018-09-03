package com.qingmei2.sample.login

import com.qingmei2.rhine.base.viewstate.IViewState

sealed class LoginViewState<out T> : IViewState {

    companion object {
        fun <T> result(result: T): LoginViewState<T> = Result(result)
        fun <T> idle(): LoginViewState<T> = Idle
        fun <T> loading(): LoginViewState<T> = Loading
        fun <T> error(error: Throwable): LoginViewState<T> = Error(error)
    }

    object Idle : LoginViewState<Nothing>()
    object Loading : LoginViewState<Nothing>()
    data class Error(val error: Throwable) : LoginViewState<Nothing>()
    data class Result<out T>(val result: T) : LoginViewState<T>()
}