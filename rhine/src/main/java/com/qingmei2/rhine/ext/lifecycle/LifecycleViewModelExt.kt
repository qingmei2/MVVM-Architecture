package com.qingmei2.rhine.ext.lifecycle

import android.arch.lifecycle.Lifecycle
import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel
import com.qingmei2.rhine.ext.autodispose.bindLifecycleEvent
import com.uber.autodispose.*
import io.reactivex.*

fun <T> Observable<T>.bindLifecycle(
        lifecycleViewModel: LifecycleViewModel,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
        bindLifecycleEvent(
                lifecycleViewModel.lifecycleOwner
                        ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel),
                lifecycleEvent
        )

fun <T> Flowable<T>.bindLifecycle(
        lifecycleViewModel: LifecycleViewModel,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): FlowableSubscribeProxy<T> =
        bindLifecycleEvent(
                lifecycleViewModel.lifecycleOwner
                        ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel),
                lifecycleEvent
        )

fun <T> Single<T>.bindLifecycle(
        lifecycleViewModel: LifecycleViewModel,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): SingleSubscribeProxy<T> =
        bindLifecycleEvent(
                lifecycleViewModel.lifecycleOwner
                        ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel),
                lifecycleEvent
        )

fun <T> Maybe<T>.bindLifecycle(
        lifecycleViewModel: LifecycleViewModel,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): MaybeSubscribeProxy<T> =
        bindLifecycleEvent(
                lifecycleViewModel.lifecycleOwner
                        ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel),
                lifecycleEvent
        )

fun Completable.bindLifecycle(
        lifecycleViewModel: LifecycleViewModel,
        lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): CompletableSubscribeProxy =
        bindLifecycleEvent(
                lifecycleViewModel.lifecycleOwner
                        ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel),
                lifecycleEvent
        )

private fun throwableWhenLifecycleOwnerIsNull(viewModel: LifecycleViewModel): NullPointerException =
        NullPointerException("$viewModel's lifecycleOwner is null.")