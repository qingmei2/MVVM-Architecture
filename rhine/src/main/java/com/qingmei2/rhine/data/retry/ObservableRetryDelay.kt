package com.qingmei2.rhine.data.retry

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class ObservableRetryDelay(
        @NonNull retryConfig: RetryConfig
) : RetryFuction<Observable<Throwable>, ObservableSource<*>> {

    private val maxRetries: Int
    private val delay: Int
    private val condition: Function<Throwable, Boolean>

    private var retryCount: Int = 0

    init {
        this.maxRetries = retryConfig.maxRetries
        this.delay = retryConfig.delay
        this.condition = retryConfig.condition
    }

    @Throws(Exception::class)
    override fun apply(@NonNull throwableObservable: Observable<Throwable>): ObservableSource<*> {
        return throwableObservable
                .flatMap(Function<Throwable, ObservableSource<*>> { throwable ->
                    if (!condition.apply(throwable))
                        return@Function Observable.error<Any>(throwable)

                    if (++retryCount <= maxRetries) {
                        Observable.timer(delay.toLong(), TimeUnit.MILLISECONDS)
                    } else Observable.error<Any>(throwable)
                })
    }
}
