package com.qingmei2.rhine.testframework.tools

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Created by QingMei on 2017/11/8.
 * desc:
 */
class RxTestTransformer<T> : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream
    }

}