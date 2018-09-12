package com.qingmei2.rhine.data.retry

import io.reactivex.*
import io.reactivex.annotations.NonNull
import org.reactivestreams.Publisher

class GlobalRetryTransformer<T> @JvmOverloads constructor(
        @param:NonNull private val retryConfigProvider: () -> RetryConfig = { RetryConfig.Builder().build() }
) : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Completable): CompletableSource =
            upstream.retryWhen(FlowableRetryDelay(retryConfigProvider()))


    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.retryWhen(FlowableRetryDelay(retryConfigProvider()))


    override fun apply(upstream: Maybe<T>): MaybeSource<T> =
            upstream.retryWhen(FlowableRetryDelay(retryConfigProvider()))


    override fun apply(upstream: Observable<T>): ObservableSource<T> =
            upstream.retryWhen(ObservableRetryDelay(retryConfigProvider()))


    override fun apply(upstream: Single<T>): SingleSource<T> =
            upstream.retryWhen(FlowableRetryDelay(retryConfigProvider()))
}
