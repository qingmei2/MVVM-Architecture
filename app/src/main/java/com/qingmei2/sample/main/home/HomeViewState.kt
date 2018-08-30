package com.qingmei2.sample.main.home

import com.qingmei2.rhine.base.viewstate.IViewState

sealed class HomeViewState<out T> : IViewState {

    companion object {
        fun <T> result(result: T): HomeViewState<T> = Result(result)
        fun <T> idle(): HomeViewState<T> = Idle
        fun <T> loading(): HomeViewState<T> = Loading
        fun <T> error(error: Throwable): HomeViewState<T> = Error(error)
    }

    object Idle : HomeViewState<Nothing>()
    object Loading : HomeViewState<Nothing>()
    data class Error(val error: Throwable) : HomeViewState<Nothing>()
    data class Result<out T>(val result: T) : HomeViewState<T>()
}