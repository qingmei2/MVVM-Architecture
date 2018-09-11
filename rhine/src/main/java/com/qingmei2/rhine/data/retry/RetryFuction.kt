package com.qingmei2.rhine.data.retry

import io.reactivex.functions.Function

internal interface RetryFuction<T, R> : Function<T, R>