package com.qingmei2.sample.base

import com.qingmei2.rhine.base.viewstate.IViewState

sealed class Result<out T> : IViewState {

    companion object {
        fun <T> success(result: T): Result<T> = Success(result)
        fun <T> idle(): Result<T> = Idle
        fun <T> loading(): Result<T> = Loading
        fun <T> failure(error: Throwable): Result<T> = Failure(error)
    }

    object Idle : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Failure(val error: Throwable) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
}