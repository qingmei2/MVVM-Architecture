package com.qingmei2.sample.base.viewdelegates

import android.arch.lifecycle.LifecycleOwner
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.common.loadings.CommonLoadingState
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseLoadingViewDelegate(
        private val lifecycleOwner: LifecycleOwner
) : BaseViewDelegate() {

    val loadingViewModel: CommonLoadingViewModel

    init {
        loadingViewModel = CommonLoadingViewModel.instance().apply {
            addLifecycle(this@BaseLoadingViewDelegate.lifecycleOwner)
        }
    }

    fun applyState(state: CommonLoadingState) = loadingViewModel.applyState(state)
}