package com.qingmei2.sample.base.viewdelegates

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.rhine.widget.placeholderview.ILoadingLayout
import com.qingmei2.rhine.widget.placeholderview.LoadingView
import com.qingmei2.sample.common.loadings.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseLoadingViewDelegate(
        private val lifecycleOwner: LifecycleOwner
) : BaseViewDelegate() {

    abstract val loadingContainer: View

    open val loadingViewSuppiler: () -> ILoadingLayout = { LoadingLayout() }
    open val errorViewSuppiler: () -> ILoadingLayout = { ErrorLayout() }
    open val emptyViewSuppiler: () -> ILoadingLayout = { EmptyLayout() }

    val loadingViewModel: CommonLoadingViewModel

    init {
        loadingViewModel = CommonLoadingViewModel.instance(
                LoadingView.build(
                        loadingContainer, loadingViewSuppiler,
                        errorViewSuppiler, emptyViewSuppiler
                )
        ).apply {
            addLifecycle(this@BaseLoadingViewDelegate.lifecycleOwner)
        }
    }

    fun applyState(state: CommonLoadingState) = loadingViewModel.applyState(state)
}