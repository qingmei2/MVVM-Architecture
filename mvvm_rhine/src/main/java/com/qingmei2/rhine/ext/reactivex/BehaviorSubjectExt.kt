package com.qingmei2.rhine.ext.reactivex

import io.reactivex.subjects.BehaviorSubject

inline fun <reified T> BehaviorSubject<T>.onNextWithLast(map: (T) -> T) {
    val oldValue: T? = value
    if (oldValue != null) {
        onNext(map(oldValue))
    } else {
        throw NullPointerException("BehaviorSubject<${T::class.java}> not contain value.")
    }
}