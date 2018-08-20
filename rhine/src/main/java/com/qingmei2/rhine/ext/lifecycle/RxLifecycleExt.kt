package com.qingmei2.rhine.ext.lifecycle

import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.*
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from
import io.reactivex.*

fun <T> Observable<T>.bindLifecycle(lifecycleOwner: LifecycleOwner): ObservableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Flowable<T>.bindLifecycle(lifecycleOwner: LifecycleOwner): FlowableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Single<T>.bindLifecycle(lifecycleOwner: LifecycleOwner): SingleSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Maybe<T>.bindLifecycle(lifecycleOwner: LifecycleOwner): MaybeSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun Completable.bindLifecycle(lifecycleOwner: LifecycleOwner): CompletableSubscribeProxy =
        `as`(autoDisposable<Unit>(from(lifecycleOwner)))