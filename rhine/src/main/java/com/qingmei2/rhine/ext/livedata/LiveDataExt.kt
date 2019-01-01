package com.qingmei2.rhine.ext.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.qingmei2.rhine.util.RxSchedulers
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.android.MainThreadDisposable

fun <T> LiveData<T>.toReactiveX(): Flowable<T> = Flowable
        .create({ emitter: FlowableEmitter<T> ->
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
        .subscribeOn(RxSchedulers.ui)
        .observeOn(RxSchedulers.ui)
