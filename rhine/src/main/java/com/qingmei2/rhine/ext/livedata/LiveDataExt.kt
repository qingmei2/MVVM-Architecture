package com.qingmei2.rhine.ext.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.MainThreadDisposable

fun <T> LiveData<T>.toFlowable(): Flowable<T> =
        Flowable.create({ emitter ->
            val observer = Observer<T> { data ->
                data?.let {
                    emitter.onNext(it)
                }
            }
            observeForever(observer)

            emitter.setCancellable {
                object : MainThreadDisposable() {
                    override fun onDispose() {
                        removeObserver(observer)
                    }
                }
            }
        }, BackpressureStrategy.LATEST)