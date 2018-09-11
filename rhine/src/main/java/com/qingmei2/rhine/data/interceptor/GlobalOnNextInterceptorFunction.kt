package com.qingmei2.rhine.data.interceptor

import com.qingmei2.rhine.data.core.ThrowableDelegate

import io.reactivex.Single
import io.reactivex.functions.Function

interface GlobalOnNextInterceptorFunction<T> : Function<T, Single<ThrowableDelegate>>
