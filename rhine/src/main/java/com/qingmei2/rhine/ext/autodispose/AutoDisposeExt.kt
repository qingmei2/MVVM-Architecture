package com.qingmei2.rhine.ext.autodispose

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.*
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from
import io.reactivex.*

fun <T> Observable<T>.bindLifecycle(
        lifecycleOwner: LifecycleOwner
): ObservableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Flowable<T>.bindLifecycle(
        lifecycleOwner: LifecycleOwner
): FlowableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Single<T>.bindLifecycle(
        lifecycleOwner: LifecycleOwner
): SingleSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun <T> Maybe<T>.bindLifecycle(
        lifecycleOwner: LifecycleOwner
): MaybeSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner)))

fun Completable.bindLifecycle(
        lifecycleOwner: LifecycleOwner
): CompletableSubscribeProxy =
        `as`(autoDisposable<Unit>(from(lifecycleOwner)))

fun <T> Observable<T>.bindLifecycleEvent(
        lifecycleOwner: LifecycleOwner,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Flowable<T>.bindLifecycleEvent(
        lifecycleOwner: LifecycleOwner,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): FlowableSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Single<T>.bindLifecycleEvent(
        lifecycleOwner: LifecycleOwner,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): SingleSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun <T> Maybe<T>.bindLifecycleEvent(
        lifecycleOwner: LifecycleOwner,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): MaybeSubscribeProxy<T> =
        `as`(autoDisposable(from(lifecycleOwner, lifecycleEvent)))

fun Completable.bindLifecycleEvent(
        lifecycleOwner: LifecycleOwner,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): CompletableSubscribeProxy =
        `as`(autoDisposable<Unit>(from(lifecycleOwner, lifecycleEvent)))