package com.qingmei2.rhine.data.interceptor

import com.qingmei2.rhine.data.core.ThrowableDelegate
import io.reactivex.*
import io.reactivex.annotations.NonNull
import org.reactivestreams.Publisher

class GlobalOnErrorResumeTransformer<T>(
        @param:NonNull private val globalOnErrorResumeFunction: (Throwable) -> Single<ThrowableDelegate>
) : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Completable): CompletableSource =
            upstream.onErrorResumeNext {
                globalOnErrorResumeFunction(it)
                        .flatMapCompletable { rxerror -> Completable.error(if (rxerror !== ThrowableDelegate.EMPTY) rxerror else it) }
            }

    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.onErrorResumeNext { throwable: Throwable ->
                globalOnErrorResumeFunction(throwable)
                        .flatMapPublisher { rxerror ->
                            Flowable.error<T> {
                                if (rxerror !== ThrowableDelegate.EMPTY) rxerror else throwable
                            }
                        }
            }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> =
            upstream.onErrorResumeNext { throwable: Throwable ->
                globalOnErrorResumeFunction(throwable)
                        .flatMapMaybe { rxerror ->
                            Maybe.error<T> {
                                if (rxerror !== ThrowableDelegate.EMPTY) rxerror else throwable
                            }
                        }
            }

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
            upstream.onErrorResumeNext { throwable: Throwable ->
                globalOnErrorResumeFunction(throwable)
                        .flatMapObservable { rxerror ->
                            Observable.error<T> {
                                if (rxerror !== ThrowableDelegate.EMPTY) rxerror else throwable
                            }
                        }
            }

    override fun apply(upstream: Single<T>): SingleSource<T> =
            upstream.onErrorResumeNext {
                globalOnErrorResumeFunction(it)
                        .flatMap { rxerror -> Single.error<T>(if (rxerror !== ThrowableDelegate.EMPTY) rxerror else it) }
            }
}