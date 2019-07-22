package com.qingmei2.rhine.ext.reactivex

import io.reactivex.subjects.BehaviorSubject

inline fun <reified T> BehaviorSubject<T>.copyMap(map: (T) -> T) {
    val oldValue: T? = value
    if (oldValue != null) {
        onNext(map(oldValue))
    } else {
        throw NullPointerException("BehaviorSubject<${T::class.java}> not contain value.")
    }
}

inline fun <reified T> BehaviorSubject<T>.copyFlatMap(map: (T) -> List<T>) {
    val oldValue: T? = value
    if (oldValue != null) {
        map(oldValue).forEach { onNext(it) }
    } else {
        throw NullPointerException("BehaviorSubject<${T::class.java}> not contain value.")
    }
}