package com.qingmei2.rhine.data.core

import com.qingmei2.rhine.data.interceptor.GlobalOnErrorResumeTransformer
import com.qingmei2.rhine.data.interceptor.GlobalOnNextTransformer
import com.qingmei2.rhine.data.retry.GlobalRetryTransformer
import com.qingmei2.rhine.data.retry.RetryConfig
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers

class GlobalHandleErrorTransformer<T> constructor(
        private val upStreamSchedulerProvider: () -> Scheduler = { AndroidSchedulers.mainThread() },
        private val downStreamSchedulerProvider: () -> Scheduler = { AndroidSchedulers.mainThread() },
        private val globalOnNextInterceptor: (T) -> Single<ThrowableDelegate>,
        private val globalOnErrorResumeTransformer: (Throwable) -> Single<ThrowableDelegate>,
        private val globalRetryTransformer: () -> RetryConfig,
        private val globalDoOnErrorConsumer: (Throwable) -> Unit
) : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Observable<T>): Observable<T> =
            upstream
                    .observeOn(upStreamSchedulerProvider())
                    .compose(GlobalOnNextTransformer<T>(globalOnNextInterceptor))
                    .compose(GlobalOnErrorResumeTransformer<T>(globalOnErrorResumeTransformer))
                    .compose(GlobalRetryTransformer<T>(globalRetryTransformer))
                    .doOnError(globalDoOnErrorConsumer)
                    .observeOn(downStreamSchedulerProvider())

    override fun apply(upstream: Completable): Completable =
            upstream
                    .observeOn(upStreamSchedulerProvider())
                    .compose(GlobalOnNextTransformer(globalOnNextInterceptor))
                    .compose(GlobalOnErrorResumeTransformer<T>(globalOnErrorResumeTransformer))
                    .compose(GlobalRetryTransformer<T>(globalRetryTransformer))
                    .doOnError(globalDoOnErrorConsumer)
                    .observeOn(downStreamSchedulerProvider())

    override fun apply(upstream: Flowable<T>): Flowable<T> =
            upstream
                    .observeOn(upStreamSchedulerProvider())
                    .compose(GlobalOnNextTransformer<T>(globalOnNextInterceptor))
                    .compose(GlobalOnErrorResumeTransformer<T>(globalOnErrorResumeTransformer))
                    .compose(GlobalRetryTransformer<T>(globalRetryTransformer))
                    .doOnError(globalDoOnErrorConsumer)
                    .observeOn(downStreamSchedulerProvider())

    override fun apply(upstream: Maybe<T>): Maybe<T> =
            upstream
                    .observeOn(upStreamSchedulerProvider())
                    .compose(GlobalOnNextTransformer<T>(globalOnNextInterceptor))
                    .compose(GlobalOnErrorResumeTransformer<T>(globalOnErrorResumeTransformer))
                    .compose(GlobalRetryTransformer<T>(globalRetryTransformer))
                    .doOnError(globalDoOnErrorConsumer)
                    .observeOn(downStreamSchedulerProvider())

    override fun apply(upstream: Single<T>): Single<T> =
            upstream
                    .observeOn(upStreamSchedulerProvider())
                    .compose(GlobalOnNextTransformer<T>(globalOnNextInterceptor))
                    .compose(GlobalOnErrorResumeTransformer<T>(globalOnErrorResumeTransformer))
                    .compose(GlobalRetryTransformer<T>(globalRetryTransformer))
                    .doOnError(globalDoOnErrorConsumer)
                    .observeOn(downStreamSchedulerProvider())
}
