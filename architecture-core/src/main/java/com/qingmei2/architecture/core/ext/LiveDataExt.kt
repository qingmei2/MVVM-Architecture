package com.qingmei2.architecture.core.ext

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@AnyThread
inline fun <reified T> MutableLiveData<T>.postNext(map: (T) -> T) {
    postValue(map(verifyLiveDataNotEmpty()))
}

@MainThread
inline fun <reified T> MutableLiveData<T>.setNext(map: (T) -> T) {
    value = map(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> LiveData<T>.verifyLiveDataNotEmpty(): T {
    return value
            ?: throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
}