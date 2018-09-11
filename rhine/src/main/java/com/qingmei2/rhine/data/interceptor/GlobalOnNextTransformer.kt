package com.qingmei2.rhine.data.interceptor

import com.qingmei2.rhine.data.core.ThrowableDelegate
import io.reactivex.*
import io.reactivex.annotations.NonNull
import org.reactivestreams.Publisher

class GlobalOnNextTransformer<T>(
        @param:NonNull private val onNextInterceptor: GlobalOnNextInterceptorFunction<T>
) : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer,
        GlobalOnNextInterceptorFunction<T> by onNextInterceptor {

    override fun apply(upstream: Completable): CompletableSource = upstream

    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.flatMap {
                apply(it)
                        .flatMapPublisher { rxerror ->
                            if (rxerror !== ThrowableDelegate.EMPTY) Flowable.error(rxerror) else Flowable.just(it)
                        }
            }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> =
            upstream.flatMap {
                apply(it)
                        .flatMapMaybe { rxerror ->
                            if (rxerror !== ThrowableDelegate.EMPTY) Maybe.error(rxerror) else Maybe.just(it)
                        }
            }

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
            upstream.flatMap {
                apply(it)
                        .flatMapObservable { rxerror ->
                            if (rxerror !== ThrowableDelegate.EMPTY) Observable.error(rxerror) else Observable.just(it)
                        }
            }

    override fun apply(upstream: Single<T>): SingleSource<T> =
            upstream.flatMap {
                apply(it)
                        .flatMap { rxerror ->
                            if (rxerror !== ThrowableDelegate.EMPTY) Single.error(rxerror) else Single.just(it)
                        }
            }
}
