package com.qingmei2.sample.base

import com.qingmei2.architecture.core.base.viewstate.IViewState

sealed class Results<out T> : IViewState {

    companion object {
        fun <T> success(result: T): Results<T> = Success(result)
        fun <T> idle(): Results<T> = Idle
        fun <T> loading(): Results<T> = Loading
        fun <T> failure(error: Throwable): Results<T> = Failure(error)
    }

    object Idle : Results<Nothing>()
    object Loading : Results<Nothing>()
    data class Failure(val error: Throwable) : Results<Nothing>()
    data class Success<out T>(val data: T) : Results<T>()
}