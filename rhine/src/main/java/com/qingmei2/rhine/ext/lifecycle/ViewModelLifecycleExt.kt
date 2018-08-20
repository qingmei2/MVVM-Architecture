package com.qingmei2.rhine.ext.lifecycle

import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel
import com.uber.autodispose.*
import io.reactivex.*

fun <T> Observable<T>.bindLifecycle(lifecycleViewModel: LifecycleViewModel): ObservableSubscribeProxy<T> =
        bindLifecycle(lifecycleViewModel.lifecycleOwner
                ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel))

fun <T> Flowable<T>.bindLifecycle(lifecycleViewModel: LifecycleViewModel): FlowableSubscribeProxy<T> =
        bindLifecycle(lifecycleViewModel.lifecycleOwner
                ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel))

fun <T> Single<T>.bindLifecycle(lifecycleViewModel: LifecycleViewModel): SingleSubscribeProxy<T> =
        bindLifecycle(lifecycleViewModel.lifecycleOwner
                ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel))

fun <T> Maybe<T>.bindLifecycle(lifecycleViewModel: LifecycleViewModel): MaybeSubscribeProxy<T> =
        bindLifecycle(lifecycleViewModel.lifecycleOwner
                ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel))

fun Completable.bindLifecycle(lifecycleViewModel: LifecycleViewModel): CompletableSubscribeProxy =
        bindLifecycle(lifecycleViewModel.lifecycleOwner
                ?: throw throwableWhenLifecycleOwnerIsNull(lifecycleViewModel))

private fun throwableWhenLifecycleOwnerIsNull(viewModel: LifecycleViewModel): NullPointerException =
        NullPointerException("$viewModel's lifecycleOwner is null.")

