package com.qingmei2.rhine.data.core

import com.qingmei2.rhine.data.interceptor.GlobalOnErrorResumeTransformer
import com.qingmei2.rhine.data.interceptor.GlobalOnNextTransformer
import com.qingmei2.rhine.data.retry.GlobalRetryTransformer
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

class GlobalHandleErrorTransformer<T>(
        @param:NonNull private val upStreamScheduler: Scheduler,
        @param:NonNull private val downStreamScheduler: Scheduler,
        @param:NonNull private val onNextInterceptor: GlobalOnNextTransformer<T>,
        @param:NonNull private val onErrorResumeTransformer: GlobalOnErrorResumeTransformer<T>,
        @param:NonNull private val retryTransformer: GlobalRetryTransformer<T>,
        @param:NonNull private val doOnErrorConsumer: GlobalDoOnErrorConsumer
) : ObservableTransformer<T, T>, FlowableTransformer<T, T>,
        SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {

    constructor(
            @NonNull onNextInterceptor: GlobalOnNextTransformer<T>,
            @NonNull onErrorResumeTransformer: GlobalOnErrorResumeTransformer<T>,
            @NonNull retryTransformer: GlobalRetryTransformer<T>,
            @NonNull doOnErrorConsumer: GlobalDoOnErrorConsumer
    ) : this(AndroidSchedulers.mainThread(),
            Schedulers.io(),
            onNextInterceptor,
            onErrorResumeTransformer,
            retryTransformer,
            doOnErrorConsumer)

    override fun apply(upstream: Observable<T>): Observable<T> =
            upstream
                    .observeOn(upStreamScheduler)
                    .compose(onNextInterceptor)
                    .compose(onErrorResumeTransformer)
                    .observeOn(downStreamScheduler)
                    .compose(retryTransformer)
                    .doOnError(doOnErrorConsumer)

    override fun apply(upstream: Completable): Completable =
            upstream
                    .observeOn(upStreamScheduler)
                    .compose(onNextInterceptor)
                    .compose(onErrorResumeTransformer)
                    .observeOn(downStreamScheduler)
                    .compose(retryTransformer)
                    .doOnError(doOnErrorConsumer)

    override fun apply(upstream: Flowable<T>): Flowable<T> =
            upstream
                    .observeOn(upStreamScheduler)
                    .compose(onNextInterceptor)
                    .compose(onErrorResumeTransformer)
                    .observeOn(downStreamScheduler)
                    .compose(retryTransformer)
                    .doOnError(doOnErrorConsumer)

    override fun apply(upstream: Maybe<T>): Maybe<T> =
            upstream
                    .observeOn(upStreamScheduler)
                    .compose(onNextInterceptor)
                    .compose(onErrorResumeTransformer)
                    .compose(retryTransformer)
                    .observeOn(downStreamScheduler)
                    .doOnError(doOnErrorConsumer)

    override fun apply(upstream: Single<T>): Single<T> =
            upstream
                    .observeOn(upStreamScheduler)
                    .compose(onNextInterceptor)
                    .compose(onErrorResumeTransformer)
                    .observeOn(downStreamScheduler)
                    .compose(retryTransformer)
                    .doOnError(doOnErrorConsumer)
}
