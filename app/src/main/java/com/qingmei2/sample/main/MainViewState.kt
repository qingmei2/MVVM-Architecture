package com.qingmei2.sample.main

import com.qingmei2.rhine.base.viewstate.IViewState

sealed class MainViewState<out T> : IViewState {

    companion object {
        fun <T> result(volumeList: List<T>): MainViewState<T> = Result(volumeList)
        fun <T> idle(): MainViewState<T> = Idle
        fun <T> loading(): MainViewState<T> = Loading
        fun <T> error(error: Throwable): MainViewState<T> = Error(error)
    }

    object Idle : MainViewState<Nothing>()
    object Loading : MainViewState<Nothing>()
    data class Error(val error: Throwable) : MainViewState<Nothing>()
    data class Result<out T>(val results: List<T>) : MainViewState<T>()
}