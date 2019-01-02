package com.qingmei2.rhine.ext.reactivex

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*

fun <T> Flowable<T>.toLiveData(): LiveData<T> =
        LiveDataReactiveStreams.fromPublisher(this)

fun <T> Observable<T>.toLiveData(
        backPressureStrategy: BackpressureStrategy = BackpressureStrategy.LATEST
): LiveData<T> =
        this.toFlowable(backPressureStrategy).toLiveData()

fun <T> Single<T>.toLiveData(): LiveData<T> =
        this.toFlowable().toLiveData()

fun <T> Maybe<T>.toLiveData(): LiveData<T> =
        this.toFlowable().toLiveData()