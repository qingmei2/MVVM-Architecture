package com.qingmei2.architecture.core.ext

import androidx.lifecycle.MutableLiveData

inline fun <reified T> MutableLiveData<T>.scanNext(map: (T) -> T) {
    val oldValue = value
            ?: throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
    postValue(map(oldValue))
}