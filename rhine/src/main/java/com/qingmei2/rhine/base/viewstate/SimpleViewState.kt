package com.qingmei2.rhine.base.viewstate

sealed class SimpleViewState<out T> : IViewState {

    companion object {
        fun <T> result(result: T): SimpleViewState<T> = Result(result)
        fun <T> idle(): SimpleViewState<T> = Idle
        fun <T> loading(): SimpleViewState<T> = Loading
        fun <T> error(error: Throwable): SimpleViewState<T> = Error(error)
    }

    object Idle : SimpleViewState<Nothing>()
    object Loading : SimpleViewState<Nothing>()
    data class Error(val error: Throwable) : SimpleViewState<Nothing>()
    data class Result<out T>(val result: T) : SimpleViewState<T>()
}